package org.owpk.component;

import lombok.Getter;
import org.owpk.component.Component;
import org.owpk.component.wallet.WalletComponent;

import java.util.List;
import java.util.Map;

@Getter
public class Composite<T> implements Component<T> {
   protected List<Component<T>> list;

   public Composite(List<Component<T>> list) {
      this.list = list;
   }

   @Override
   public void execute() {
      list.forEach(Component::execute);
   }
}
