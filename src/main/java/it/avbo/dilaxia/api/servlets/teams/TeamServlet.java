package it.avbo.dilaxia.api.servlets.teams;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import it.avbo.dilaxia.api.database.TeamSource;
import it.avbo.dilaxia.api.entities.Team;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import it.avbo.dilaxia.api.models.teams.TeamCreationModel;
import it.avbo.dilaxia.api.services.Utils;


@WebServlet("/teams")
public class TeamServlet extends HttpServlet {

	private final Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (!request.isRequestedSessionIdValid()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ignored) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"il parametro 'id' deve essere un numero");
			return;
		}

		Optional<Team> result = TeamSource.getTeamById(id);

		if (result.isPresent()) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(gson.toJson(result.get()));
			return;
		}
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!request.isRequestedSessionIdValid()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Impossibile trovare i dati che corrispondono all'utente");
			return;
		}

		if (!(user.getRole() == UserRole.Admin || user.getRole() == UserRole.Teacher)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Solo gli admin e i docenti possono creare dei team");
			return;
		}

		Optional<String> data = Utils.stringFromReader(request.getReader());
		if (data.isEmpty()) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Il corpo della richiesta è vuoto");
			return;
		}
		TeamCreationModel teamCreationModel;
		try {
			teamCreationModel = gson.fromJson(data.get(), TeamCreationModel.class);
		} catch (JsonSyntaxException e) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Il formato dei dati inviati non corrisponde alla documentazione");
			return;
		}
		Team teamToAdd = new Team(0, teamCreationModel.getName(), teamCreationModel.getSportId(),
				teamCreationModel.getUsernameCoach()

		);

		if (!TeamSource.addTeam(teamToAdd)) {
			response.sendError(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Errore durante l'aggiunta della squadra"
			);
			return;
		}

		response.setStatus(HttpServletResponse.SC_CREATED);

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (!request.isRequestedSessionIdValid()) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ignored) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"il parametro 'id' deve essere un numero");
			return;
		}
		Optional<Team> result = TeamSource.getTeamById(id);

		if (result.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
			"La squadra con id "+id+" non esiste" );
			return ;
		}
		
		
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Impossibile trovare i dati che corrispondono all'utente");
			return;
		}
		

		if (user.getRole() != UserRole.Admin || (user.getUsername()).equals(result.get().getUsernameCoach())) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Solo gli admin e i docenti possono creare dei team");
			return;
		}
		
		
		if(!TeamSource.removeTeam(id)) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Errore inaspettato");
			
			return;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
		response.setStatus(HttpServletResponse.SC_OK);
		
	}

}
