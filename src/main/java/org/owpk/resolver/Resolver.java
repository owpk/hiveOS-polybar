package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;

import java.util.List;

public interface Resolver<E> {
   void resolve(List<E> list);

   void printError(JsonNode body, int status);
}
