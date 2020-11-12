package org.owpk.entities.jsonConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect
@ToString
@AllArgsConstructor
public class JsonConfig {

   private String objectName;
   private Map<String, List<String>> filterBy;
   private List<String> fieldsToShow;
   @JsonProperty
   private List<JsonConfig> entitiesToShow;

   public JsonConfig(String objectName, Map<String, List<String>> filterBy, List<String> fieldsToShow) {
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

}
