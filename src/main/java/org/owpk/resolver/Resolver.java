package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;

import java.util.List;

public interface Resolver<T extends Component> {
   void resolve(List<T> list);
   void printError(JsonNode body, int status);
}
