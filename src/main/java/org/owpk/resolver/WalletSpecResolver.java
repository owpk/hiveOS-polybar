package org.owpk.resolver;


import com.mashape.unirest.http.JsonNode;
import org.owpk.FilterInt;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.Wallet;
import picocli.CommandLine;

import java.util.List;

public class WalletSpecResolver extends AbsResolver<Wallet> {

    @CommandLine.Option(names = {"-v", "--verbose"},
            paramLabel = "FILTER",
            description = "wallet specification")
    private String arguments;

    public WalletSpecResolver(String[] args) {
        super(args);
    }

    @Override
    protected List<String> getOptionList() {
        return parseOptions(arguments, ",");
    }

    @Override
    public List<Wallet> doFilter(List<Wallet> entities) {
        return entities;
    }
}
