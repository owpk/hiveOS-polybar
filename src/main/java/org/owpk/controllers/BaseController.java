package org.owpk.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.utils.Resources;

public class BaseController implements Controller {

   private static BaseController baseController;

   public static BaseController getBaseController() {
      if (baseController == null)
         baseController = new BaseController();
      return baseController;
   }

   private BaseController() {
   }

   public HttpResponse<JsonNode> getRequest(String resource, String authToken) throws UnirestException {
      return Unirest.get(Resources.API_TARGET + resource)
             .header("accept", "application/json")
             .header("Authorization", "Bearer " + authToken)
             .asJson();
   }

   //TODO
   public HttpResponse<JsonNode> postRequest(String basePath, String userJson) {
      return null;
   }
}
