package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.AbsComponent;

import java.util.List;
import java.util.function.Predicate;

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
    protected Predicate<String> defaultPredicate() {
        return x -> x.startsWith("pool_balances");
    }

    @Override
    protected void defaultOutput(List<String> options, String key, Object value) {
        poolBalances.forEach(x -> x.execute(parseInheritedOptions(options)));
    }
}
