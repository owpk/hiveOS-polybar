package org.owpk.module;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.controllers.AuthController;
import org.owpk.controllers.Controller;
import org.owpk.controllers.WalletController;
import org.owpk.entities.User;
import org.owpk.utils.JsonMapper;

import java.io.IOException;

public class CurrentModule {

    private final Controller auth;
    private final Controller wallet;

    public CurrentModule() {
        auth = new AuthController();
        wallet = new WalletController();
    }

    public HttpResponse<JsonNode> auth(User user) throws IOException, UnirestException {
        String jsonUser = JsonMapper.mapToJson(user);
        return auth.postRequest("/auth/login", jsonUser);
    }

    public HttpResponse<JsonNode> wallet(Integer farmId, String token) throws UnirestException {
        return wallet.getRequest("/farms/" + farmId + "/wallets", token);
    }
}