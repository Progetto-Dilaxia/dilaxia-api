package it.avbo.dilaxia.api.servlets.sports;

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

/**
 * Servlet implementation class GetAllSportsServlet
 */
public class GetAllSportsServlet extends HttpServlet {
       
    private final Gson gson = new Gson();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
