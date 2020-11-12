package org.owpk.entities;

import org.owpk.entities.jsonConfig.JsonConfig;

import java.util.List;

public interface Component {
   void execute(List<String> options);

   void execute(JsonConfig jsonConfig);
}
