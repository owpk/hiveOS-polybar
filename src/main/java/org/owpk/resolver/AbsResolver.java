package org.owpk.resolver;

import org.owpk.entities.Component;
import org.owpk.entities.api.wallet.PoolBalances;
import org.owpk.utils.JsonMapper;
import picocli.CommandLine;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbsResolver implements Resolver {

   protected String[] args;
   protected CommandLine cmd;

   public AbsResolver(String[] args) {
      this.args = args;
   }

   protected List<String> parseOptions(String opt, String regex) {
      if (opt == null) return Collections.emptyList();
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

}
