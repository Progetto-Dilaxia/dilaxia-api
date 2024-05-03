package it.avbo.dilaxia.api.servlets.tournaments;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.SubscriptionSource;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.tournaments.TournamentSubscriptionModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/tournaments/subscribe")
public class TournamentSubscriptionServlet extends HttpServlet {

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

        if (user.getRole() != UserRole.Teacher && user.getRole() != UserRole.Admin) {
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

        TournamentSubscriptionModel tournamentSubscriptionModel;
        try {
            tournamentSubscriptionModel = gson.fromJson(data.get(), TournamentSubscriptionModel.class);
        } catch (
                JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }

        if(!SubscriptionSource.addTournamentSubscription(tournamentSubscriptionModel)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'iscrizione"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
