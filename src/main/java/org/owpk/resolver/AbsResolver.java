package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.FilterInt;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.Wallet;
import picocli.CommandLine;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbsResolver<E extends Component> implements Resolver<E>, FilterInt<E> {

   protected String[] args;

   public AbsResolver(String[] args) {
      this.args = args;
   }

   protected List<String> parseOptions(String opt, String regex) {
      if (opt == null) return Collections.emptyList();
      return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
   }

   @Override
   public void resolve(List<E> list) {
      try {
         CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
         Composite composite = new Composite(doFilter(list));
         composite.execute(getOptionList());
      } catch (Exception e) {
         System.out.println(e.getLocalizedMessage());
      }
   }

   public void printError(JsonNode body, int status) {
      System.out.printf("Err: %s, %d", body, status);
   }

   protected abstract List<String> getOptionList();


}
