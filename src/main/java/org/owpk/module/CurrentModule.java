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
import org.owpk.entities.UserDetails;
import org.owpk.entities.api.wallet.Entity;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;
import org.owpk.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrentModule implements Module {

    private final Controller auth;
    private final Controller wallet;
    private final TokenManager tokenManager;
    private final UserDetails userDetails;

    public CurrentModule() {
        this.userDetails = Resources.read("cfg.yaml", UserDetails.class);
        auth = new AuthController();
        wallet = new WalletController();
        tokenManager = new TokenManager();
    }

    @Override
    public void authRequest(UserDetails userDetails) throws IOException, UnirestException {
        String jsonUser = JsonMapper.mapToJson(userDetails.getUser());
        HttpResponse<JsonNode> response = auth.postRequest("/auth/login", jsonUser);
        if (response.getStatus() == 200) {
            tokenManager.writeToken((String) response.getBody().getObject().get("access_token"),
                    (Integer) response.getBody().getObject().get("expires_in"));
        }
        System.out.println("Auth: " + response.getStatusText());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void walletRequest() throws UnirestException {
        if (tokenManager.checkIfExpired()) {
            HttpResponse<JsonNode> response = wallet.getRequest("/farms/" + userDetails.getFarm_id() + "/wallets", tokenManager.getToken());

            if (response.getStatus() == 200) {
                final ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> wallets = new ArrayList<>();

                for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                    Wallet w = JsonMapper.mapFromJson(((JSONObject) o).toString(), Wallet.class);
                    if (userDetails.getWalletNames() != null && userDetails.getWalletNames().contains(w.getName()))
                        wallets.add(objectMapper.convertValue(w, Map.class));
                    else if (userDetails.getWalletNames() == null || userDetails.getWalletNames().size() == 0) {
                        wallets.add(objectMapper.convertValue(w, Map.class));
                    }
                }
                wallets.forEach(System.out::println);

            } else {
                System.out.println(response.getBody());
                System.out.println(response.getStatus());
                System.out.println(response.getStatusText());
            }
        }
    }

}