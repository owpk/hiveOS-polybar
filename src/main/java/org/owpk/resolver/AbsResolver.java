package org.owpk.resolver;

import org.owpk.entities.api.wallet.PoolBalances;
import org.owpk.utils.JsonMapper;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbsResolver<E> implements Resolver<E> {

   protected String[] args;
   protected CommandLine cmd;

   public AbsResolver(String[] args) {
      this.args = args;
   }

   protected List<String> parseOptions(String opt, String regex) {
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

}
