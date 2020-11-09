package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;

import java.util.List;

public class JsonResolver<E extends Component> implements Resolver<E>{

   @Override
   public void resolve(List<E> list) {

   }

   @Override
   public void printError(JsonNode body, int status) {

   }
}
