package org.owpk.entities;

import lombok.Getter;
import lombok.Setter;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.FilterInt;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;

import java.util.*;

@Getter
@Setter
public class Composite<T extends Component> implements Component, FilterInt<T> {

   protected List<T> list;

   public Composite(List<T> list) {
      this.list = list;
   }

   private boolean delimiter;

   @Override
   public void execute(List<String> options) {
      String delimiter = (String) Resources.ConfigReader.getProps().get("delimiter");
      list.forEach(x -> {
         x.execute(options);
         System.out.print(this.delimiter ? delimiter : "");
      });
   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      list.forEach(x -> x.execute(jsonConfig));
   }

   public void useDelimiter(boolean delimiter) {
      this.delimiter = delimiter;
   }

   @Override
   public void doFilter(Map<String, List<String>> optionMap) {
      if (list.size() == 0) return;

      Map<String, List<String>> map = optionMap;
      List<Map<String, Object>> lst = JsonMapper.convert(list);

      lst.forEach(x -> map.forEach((k, v) -> {
         if (!v.contains((String) x.get(k))) {
//                list.removeIf(i -> getPredicate(x, i).test(i));
         }
      }));
   }

   @SuppressWarnings("unchecked")
   private void defaultFilter(Map<String, String> filterDefinition) {
      filterDefinition.forEach((k, v) -> {
         Iterator<T> iterator = list.iterator();
         while (iterator.hasNext()) {
            T obj = iterator.next();
            Map<String, Object> objGraph = JsonMapper.convert(obj, LinkedHashMap.class);
            objGraph.forEach((ok, ov) -> {
               if (ok.equals(k)) {
                  if (!ov.equals(v)) {
                     iterator.remove();
                  }
               }
            });
         }
      });
   }

   @Override
   public void doFilter(JsonConfig jsonConfig) {
      if (list.size() == 0 || jsonConfig.getFilterBy() == null || jsonConfig.getFilterBy().size() == 0) return;
      Map<String, String> optLst = jsonConfig.getFilterBy();
      defaultFilter(optLst);
   }
}
