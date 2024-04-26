package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.UserSportSubscription;
import it.avbo.dilaxia.api.entities.enums.ProfessionalLevel;
import it.avbo.dilaxia.api.models.tournaments.TournamentSubscriptionModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionSource {
    public static Optional<UserSportSubscription[]> getAllUserSportSubscriptions(String userId) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
           SELECT * FROM iscrizioni_utenti_sport WHERE studente = ?; 
       """)) {
            statement.setString(1,userId);
            ResultSet result = statement.executeQuery();
            List<UserSportSubscription> subscription = new ArrayList<>();

            while(result.next()) {
                subscription.add(new UserSportSubscription(
                        result.getInt("id_sport"),
                        result.getString("studente"),
                        ProfessionalLevel.fromValue(result.getString("livello_professionalita"))
                ));
            }
            return Optional.of(subscription.toArray(new UserSportSubscription[0]));
        }catch (SQLException e) {

        }
        return Optional.empty();
    }

    public static boolean addUserSportSubscription(UserSportSubscription userSportSubscription){
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
				INSERT INTO iscrizioni_utenti_sport(id_sport, studente, livello_professionista)
				VALUES (?,?,?)
				""")) {
            statement.setInt(1, userSportSubscription.getSportId());
            statement.setString(2, userSportSubscription.getStudent());
            statement.setString(3, userSportSubscription.getProfessionalLevel().getValue());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {

        }
        return false;
    }


    public static boolean addTournamentSubscription(TournamentSubscriptionModel tournamentSubscription) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
              INSERT INTO iscrizioni_squadre_tornei(id_torneo, id_squadra)
              VALUES (?,?)
              """)) {
            statement.setInt(1, tournamentSubscription.getTournamentId());
            statement.setInt(2, tournamentSubscription.getTeamId());
            return true;
        }catch (SQLException ignored) {
            return false;
        }
    }
}
