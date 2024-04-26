package it.avbo.dilaxia.api.servlets.teams;

import it.avbo.dilaxia.api.database.SubscriptionSource;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.UserTeamSubscription;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/teams/subscribe")
public class TeamSubscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    "Solo gli insegnanti possono creare dei tornei"
            );
            return;
        }

        int teamId;
        try {
            teamId = Integer.parseInt(request.getParameter("team"));
        } catch (NumberFormatException ignored) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "il parametro 'team' deve essere un numero");
            return;
        }

        UserTeamSubscription userTeamSubscription = new UserTeamSubscription(user.getUsername(), teamId);

        if(!SubscriptionSource.addUserTeamSubscription(userTeamSubscription)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'aggiunta di una partita"
            );
            return;
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

    }
}
