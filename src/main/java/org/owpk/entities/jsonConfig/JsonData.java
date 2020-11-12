package org.owpk.entities.jsonConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.owpk.entities.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect
@ToString
public class JsonData implements Component {
   @JsonProperty(value = "data")
   private List<JsonConfig> data;

   @Override
   public void execute(List<String> options) {

   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      data.forEach(x -> x.execute(jsonConfig));
   }
}
