package org.owpk.resolver;


import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.PoolBalances;
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

    @Override
    public void resolve(List<Wallet> list) {
        try {
            CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
            Composite<Wallet> composite = new Composite<>(list);
            if (defaultPrintPattern)
                Resources.ConfigReader.getProps().setProperty("format", "%s : %s\n");
            composite.useDelimiter(delimiter);
            if (configJson) {
                JsonConfig cfg = Resources.ConfigReader.getJsonConfig(configName);
                composite.doFilter(cfg);
                composite.execute(cfg);
            }
            else {
                composite.doFilter(parseFilterOptions());
                composite.execute(getOptionList());
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
