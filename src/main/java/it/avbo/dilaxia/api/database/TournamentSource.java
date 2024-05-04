package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.Tournament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TournamentSource {

    private final static Logger logger = LoggerFactory.getLogger(TournamentSource.class);

    public static Optional<Tournament[]> getAllTournaments() {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            SELECT *                                                      
            FROM tornei;                                              
            """)) {
            List<Tournament> tournaments = new ArrayList<>();

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                tournaments.add(new Tournament(
                        result.getInt("id"),
                        result.getInt("id_sport"),
                        result.getInt("id_campo"),
                        result.getString("username_creatore"),
                        result.getString("descrizione")
                ));
            }

            return Optional.of(tournaments.toArray(new Tournament[0]));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

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
                        result.getString("username_creatore"),
                        result.getString("descrizione")
                ));
            }
        } catch (SQLException e) {
            logger.error("Unexpected error during statement execution:\n{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    public static int addTournament(Tournament tournament) {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
                    INSERT INTO tornei(id_sport, id_campo, username_creatore, descrizione)
                    values (?, ?, ?, ?);
                """, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, tournament.getSportId());
            statement.setInt(2, tournament.getCampId());
            statement.setString(3, tournament.getCreatorUsername());
            statement.setString(4, tournament.getDescription());
            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Unexpected Error:{} ", e.getMessage(), e);
        }
        return -1;
    }
}
