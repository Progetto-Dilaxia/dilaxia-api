package it.avbo.dilaxia.api.servlets.tournaments;

import com.google.gson.Gson;
import it.avbo.dilaxia.api.database.TournamentSource;
import it.avbo.dilaxia.api.entities.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/tournaments/")
public class TournamentsServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.isRequestedSessionIdValid()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException ignored) {
            resp.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "il parametro 'id' deve essere un numero"
            );
            return;
        }

        Optional<Tournament> result = TournamentSource.getTournamentById(id);

        if(result.isPresent()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(result.get()));
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
