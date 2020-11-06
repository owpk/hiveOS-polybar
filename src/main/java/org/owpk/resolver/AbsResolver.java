package org.owpk.resolver;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbsResolver implements Resolver {

   protected String[] args;

   public AbsResolver(String[] args) {
      this.args = args;
   }

   protected List<String> parseOptions(String opt, String regex) {
      if (opt == null) return Collections.emptyList();
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

}
