package org.owpk.module;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owpk.controllers.Controller;
import org.owpk.controllers.BaseController;
import org.owpk.entities.Component;
import org.owpk.resolver.EntityResolver;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class EntityModule {
   private static final Logger log = LogManager.getLogger(EntityModule.class);
   private final Controller baseController;
   private final TokenManager tokenManager;

   public EntityModule() {
      baseController = new BaseController();
      tokenManager = new TokenManager();
   }

   public <T extends Component> void entityRequest(EntityResolver<T> resolver, String path, Class<T> clazz) {
      if (tokenManager.checkIfExpired()) {
         try {
            HttpResponse<JsonNode> response = baseController.getRequest(
                   path, tokenManager.getToken());
            if (response.getStatus() == 200) {
               List<T> entities = new ArrayList<>();
               for (Object o : (JSONArray) response.getBody().getObject().get("data")) {
                  entities.add(JsonMapper.readFromJson(((JSONObject) o).toString(), clazz));
               }
               resolver.resolve(entities);
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