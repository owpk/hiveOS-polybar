package org.owpk.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface Controller {

   HttpResponse<JsonNode> getRequest(String resource, String authToken) throws UnirestException;

   HttpResponse<JsonNode> postRequest(String resource, String jsonBody) throws UnirestException;

}
