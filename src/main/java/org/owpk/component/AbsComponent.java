package org.owpk.component;

import lombok.Getter;
import org.owpk.utils.JsonMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public abstract class AbsComponent<T> implements Component<T> {

   protected Map<Object, Object> objectGraph;
   protected List<String> options;

   public AbsComponent(Map<Object, Object> objectGraph, List<String> options) {
      this.objectGraph = objectGraph;
      this.options = options;
   }

   @SuppressWarnings("unchecked")
   protected <E> List<Map<Object, Object>> convert(List<E> list) {
      List<Map<Object, Object>> mapList = new ArrayList<>();
      for (E t: list) {
         mapList.add(JsonMapper.convert(t, Map.class));
      }
      return mapList;
   }

   protected List<String> parseOptions(String opt, String regex) {
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

   @Override
   public void execute() {
      if (options.size() == 0) {
         objectGraph.forEach((k,v) -> System.out.println(k + " : " + v));
      } else
         options.forEach(x -> childPrinter());
   }
   protected abstract Consumer<String> childPrinter();

}
