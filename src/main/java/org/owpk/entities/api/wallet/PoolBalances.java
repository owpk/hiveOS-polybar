package org.owpk.entities.api.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.owpk.entities.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PoolBalances implements Component {

    private String pool;
    @JsonProperty
    private Double value;
    @JsonProperty(value = "value_usd")
    private Double valueUsd;
    @JsonProperty(value = "value_fiat")
    private Double valueFiat;
    @JsonProperty
    private String status;

    public PoolBalances(String pool, Double value, Double valueUsd, Double valueFiat) {
        this.pool = pool;
        this.value = value;
        this.valueUsd = valueUsd;
        this.valueFiat = valueFiat;
    }

    @Override
    public String toString() {
        return "Pool: " + pool + "\n" +
                "Value: " + value + "\n" +
                "Value USD: " + valueUsd + "\n" +
                "Value fiat: " + valueFiat;
    }

    @Override
    public void execute(List<String> options) {

    }
}
