package org.owpk.component.wallet;

import lombok.Getter;
import org.owpk.component.AbsComponent;
import org.owpk.component.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class PoolBalanceComponent extends AbsComponent<PoolBalanceComponent> {

   public PoolBalanceComponent(Map<Object, Object> objectGraph, List<String> options) {
      super(objectGraph, options);
   }

   @Override
   protected Consumer<String> childPrinter() {
      return x -> {
         System.out.println(x + objectGraph.get(x));
      };
   }
}
