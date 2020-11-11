package org.owpk.resolver;


import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.entities.jsonConfig.JsonData;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;
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
    protected Predicate<JsonConfig> getJsonConfigPredicate(JsonConfig jsonConfig) {
        return x -> x.getObjectName().equals("wallet");
    }

    @Override
    protected Predicate<Wallet> getPredicate(Map<String, Object> objList, Wallet entityList) {
        return x -> x.getName().equals(objList.get("name"));
    }

    @Override
    protected void executeByJsonConfig(Composite composite) {
        composite.execute(Resources.ConfigReader.getJsonConfig("wallet"));
    }

    @Override
    public List<Wallet> doFilter(List<Wallet> entities) {
        JsonConfig jsonConfig = Resources.ConfigReader.getJsonConfig("wallet");
        List<JsonConfig> options = jsonConfig.getEntitiesToShow();
        for (JsonConfig cfg : options) {
            if (cfg.getObjectName().equals("pool_balances")) {

            }
        }
        return entities;
    }
}
