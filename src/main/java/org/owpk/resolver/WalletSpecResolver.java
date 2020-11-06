package org.owpk.resolver;


import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.api.wallet.Wallet;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WalletSpecResolver extends AbsResolver<Wallet> {

   @CommandLine.Option(names = { "-n", "--names"},
          paramLabel = "SPECIFICATIONS",
          description = "wallet specification")
   private String walletNames;

   @CommandLine.Option(names = {"-c", "--coin"},
          paramLabel = "FILTER",
          description = "wallet specification")
   private String coinNames;

   @CommandLine.Option(names = {"-v", "--value"},
          paramLabel = "FILTER",
          description = "wallet specification")
   private String values;

   public WalletSpecResolver(String[] args) {
      super(args);
   }

   @Override
   public void resolve(List<Wallet> list) {
      CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
      if (checkIfEmpty(walletNames)) {
         final List<String> options = parseOptions(walletNames, ",");
         list.removeIf(x -> !options.contains(x.getName()));
      }
      if (checkIfEmpty(coinNames)) {
         final List<String> options = parseOptions(coinNames, ",");
         list.removeIf(x -> !options.contains(x.getCoin()));
      }

      if (checkIfEmpty(values)) {
         final List<String> options = parseOptions(values, ",");
//         List<Map<Object, Object>> mapList = convert(list);

         return;
      }
      list.forEach(System.out::println);
   }


   private boolean checkIfEmpty(String options) {
      return options != null && !options.isBlank();
   }

   @Override
   public void printError(JsonNode body, int status) {
      System.out.printf("Err: %s, %d", body, status);
   }
}
