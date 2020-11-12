package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.utils.FilterInt;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.entities.apiJson.wallet.Wallet;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.JsonMapper;
import org.owpk.utils.Resources;
import picocli.CommandLine;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbsResolver<E extends Component> implements Resolver<E> {
    protected String configName;

    @CommandLine.Option(names = {"-f", "--filter"},
            paramLabel = "FILTER",
            description = "filter by json field and value [-f field1:value1,value2;field2:value1,value2;...]")
    protected String filter;

    @CommandLine.Option(names = {"-v", "--verbose"},
            paramLabel = "FILTER",
            description = "filter by json field [-v field1,field2,field3:filed1:field2,field3...] ")
    protected String arguments;

    @CommandLine.Option(names = {"-d","--delimiter"})
    protected boolean delimiter;

    @CommandLine.Option(names = {"-D","--default"})
    protected boolean defaultPrintPattern;

    @CommandLine.Option(names = {"-c","--config"})
    protected boolean configJson;

    protected String[] args;

    public AbsResolver(String[] args, String name) {
        this.configName = name;
        this.args = args;
    }

    protected List<String> getOptionList() {
        return parseOptions(arguments, ",");
    }

    public void printError(JsonNode body, int status) {
        System.out.printf("Err: %s, %d", body, status);
    }

    protected Map<String, List<String>> parseFilterOptions() {
        return parseOptions(filter, ";").stream()
                .collect(Collectors.toMap(x -> x.split(":")[0],
                        i -> parseOptions(i.split(":")[1], ",")));
    }

    protected List<String> parseOptions(String opt, String regex) {
        if (opt == null) return Collections.emptyList();
        return Arrays.stream(opt.split(regex)).map(String::trim).collect(Collectors.toList());
    }

    /**
     * In general, if you need to filter your list of entities, you need to override this method in the inherited
     * class and specify the class field name, according to this field
     * the object will be removed from list you need to filter.
     * For example:
     * @see org.owpk.resolver.WalletSpecResolver#getPredicate(Map, Wallet)
     */
    protected abstract Predicate<E> getPredicate(Map<String, Object> objList, E entityList);

    /**
     * Same for json config parameters
     */
    protected abstract Predicate<JsonConfig> getJsonConfigPredicate(JsonConfig jsonConfig);

}
