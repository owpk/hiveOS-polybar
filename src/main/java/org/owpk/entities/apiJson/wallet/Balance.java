package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.owpk.entities.AbsComponent;
import org.owpk.entities.jsonConfig.JsonConfig;

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
