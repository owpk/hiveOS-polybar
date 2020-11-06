package org.owpk.entities.api.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wallet {

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
