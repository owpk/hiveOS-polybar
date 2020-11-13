package org.owpk.module;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owpk.controllers.AuthController;
import org.owpk.controllers.Controller;
import org.owpk.controllers.WalletController;
import org.owpk.entities.apiJson.auth.User;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.resolver.Resolver;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;
import org.owpk.utils.TokenManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CurrentModule implements Module {
   private static final Logger log = LogManager.getLogger(CurrentModule.class);
   private final Controller auth;
   private final Controller wallet;
   private final TokenManager tokenManager;
   private final Properties properties;

   public CurrentModule() {
      properties = Resources.ConfigReader.getProperties();
      auth = new AuthController();
      wallet = new WalletController();
      tokenManager = new TokenManager();
   }

   @Override
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

   @Override
   public void walletsRequest(Resolver<Wallet> resolver) {
      if (tokenManager.checkIfExpired()) {
         try {
            HttpResponse<JsonNode> response = wallet.getRequest(
                   "/farms/" + properties.getProperty("farmId") + "/wallets",
                   tokenManager.getToken());
            if (response.getStatus() == 200) {
               List<Wallet> wallets = new ArrayList<>();
               for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                  wallets.add(JsonMapper.readFromJson(((JSONObject) o).toString(), Wallet.class));
               }
               resolver.resolve(wallets);
            } else {
               resolver.printError(response.getBody(), response.getStatus());
            }
            log.info(response.getHeaders());
            log.info(response.getStatus());
            log.info(response.getBody());
         } catch (UnirestException e) {
            log.error(e);
         }
      }
   }
}