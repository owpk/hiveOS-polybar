package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;

import java.util.List;
import java.util.Map;

/**
 * @see org.owpk.resolver.AbsResolver
 * {@link org.owpk.resolver.AbsResolver#getPredicate(Map, Component)}
 */
public interface Resolver<E extends Component> {
    void resolve(List<E> list);

    void printError(JsonNode body, int status);
}
