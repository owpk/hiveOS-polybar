package org.owpk.module;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.User;
import org.owpk.entities.api.wallet.Wallet;
import org.owpk.resolver.Resolver;

import java.io.IOException;

public interface Module {
    void authRequest(User user) throws IOException, UnirestException;
    void walletsRequest(Resolver<Wallet> resolver) throws UnirestException;
}
