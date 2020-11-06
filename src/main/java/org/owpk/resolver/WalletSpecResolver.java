package org.owpk.resolver;


import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.entities.api.wallet.Wallet;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WalletSpecResolver extends AbsResolver<Component> {

   @CommandLine.Option(names = {"-f", "--filter"},
          paramLabel = "FILTER",
          description = "wallet specification")
   private String coinNames;

   public WalletSpecResolver(String[] args) {
      super(args);
   }

   @Override
   public void resolve(List<Component> list) {
      CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
      List<String> options = parseOptions(coinNames, ",");
      Composite composite = new Composite(list);
      composite.execute(options);
   }

   private boolean checkIfEmpty(String options) {
      return options != null && !options.isBlank();
   }

   @Override
   public void printError(JsonNode body, int status) {
      System.out.printf("Err: %s, %d", body, status);
   }
}
