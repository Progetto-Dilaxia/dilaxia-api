package it.avbo.dilaxia.api.servlets.sports;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.SubscriptionSource;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.UserSportSubscription;
import it.avbo.dilaxia.api.entities.enums.ProfessionalLevel;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.sports.SportSubscriptionModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/sports/subscribe")
public class SportSubscriptionServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        if (user.getRole() != UserRole.Student) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo gli studenti possono iscriversi a uno sport"
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
        SportSubscriptionModel sportSubscriptionModel;
        try {
            sportSubscriptionModel = gson.fromJson(data.get(), SportSubscriptionModel.class);
        } catch (
                JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }
        UserSportSubscription userSportSubscription = new UserSportSubscription(
                sportSubscriptionModel.getSportId(),
                user.getUsername(),
                ProfessionalLevel.fromValue(sportSubscriptionModel.getProfessionalLevel())
        );


        if (!SubscriptionSource.addUserSportSubscription(userSportSubscription)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'aggiunta dello sport"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

    }
}
