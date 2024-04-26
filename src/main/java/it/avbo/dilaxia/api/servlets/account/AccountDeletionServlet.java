package it.avbo.dilaxia.api.servlets.account;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.avbo.dilaxia.api.database.UserSource;
import it.avbo.dilaxia.api.entities.User;
import it.avbo.dilaxia.api.models.auth.AccountDeletionModel;
import it.avbo.dilaxia.api.services.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.SaltedSimpleDigestPassword;
import org.wildfly.security.password.spec.SaltedHashPasswordSpec;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@WebServlet("/account/delete")
public class AccountDeletionServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private static final PasswordFactory passwordFactory;

    static {
        try {
            passwordFactory = PasswordFactory.getInstance(SaltedSimpleDigestPassword.ALGORITHM_SALT_PASSWORD_DIGEST_SHA_256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Optional<String> data = Utils.stringFromReader(request.getReader());

        if(data.isEmpty()) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il corpo della richiesta è vuoto"
            );
            return;
        }

        AccountDeletionModel accountDeletionModel;
        try{
        accountDeletionModel = gson.fromJson(data.get(), AccountDeletionModel.class);
        } catch (JsonSyntaxException e) {
            response.sendError(
                    HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Il formato dei dati inviati non corrisponde alla documentazione"
            );
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            SaltedHashPasswordSpec saltedHashSpec = new SaltedHashPasswordSpec(user.getPasswordHash(), user.getSalt());
            try {
                SaltedSimpleDigestPassword restored = (SaltedSimpleDigestPassword) passwordFactory.generatePassword(saltedHashSpec);
                if (passwordFactory.verify(restored, accountDeletionModel.getPassword().toCharArray())) {
                    if(UserSource.removeUser(user.getUsername())) {
                        request.getSession().invalidate();
                        response.setStatus(HttpServletResponse.SC_OK);
                        return;
                    }
                }
            } catch (InvalidKeySpecException | InvalidKeyException ignored) {
            }
        }
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
