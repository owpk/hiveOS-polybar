package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.AbsComponent;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Balance extends AbsComponent {
    @JsonProperty
    private String status;
    private Double value;

}
