package org.owpk.resolver;

import org.owpk.entities.Component;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbsResolver<E extends Component> implements Resolver<E> {

   protected String[] args;

   public AbsResolver(String[] args) {
      this.args = args;
   }

   protected List<String> parseOptions(String opt, String regex) {
      if (opt == null) return Collections.emptyList();
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

}
