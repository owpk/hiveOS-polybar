package org.owpk.module;

import org.owpk.entities.apiJson.auth.User;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.resolver.Resolver;

public interface Module {
   void authRequest(User user);

   void walletsRequest(Resolver<Wallet> resolver);
}
