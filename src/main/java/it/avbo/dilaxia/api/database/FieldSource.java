package it.avbo.dilaxia.api.database;

import it.avbo.dilaxia.api.entities.Field;
import it.avbo.dilaxia.api.entities.Sport;
import it.avbo.dilaxia.api.entities.enums.FieldType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieldSource {
    public static Optional<Field> getFieldById(int id) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            SELECT *
            FROM campi
            WHERE campi.id = ?
        """)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return Optional.of(
                        new Field(
                                result.getInt("id"),
                                result.getInt("id_sport"),
                                result.getString("indirizzo"),
                                FieldType.fromValue(result.getString("tipo"))
                        )
                );
            }
        } catch (SQLException ignored) {

        }
        return Optional.empty();
    }

    public static Optional<Field[]> getFieldOfSport(int sportId) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            SELECT *
            FROM campi
            WHERE campi.id_sport = ?
        """)) {
            statement.setInt(1, sportId);
            ResultSet result = statement.executeQuery();

            List<Field> fields = new ArrayList<>();
            while(result.next()) {
                fields.add(
                        new Field(
                                result.getInt("id"),
                                result.getInt("id_sport"),
                                result.getString("indirizzo"),
                                FieldType.fromValue(result.getString("tipo"))
                        )
                );
            }

            return Optional.of(fields.toArray(new Field[0]));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public static boolean addField(Field field) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            INSERT INTO campi(id_sport,indirizzo, tipo)
            VALUES (?,?,?);
        """)) {
            statement.setString(2, field.getAddress());
            statement.setString(3, field.getFieldType().getValue());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean removeField(int id) {
        try(PreparedStatement statement = DBWrapper.getConnection().prepareStatement("""
            DELETE FROM campi
            WHERE campi.id = ?
        """)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
