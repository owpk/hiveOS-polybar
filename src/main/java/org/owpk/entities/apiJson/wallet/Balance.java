package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.AbsComponent;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.Resources;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Balance extends AbsComponent {

   @JsonProperty
   private String status;
   private Double value;

   @Override
   public void execute(JsonConfig jsonConfig) {
      printFieldsToShow(jsonConfig);
   }
}
