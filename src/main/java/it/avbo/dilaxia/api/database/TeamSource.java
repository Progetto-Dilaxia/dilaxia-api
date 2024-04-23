package it.avbo.dilaxia.api.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import it.avbo.dilaxia.api.entities.Sport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.avbo.dilaxia.api.entities.Team;

public class TeamSource {
	private final static Logger logger = LoggerFactory.getLogger(TeamSource.class);

	public static Optional<Team> getTeamById(int id) {
		try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				SELECT *
				FROM squadre
				WHERE id = ?
				LIMIT 1;
				""")) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) { // controlla se c'è un risultato e va al prossimo
				return Optional.of(new Team(result.getInt("id"), // NOMI DATABASE
						result.getString("nome"),
						result.getInt("id_sport"),
						result.getString("username_coach")
				));
			}
		} catch (SQLException e) {
			logger.error("Id doesn't exist:\n{}", e.getMessage(), e);
		}
		return Optional.empty();
	}

	public static Optional<Team[]> getAllTeams() {
		String query = "SELECT * FROM squadre;";
		try (Statement statement = DBWrapper.getConnection().createStatement()) {

			List<Team> teams = new ArrayList<>();

			ResultSet result = statement.executeQuery(query);

			while(result.next()) {
				teams.add(new Team(
						result.getInt("id"),
						result.getString("nome"),
						result.getInt("id_sport"),
						result.getString("username_coach")
				));
			}
			return Optional.of(teams.toArray(new Team[0]));
		}catch (SQLException e) {
			logger.error("Unexpected error:\n{}", e.getMessage(), e);
		}
		return Optional.empty();
	}

	// ritorna l'id della squadra appena creata
	public static boolean addTeam(Team team) {
		try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				INSERT INTO squadre(nome, id_sport, username_coach)
				values(?,?,?)
				""")) {
			statement.setString(1, team.getName());
			statement.setInt(2, team.getSportId());
			statement.setString(3, team.getUsernameCoach());

			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Unexpected Error:{} ", e.getMessage(), e);
		}
		return false;
	}

	public static boolean removeTeam(int id) {
		try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				DELETE FROM squadre
				WHERE squadre.id = ?;
				""")) {
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;

		}
	}
}
