package org.owpk.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.controllers.BaseController;
import org.owpk.core.resolver.RawDataResolver;
import org.owpk.utils.TokenManager;

public class RawDataManager {
    private final BaseController baseController;
    private final TokenManager tokenManager;

    public RawDataManager() {
        baseController = BaseController.getBaseController();
        tokenManager = TokenManager.getTokenManager();
    }

    public String rawRequest(String[] options) {
        HttpResponse<JsonNode> httpRequest;
        RawDataResolver rawDataResolver = new RawDataResolver(options);
        try {
            httpRequest = baseController.getRequest(options[1], tokenManager.getToken());
            return httpRequest.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "";
    }

}
