package org.owpk.entities.api.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.*;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    private Long id;
    @JsonProperty(value = "user_id")
    private Long userId;
    private String name;
    private String source;
    private String coin;
    private String wal;
    @JsonProperty(value = "fetch_balance")
    private boolean fetchBalance;
    @JsonProperty(value = "api_key_id")
    private Integer apiKeyId;
    private Balance balance;
    @JsonProperty(value = "pool_balances")
    private List<PoolBalances> poolBalances;
    @JsonProperty(value = "fs_count")
    private Integer fsCount;
    @JsonProperty(value = "workers_count")
    private Integer workersCount;

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

    @Override
    public String toString() {
        return  "WalletName: " + name + "\n" +
                "Source: " + source + "\n" +
                "Coin: " + coin + "\n" +
                "Wallet addr: " + wal + "\n" +
                "Balance: " + balance.getValue() + "\n" +
                "Pool balances: " + poolBalances + "\n" +
                "FS count: " + fsCount + "\n" +
                "Worker count" + workersCount;
    }
}
