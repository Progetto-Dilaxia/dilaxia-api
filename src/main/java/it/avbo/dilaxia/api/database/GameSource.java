package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameSource {

    private final static Logger logger = LoggerFactory.getLogger(TournamentSource.class);

    public static boolean addGame(Game game) {

        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				INSERT INTO partite(id_campo, username_creatore, descrizione,
                    data_partita, anni_classe_partecipanti, max_giocatori, min_giocatori,
                    numero_giocatori_per_squadra, genere, id_squadra1, id_squadra_2, id_torneo)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""")){

            statement.setInt(1, game.getFieldId());
            statement.setString(2, game.getCreatorUsername());
            statement.setString(3, game.getGameDescription());
            statement.setDate(4, game.getGameDate());
            statement.setString(5, game.getClassYears());
            statement.setInt(6, game.getMaxPlayers());
            statement.setInt(7, game.getMinPlayers());
            statement.setInt(8, game.getPlayersPerTeam());
            statement.setString(9, game.getSex());
            statement.setInt(10, game.getIdTeam1());
            statement.setInt(11, game.getIdTeam2());
            statement.setInt(12, game.getTournamentId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

}
