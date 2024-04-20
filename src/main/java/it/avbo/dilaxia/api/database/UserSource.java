package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserSource {
    private static Logger logger = LoggerFactory.getLogger(UserSource.class);

    public static Optional<User> getUserByIdentifier(String identifier) {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
                SELECT *
               FROM utenti
               WHERE username = ? OR email = ?
               LIMIT 1;
             """)) {

            statement.setString(1, identifier);
            statement.setString(2, identifier);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("sesso").charAt(0),
                        Date.valueOf(resultSet.getString("data_nascita")),
                        UserRole.fromValue(resultSet.getString("ruolo")),
                        resultSet.getBytes("password_hash"),
                        resultSet.getBytes("salt")
                ));
            }
        } catch (SQLException e) {
            logger.error("Unexpected error during statement execution:\n{}", e.getMessage(), e);
        }
        return Optional.empty();
    }

    public static boolean addUser(User user) {
        try (PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
                    INSERT INTO utenti(username, email, sesso, data_nascita, ruolo, password_hash, salt)
                   VALUES (?, ?, ?, ?, ?, ?, ?);
                """)) {
            statement.setString(1, user.username);
            statement.setString(2, user.email);
            statement.setString(3, String.valueOf(user.sex));
            statement.setDate(4, user.birthday);
            statement.setString(5, user.role.getValue());
            statement.setBytes(6, user.passwordHash);
            statement.setBytes(7, user.salt);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;

        }
    }

    public static boolean removeUser(String username) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            DELETE FROM utenti
            WHERE utenti.username = ?;
        """)) {
            statement.setString(1, username);
            statement.executeUpdate();
            return true;
        }catch (SQLException e) {
            return false;

        }

    }
}
