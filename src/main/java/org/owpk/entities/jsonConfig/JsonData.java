package org.owpk.entities.jsonConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect
@ToString
public class JsonData {
   @JsonProperty(value = "data")
   private List<JsonConfig> data;
}
