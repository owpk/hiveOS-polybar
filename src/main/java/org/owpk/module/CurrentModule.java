package org.owpk.module;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owpk.controllers.AuthController;
import org.owpk.controllers.Controller;
import org.owpk.controllers.WalletController;
import org.owpk.entities.User;
import org.owpk.entities.UserDetails;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.resolver.Resolver;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurrentModule implements Module {
    private final Controller auth;
    private final Controller wallet;
    private final TokenManager tokenManager;
    private final UserDetails userDetails;

    public CurrentModule() {

        //TODO
        userDetails = new UserDetails();
        // STAB !!!

        auth = new AuthController();
        wallet = new WalletController();
        tokenManager = new TokenManager();
    }

    @Override
    public void authRequest(User user) throws IOException, UnirestException {
        String jsonUser = JsonMapper.writeToJson(user);
        HttpResponse<JsonNode> response = auth.postRequest("/auth/login", jsonUser);
        if (response.getStatus() == 200) {
            tokenManager.writeToken((String) response.getBody().getObject().get("access_token"),
                    (Integer) response.getBody().getObject().get("expires_in"));
        }
    }

    @Override
    public void walletsRequest(Resolver<Wallet> resolver) throws UnirestException {
        if (tokenManager.checkIfExpired()) {
            HttpResponse<JsonNode> response = wallet.getRequest("/farms/" + userDetails.getFarm_id() + "/wallets", tokenManager.getToken());

            if (response.getStatus() == 200) {
                List<Wallet> wallets = new ArrayList<>();
                for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                    Wallet w = JsonMapper.readFromJson(((JSONObject) o).toString(), Wallet.class);
                    wallets.add(w);
                }
                resolver.resolve(wallets);
            } else {
                System.out.printf("Err: %s, %d", response.getBody(), response.getStatus());
            }
        }
    }

}