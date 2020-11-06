package org.owpk.resolver;


import com.mashape.unirest.http.JsonNode;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import picocli.CommandLine;

import java.util.List;

public class WalletSpecResolver extends AbsResolver {

    @CommandLine.Option(names = {"-v", "--verbose"},
            paramLabel = "FILTER",
            description = "wallet specification")
    private String coinNames;

    public WalletSpecResolver(String[] args) {
        super(args);
    }

    @Override
    public void resolve(List<Component> list) {
        try {
            CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
            List<String> options = parseOptions(coinNames, ",");
            Composite<Component> composite = new Composite<>(list);
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
