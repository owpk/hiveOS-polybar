package org.owpk.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class Composite implements Component {
   protected List<Component> list;

   public Composite(List<Component> list) {
      this.list = list;
   }

   @Override
   public void execute(List<String> options) {
      list.forEach(x -> x.execute(options));
   }
}
