package org.owpk.resolver;


import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.entities.jsonConfig.JsonConfig;

import java.util.Map;
import java.util.function.Predicate;

public class WalletSpecResolver extends AbsResolver<Wallet> {

    public WalletSpecResolver(String[] args, String name) {
        super(args, name);
    }

    @Override
    protected Predicate<JsonConfig> getJsonConfigPredicate(JsonConfig jsonConfig) {
        return x -> x.getObjectName().equals(configName);
    }

    @Override
    protected Predicate<Wallet> getPredicate(Map<String, Object> objList, Wallet entityList) {
        return x -> x.getName().equals(objList.get("name"));
    }

}
