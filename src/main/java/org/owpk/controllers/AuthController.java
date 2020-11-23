package org.owpk.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.utils.Resources;

public class AuthController implements Controller {

   private static AuthController authController;

   public static AuthController getAuthController() {
      if (authController == null)
         authController = new AuthController();
      return authController;
   }

   private AuthController() {
   }

   public HttpResponse<JsonNode> getRequest(String resource, String authToken) throws UnirestException {
      return Unirest.get(Resources.API_TARGET + resource)
             .header("accept", "application/json")
             .header("Authorization", authToken)
             .asJson();
   }

   public HttpResponse<JsonNode> postRequest(String resource, String json) throws UnirestException {
      return Unirest.post(Resources.API_TARGET + resource)
             .header("Content-Type", "application/json")
             .body(json)
             .asJson();
   }
}
