package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;

import java.util.List;

public interface Resolver<E extends Component> {
    void resolve(List<E> list);

    void printError(JsonNode body, int status);
}
