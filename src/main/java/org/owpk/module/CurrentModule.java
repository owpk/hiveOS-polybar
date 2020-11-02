package org.owpk.module;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.owpk.utils.JsonMapper;
import org.owpk.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentModule implements Module {

    private final Controller auth;
    private final Controller wallet;
    private final TokenManager tokenManager;
    private final UserDetails userDetails;

    public CurrentModule(UserDetails userDetails) {
        this.userDetails = userDetails;
        auth = new AuthController();
        wallet = new WalletController();
        tokenManager = new TokenManager();
    }

    @Override
    public void authRequest() throws IOException, UnirestException {
        String jsonUser = JsonMapper.mapToJson(userDetails.getUser());
        HttpResponse<JsonNode> response = auth.postRequest("/auth/login", jsonUser);
        if (response.getStatus() == 200) {
            tokenManager.writeToken((String) response.getBody().getObject().get("access_token"),
                    (Integer) response.getBody().getObject().get("expires_in"));
        }
        System.out.println("Auth:"+response.getStatusText());
    }

    @Override
    public void walletRequest() throws UnirestException {
        if (tokenManager.checkIfExpired()) {
            HttpResponse<JsonNode> response = wallet.getRequest("/farms/" + userDetails.getFarm_id() + "/wallets", tokenManager.getToken());

            if (response.getStatus() == 200) {
                List<Wallet> walletList = new ArrayList<>();

                for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                    Wallet w = JsonMapper.mapFromJson(((JSONObject) o).toString(), Wallet.class);
                    if (userDetails.getWalletNames() != null && userDetails.getWalletNames().contains(w.getName()))
                        walletList.add(w);
                    else if (userDetails.getWalletNames() == null || userDetails.getWalletNames().size() == 0) {
                        walletList.add(w);
                    }
                }

                walletList.forEach(consumer);
                Map<String, String> studentMap = new ObjectMapper().convertValue(walletList.get(0), Map.class);
                System.out.println(studentMap.get("coin"));

            } else {
                System.out.println(response.getBody());
                System.out.println(response.getStatus());
                System.out.println(response.getStatusText());
            }
        }
    }

}