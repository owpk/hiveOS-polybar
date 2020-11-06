package org.owpk.resolver;

import picocli.CommandLine;

public abstract class AbsResolver<E> implements Resolver<E> {
   protected String[] args;
   protected CommandLine cmd;

   public AbsResolver(String[] args) {
      this.args = args;
   }
}
