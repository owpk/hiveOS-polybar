package org.owpk.entities.api.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.owpk.entities.Component;
import org.owpk.utils.JsonMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet implements Component {

    private Long id;
    @JsonProperty(value = "user_id") private Long userId;
    private String name;
    private String source;
    private String coin;
    private String wal;
    @JsonProperty(value = "fetch_balance") private boolean fetchBalance;
    @JsonProperty(value = "api_key_id")
    private Integer apiKeyId;
    private Balance balance;
    @JsonProperty(value = "pool_balances") private List<PoolBalances> poolBalances;
    @JsonProperty(value = "fs_count") private Integer fsCount;
    @JsonProperty(value = "workers_count") private Integer workersCount;
    @Override

    public String toString() {
        return  name;
    }

    @Override
    public void execute(List<String> options) {
        Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
        objGraph.forEach((k, v) -> {
            if (options.contains(k)) {
                System.out.println(k + " : " + v);
            }
        });
    }
}
