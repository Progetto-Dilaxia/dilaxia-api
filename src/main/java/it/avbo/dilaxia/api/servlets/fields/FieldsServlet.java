package it.avbo.dilaxia.api.servlets.fields;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.FieldSource;
import it.avbo.dilaxia.api.entities.Field;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.FieldType;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.fields.AddFieldModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/fields")
public class FieldsServlet extends HttpServlet {
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Prima controlliamo se abbiamo un ID
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Optional<Field> result = FieldSource.getFieldById(id);
            if(result.isPresent()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);

                response.getWriter().print(gson.toJson(result.get()));
                return;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException ignored) {}

        try {
            int sportId = Integer.parseInt(request.getParameter("sport"));
            Optional<Field[]> result = FieldSource.getFieldOfSport(sportId);
            if(result.isPresent()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);

                response.getWriter().print(gson.toJson(result.get()));
                return;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException ignored) {}
        response.sendError(
                HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                "Non è stato possibile trovare i valori della query"
        );
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Impossibile trovare i dati che corrispondono all'utente"
            );
            return;
        }

        if (user.role != UserRole.Admin) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo gli admin possono aggiungere dei campi da gioco"
            );
            return;
        }

        Optional<String> data = Utils.stringFromReader(request.getReader());
        if(data.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il corpo della richiesta è vuoto"
            );
            return;
        }

        AddFieldModel addFieldModel;
        try {
            addFieldModel = gson.fromJson(data.get(), AddFieldModel.class);
        } catch (JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }

        Field fieldToAdd = new Field(
                0,
                addFieldModel.getSportId(),
                addFieldModel.getAddress(),
                FieldType.fromValue(addFieldModel.getType())
        );

        if(fieldToAdd.getFieldType() == null) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Tipo del campo sconosciuto: " + addFieldModel.getType()
            );
            return;
        }

        if(!FieldSource.addField(fieldToAdd)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Impossibile aggiungere il campo al database, controlla che tutti i riferimenti a risorse esterne siano esistenti"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Impossibile trovare i dati che corrispondono all'utente"
            );
            return;
        }

        if (user.role != UserRole.Admin) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo gli admin possono rimuovere dei campi da gioco"
            );
            return;
        }

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException ignore) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il campo 'id' deve essere un numero"
            );
            return;
        }

        if(!FieldSource.removeField(id)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'eliminazione del campo"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
