package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.AbsComponent;

import java.util.List;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(of = "name")
public class Wallet extends AbsComponent {

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
    protected void maybeOtherConditions(List<String> options, String key, Object value) {
        if (checkIfValuePresent(options, "pool_balances") && key.startsWith("pool_balances")) {
            poolBalances.forEach(x -> x.execute(parseInheritedOptions(options, i -> i.startsWith("pool_balances"))));
        } else if (checkIfValuePresent(options, "balance") && key.startsWith("balance")) {
            balance.execute(parseInheritedOptions(options, i -> i.startsWith("balance")));
        }
    }
}
