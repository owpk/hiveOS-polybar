package org.owpk.component.wallet;

import org.owpk.component.AbsComponent;
import org.owpk.component.Component;
import org.owpk.component.Composite;
import org.owpk.entities.api.wallet.PoolBalances;
import org.owpk.utils.JsonMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WalletComponent extends AbsComponent<WalletComponent> {

   public WalletComponent(Map<Object, Object> objectGraph, List<String> options) {
      super(objectGraph, options);
   }

   @Override
   protected Consumer<String> childPrinter() {
      return x -> {
         if (x.startsWith("pool_balances")) {
            final List<String> opt = parseOptions(x, ":");
            opt.remove(0);
            final Composite<PoolBalanceComponent> params = new Composite<>(
                    convert((ArrayList<PoolBalances>) objectGraph.get(x))
                            .stream()
                            .map(i -> new PoolBalanceComponent(i, opt))
                            .collect(Collectors.toList()));
         } else System.out.println(x + " : " + objectGraph.get(x));
      };
   }
}
