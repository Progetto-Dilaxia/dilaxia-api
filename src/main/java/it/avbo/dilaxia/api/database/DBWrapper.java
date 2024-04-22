package it.avbo.dilaxia.api.database;

import org.mariadb.jdbc.MariaDbDataSource;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.SaltedSimpleDigestPassword;
import org.wildfly.security.password.spec.ClearPasswordSpec;

import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.entities.enums.UserRole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class DBWrapper {
    static private Connection connection;
    static private final MariaDbDataSource dataSource;

    static {
        dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/dilaxia?allowMultiQueries=true&createDatabaseIfNotExist=true");

            dataSource.setUser("root");
            dataSource.setPassword("");
            connection = dataSource.getConnection();
        } catch (SQLException ignored) {
            try {
                dataSource.setPassword("dilaxia");
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setupDatabase() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String dbInitialization;
        try(InputStream inputStream = classloader.getResourceAsStream("init.sql")) {
            assert inputStream != null;
            dbInitialization = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute(dbInitialization);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        try {
            PasswordFactory passwordFactory = PasswordFactory.getInstance(SaltedSimpleDigestPassword.ALGORITHM_SALT_PASSWORD_DIGEST_SHA_256);
        
            SaltedSimpleDigestPassword digestedPassword = (SaltedSimpleDigestPassword) 
            passwordFactory.generatePassword(new ClearPasswordSpec("admin".toCharArray()));
            UserSource.addUser(new User(
            		"Admin",
            		"admin@avbo.it",
            		'N',
            		new Date(0),
            		UserRole.Admin,
            		digestedPassword.getDigest(),
            		digestedPassword.getSalt()
            		));
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
