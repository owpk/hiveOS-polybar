package org.owpk.module;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owpk.controllers.AuthController;
import org.owpk.controllers.Controller;
import org.owpk.entities.apiJson.auth.User;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.TokenManager;

import java.io.IOException;

public class AuthModule {
    private static final Logger log = LogManager.getLogger(EntityModule.class);
    private final Controller auth;
    private final TokenManager tokenManager;

    public AuthModule() {
        auth = new AuthController();
        tokenManager = new TokenManager();
    }

    public void authRequest(User user) {
        try {
            String jsonUser = JsonMapper.writeToJson(user);
            HttpResponse<JsonNode> response = auth.postRequest("/auth/login", jsonUser);
            if (response.getStatus() == 200) {
                tokenManager.writeToken((String) response.getBody().getObject().get("access_token"),
                        (Integer) response.getBody().getObject().get("expires_in"));
            }
            System.out.printf("Auth: %s, Status code: %d\n", response.getStatusText(), response.getStatus());
            log.info(response.getHeaders());
            log.info(response.getStatus());
            log.info(response.getBody());
        } catch (IOException | UnirestException e) {
            log.error(e);
        }
    }
}
