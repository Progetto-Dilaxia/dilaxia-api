package it.avbo.dilaxia.api.servlets.tournaments;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.TournamentSource;
import it.avbo.dilaxia.api.database.TournamentSubscriptionSource;
import it.avbo.dilaxia.api.entities.Tournament;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.tournaments.TournamentCreationModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/tournaments/create")
public class CreateTournamentServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (user.getRole() == UserRole.Student || user.getRole() == UserRole.External) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo i professori e gli admin possono creare dei tornei"
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

        TournamentCreationModel tournamentCreationModel = gson.fromJson(data.get(), TournamentCreationModel.class);

        Tournament tournamentToCreate = new Tournament(
                0, // The id gets ignored
                tournamentCreationModel.getSportId(),
                tournamentCreationModel.getFieldId(),
                user.getUsername(),
                tournamentCreationModel.getDescription()
        );
        
        int tournamentId;
        try {
            tournamentId = TournamentSource.addTournament(tournamentToCreate);
        } catch (JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }
        
        if (tournamentId == -1) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Impossibile aggiungere il torneo"
            );
            return;
        }

        if (!TournamentSubscriptionSource.addTournamentSubscriptions(tournamentCreationModel.getTeams(), tournamentId)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Impossibile iscrivere le squadre al torneo"
            );
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
