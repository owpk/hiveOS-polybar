package org.owpk.resolver;


import org.owpk.entities.api.wallet.Wallet;
import picocli.CommandLine;

import java.util.List;

public class WalletResolver extends AbsResolver<Wallet> {


   private String[] walletNames;
   private String[] coins;
   private String[] getWalletNames;
   private boolean poolBalancesRequested;
   private String balance;

   public WalletResolver(String[] args) {
      super(args);
   }

   @Override
   public void resolve(List<Wallet> list) {
      CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);

   }
}
