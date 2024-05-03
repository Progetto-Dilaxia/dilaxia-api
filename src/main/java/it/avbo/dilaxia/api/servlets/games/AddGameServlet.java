package it.avbo.dilaxia.api.servlets.games;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.GameSource;
import it.avbo.dilaxia.api.entities.Game;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.games.GameCreationModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@WebServlet("/games")
public class AddGameServlet extends HttpServlet {

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

        if (user.getRole() != UserRole.Teacher && user.getRole() != UserRole.Admin) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Solo gli insegnanti possono creare dei tornei"
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
        GameCreationModel gameCreationModel;
        try {
            gameCreationModel = gson.fromJson(data.get(), GameCreationModel.class);
        } catch (
                JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }
        Game gameToAdd;

        try{
        gameToAdd = new Game(
                0,
                gameCreationModel.getFieldId(),
                gameCreationModel.getSportId(),
                user.getUsername(),
                gameCreationModel.getGameDescription(),
                Timestamp.valueOf(gameCreationModel.getGameDate()),
                gameCreationModel.getClassYears(),
                gameCreationModel.getPlayersPerTeam(),
                gameCreationModel.getIdTeam1(),
                gameCreationModel.getIdTeam2(),
                gameCreationModel.getTournamentId()
        );
        } catch (IllegalArgumentException ignored) {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Formato della data errato");
            return;
        }
        String msg = GameSource.addGame(gameToAdd);
        /*
        if (!GameSource.addGame(gameToAdd)) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Errore durante l'aggiunta di una partita"
            );
            return;
        }*/

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().print(msg);
    }
}
