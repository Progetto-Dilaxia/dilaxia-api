package it.avbo.dilaxia.api.servlets.sports;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import com.google.gson.Gson;

import it.avbo.dilaxia.api.database.SportSource;
import it.avbo.dilaxia.api.entities.Sport;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.sports.SportCreationModel;
import it.avbo.dilaxia.api.services.Utils;

/**
 * Servlet implementation class AddSportServlet
 */
@WebServlet("/sports/add")
public class AddSportServlet extends HttpServlet {

    private final Gson gson = new Gson();

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

        if (user.getRole() != UserRole.Admin) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo gli admin possono creare dei tornei"
            );
            return;
        }

        Optional<String> data = Utils.stringFromReader(request.getReader());
        if (data.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il corpo della richiesta è vuoto"
            );
            return;
        }
        SportCreationModel sportCreationModel;
        try {
            sportCreationModel = gson.fromJson(data.get(), SportCreationModel.class);
        } catch (
                JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }
        Sport sportToAdd = new Sport(
                0,
                sportCreationModel.getName(),
                sportCreationModel.getDescription()
        );
        boolean isSportAdded = SportSource.addSport(sportToAdd);

        if (!isSportAdded) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'aggiunta dello sport"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

    }

}
