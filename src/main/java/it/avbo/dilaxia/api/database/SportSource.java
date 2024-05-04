package it.avbo.dilaxia.api.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.avbo.dilaxia.api.entities.Sport;

public class SportSource {

    private final static Logger logger = LoggerFactory.getLogger(TournamentSource.class);

    public static Optional<Sport[]> getAllSports() {
        String query = "SELECT * FROM sport;";
        try (Statement statement = DBWrapper.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(query);
            List<Sport> sports = new ArrayList<Sport>();
            while (result.next()) {

                Sport sport = new Sport(
                        result.getInt("id"),
                        result.getString("nome_sport"),
                        result.getString("descrizione")
                );
                sports.add(sport);

            }
            return Optional.of(sports.toArray(new Sport[0]));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("Unexpected error during statement execution:\n{}", e.getMessage(), e);
        }
        return Optional.empty();

    }

    public static boolean addSport(Sport sport) {

        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				INSERT INTO sport(nome_sport, descrizione)
				VALUES (?, ?)
				""")) {

            statement.setString(1, sport.getName());
            statement.setString(2, sport.getDescription());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

}
