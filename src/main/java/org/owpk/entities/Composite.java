package org.owpk.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class Composite <T extends Component> implements Component {
   protected List<T> list;

   public Composite(List<T> list) {
      this.list = list;
   }

   @Override
   public void execute(List<String> options) {
      list.forEach(x -> {
         x.execute(options);
         System.out.println("------------");
      });
   }
}
