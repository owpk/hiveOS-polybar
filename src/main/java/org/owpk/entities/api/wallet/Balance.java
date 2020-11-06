package org.owpk.entities.api.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.Component;

import java.util.List;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Balance implements Component {
    @JsonProperty
    private String status;
    private Double value;

    @Override
    public void execute(List<String> options) {

    }
}
