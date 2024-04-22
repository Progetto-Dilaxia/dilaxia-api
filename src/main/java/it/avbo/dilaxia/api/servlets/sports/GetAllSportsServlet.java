package it.avbo.dilaxia.api.servlets.sports;

import com.google.gson.Gson;
import it.avbo.dilaxia.api.database.SportSource;
import it.avbo.dilaxia.api.entities.Sport;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet implementation class GetAllSportsServlet
 */
@WebServlet("/sports/all")
public class GetAllSportsServlet extends HttpServlet {
       
    private final Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
		
		Optional<Sport[]> result = SportSource.getAllSports();
	
		if(result.isPresent()) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(gson.toJson(result.get()));
            return;
		}
	
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		
	}

}
