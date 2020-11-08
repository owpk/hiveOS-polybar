package org.owpk.module;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.owpk.entities.Component;
import org.owpk.entities.apiJson.auth.User;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.resolver.Resolver;

import java.io.IOException;

public interface Module {
    void authRequest(User user) throws IOException, UnirestException;
    <E extends Component> void walletsRequest(Resolver<E> resolver) throws UnirestException;
}
