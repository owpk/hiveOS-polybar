package org.owpk.resolver;


import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.utils.JsonMapper;
import picocli.CommandLine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WalletSpecResolver extends AbsResolver<Wallet> {

    public WalletSpecResolver(String[] args) {
        super(args);
    }

    @Override
    protected Predicate<Wallet> getPredicate(Map<String, Object> objList, Wallet entityList) {
        return x -> x.getName().equals(objList.get("name"));
    }

}
