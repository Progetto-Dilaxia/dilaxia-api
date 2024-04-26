package it.avbo.dilaxia.api.servlets.tournaments;

import com.google.gson.Gson;
import it.avbo.dilaxia.api.database.TournamentSource;
import it.avbo.dilaxia.api.database.TournamentSubscriptionSource;
import it.avbo.dilaxia.api.entities.Tournament;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.tournaments.TournamentCreationModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class CreateTournamentServlet extends HttpServlet {
    private final Gson gson = new Gson();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if(user.getRole() == UserRole.Student || user.getRole() == UserRole.External) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo i professori e gli admin possono creare dei tornei"
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

        TournamentCreationModel tournamentCreationModel = gson.fromJson(data.get(), TournamentCreationModel.class);

        Tournament tournamentToCreate = new Tournament(
                0, // The id gets ignored
                tournamentCreationModel.getSportId(),
                tournamentCreationModel.getFieldId(),
                tournamentCreationModel.getCoachUsername(),
                user.getUsername(),
                tournamentCreationModel.getDescription()
        );

        int tournamentId = TournamentSource.addTournament(tournamentToCreate);

        if(tournamentId == -1) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        if(!TournamentSubscriptionSource.addTournamentSubscriptions(tournamentCreationModel.getTeams(), tournamentId)) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
