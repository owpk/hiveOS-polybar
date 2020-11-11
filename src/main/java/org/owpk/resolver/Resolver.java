package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;

import java.util.List;

/**
 * @see org.owpk.resolver.AbsResolver
 */
public interface Resolver<E> {
    void resolve(List<E> list);

    void printError(JsonNode body, int status);
}
