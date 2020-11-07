package org.owpk.resolver;


import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.Wallet;
import picocli.CommandLine;

import java.util.List;

public class WalletSpecResolver<T extends Wallet> extends AbsResolver<T> {

    @CommandLine.Option(names = {"-v", "--verbose"},
            paramLabel = "FILTER",
            description = "wallet specification")
    private String coinNames;

    public WalletSpecResolver(String[] args) {
        super(args);
    }

    @Override
    public void resolve(List<T> list) {
        try {
            CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
            List<String> options = parseOptions(coinNames, ",");
            Composite composite = new Composite(list);
            composite.execute(options);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void printError(JsonNode body, int status) {
        System.out.printf("Err: %s, %d", body, status);
    }
}
