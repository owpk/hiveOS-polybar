package org.owpk.utils;

import org.owpk.entities.Component;
import org.owpk.entities.jsonConfig.JsonConfig;

import java.util.List;
import java.util.Map;

public interface FilterInt<E extends Component> {

   void doFilter(Map<String, List<String>> optionMap);

   void doFilter(JsonConfig jsonConfig);
}
