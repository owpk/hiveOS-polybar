package org.owpk.utils;

import org.owpk.entities.Component;

import java.util.List;
import java.util.Map;

public interface CliFilter<E extends Component> {

   void doFilter(Map<String, List<String>> optionMap);

}
