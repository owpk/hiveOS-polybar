package org.owpk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.owpk.entities.api.wallet.PoolBalances;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Filter {

    @Deprecated
    public List<PoolBalances> parseRow(String[] args, HttpResponse<JsonNode> response) {
        ObjectMapper mapper = new ObjectMapper();
        return IntStream.range(0, args.length - 1)
                .mapToObj(i -> // :
                        ((JSONArray) response.getBody().getObject().get("data")).getJSONObject(i))
                .filter(x -> Arrays.asList(args).subList(2, args.length).contains((String) x.get("name")))
                .map(x -> {
                    try {
                        return mapper.readValue(((JSONArray) x.get("pool_balances")).getJSONObject(0).toString(), PoolBalances.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new PoolBalances();
                }).collect(Collectors.toList());
    }
}
