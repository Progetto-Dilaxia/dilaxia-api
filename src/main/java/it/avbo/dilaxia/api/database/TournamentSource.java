package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.Tournament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TournamentSource {
    private final static Logger logger = LoggerFactory.getLogger(TournamentSource.class);

    public static Optional<Tournament> getTournamentById(int id) {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
                   SELECT *
                   FROM tornei
                   WHERE id = ?
                    LIMIT 1;
                """)) {
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new Tournament(
                        result.getInt("id"),
                        result.getInt("id_sport"),
                        result.getInt("id_campo"),
                        result.getString("coach"),
                        result.getString("prof_creatore"), result.getString("descrizione")
                ));
            }
        } catch (SQLException e) {
            logger.error("Unexpected error during statement execution:\n{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    public static int addTournament(Tournament tournament) {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
                    INSERT INTO tornei(id_sport, id_campo, coach, prof_creatore, descrizione)
                    values (?, ?, ?, ?, ?);
                """)
        ) {
            statement.setInt(1, tournament.sportId);
            statement.setInt(2, tournament.campId);
            statement.setString(3, tournament.coachUsername);
            statement.setString(4, tournament.creatorUsername);
            statement.setString(5, tournament.description);
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException e) {
        	logger.error("Unexpected Error:{} ", e.getMessage(), e);
        }
        return -1;
    }
}
