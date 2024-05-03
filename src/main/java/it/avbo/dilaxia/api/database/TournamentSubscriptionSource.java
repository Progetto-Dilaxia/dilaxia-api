package it.avbo.dilaxia.api.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TournamentSubscriptionSource {

    public static boolean addTournamentSubscriptions(int[] squadre, int tournamentId) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            INSERT INTO iscrizioni_squadre_tornei(id_torneo, id_squadra)
            VALUES (?, ?);
            """)) {
            statement.setInt(1, tournamentId);
            for(int squadra: squadre) {
                statement.setInt(2, squadra);
                statement.addBatch();
            }
            statement.executeBatch();
            return true;
        } catch (SQLException ignored) {

        }
        return false;
    }
}
