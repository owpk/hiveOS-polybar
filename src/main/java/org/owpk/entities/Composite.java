package org.owpk.entities;

import lombok.Getter;
import lombok.Setter;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.entities.jsonConfig.JsonConfigFilter;
import org.owpk.utils.CliFilter;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Composite<T extends Component> implements Component, CliFilter<T>, JsonConfigFilter {

   protected List<T> list;

   public Composite(List<T> list) {
      this.list = list;
   }

   private boolean delimiter;

   @Override
   public void execute(List<String> options) {
      String delimiter = (String) Resources.ConfigReader.getProperties().get("delimiter");
      if (list != null) {
         list.forEach(x -> {
            x.execute(options);
            System.out.print(this.delimiter ? delimiter : "");
         });
      } else System.out.printf((String) Resources.ConfigReader.getProperties().get("format"), "data", "N/A");
   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      String delimiter = (String) Resources.ConfigReader.getProperties().get("delimiter");
      if (list != null) {
         list.forEach(x -> {
            x.execute(jsonConfig);
            System.out.print(this.delimiter ? delimiter : "");
         });
      } else System.out.printf((String) Resources.ConfigReader.getProperties().get("format"), "data", "N/A");
   }

   public void useDelimiter(boolean delimiter) {
      this.delimiter = delimiter;
   }

   @Override
   public void doFilter(Map<String, List<String>> optionMap) {
      if (list == null || list.size() == 0) return;
      defaultFilter(optionMap);
   }

   @SuppressWarnings("unchecked")
   private void defaultFilter(Map<String, List<String>> filterDefinition) {
      filterDefinition.forEach((k, v) -> {
         Iterator<T> iterator = list.iterator();
         while (iterator.hasNext()) {
            T obj = iterator.next();
            Map<String, Object> objGraph = JsonMapper.convert(obj, LinkedHashMap.class);
            objGraph.forEach((ok, ov) -> {
               if (ok.equals(k)) {
                  if (!v.contains(ov.toString()))
                     iterator.remove();
               }
            });
         }
      });
   }

   @Override
   public void doFilter(JsonConfig jsonConfig) {
      if (list == null
             || list.size() == 0
             || jsonConfig.getFilterBy() == null
             || jsonConfig.getFilterBy().size() == 0) return;
      Map<String, List<String>> optLst = jsonConfig.getFilterBy();
      defaultFilter(optLst);
   }
}
