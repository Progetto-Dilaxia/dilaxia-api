package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameSource {

    private final static Logger logger = LoggerFactory.getLogger(TournamentSource.class);

    public static String addGame(Game game) {

        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				INSERT INTO partite(id_campo,id_sport, username_creatore, descrizione,
                    data_partita, anni_classe_partecipanti,
                    numero_giocatori_per_squadra, id_squadra1, id_squadra2, id_torneo)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""")){

            statement.setInt(1, game.getFieldId());
            statement.setInt(2, game.getSportId());
            statement.setString(3, game.getCreatorUsername());
            statement.setString(4, game.getGameDescription());
            statement.setTimestamp(5, game.getGameDate());
            statement.setString(6, game.getClassYears());
            statement.setInt(7, game.getPlayersPerTeam());
            statement.setInt(8, game.getIdTeam1());
            statement.setInt(9, game.getIdTeam2());
            statement.setInt(10, game.getTournamentId());

            statement.executeUpdate();
            return "funziona";
        } catch (SQLException e) {
            return e.getMessage();
        }

    }

}
