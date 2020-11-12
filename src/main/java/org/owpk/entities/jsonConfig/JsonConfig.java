package org.owpk.entities.jsonConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.owpk.entities.Component;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect
@ToString
@AllArgsConstructor
public class JsonConfig implements Component {

   private String objectName;
   private Map<String, String> filterBy;
   private List<String> fieldsToShow;
   @JsonProperty
   private List<JsonConfig> entitiesToShow;

   public JsonConfig(String objectName, Map<String, String> filterBy, List<String> fieldsToShow) {
      this.objectName = objectName;
      this.filterBy = filterBy;
      this.fieldsToShow = fieldsToShow;
      this.entitiesToShow = Collections.emptyList();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      JsonConfig that = (JsonConfig) o;
      return Objects.equals(objectName, that.objectName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(objectName);
   }

   @Override
   public void execute(List<String> options) {

   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      entitiesToShow.forEach(x -> execute(jsonConfig));
   }
}
