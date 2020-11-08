package org.owpk.resolver;

import com.mashape.unirest.http.JsonNode;
import org.owpk.FilterInt;
import org.owpk.entities.Component;
import org.owpk.entities.Composite;
import org.owpk.utils.JsonMapper;
import picocli.CommandLine;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbsResolver<E extends Component> implements Resolver<E>, FilterInt<E> {

    @CommandLine.Option(names = {"-f", "--filter"},
            paramLabel = "FILTER",
            description = "filter by json field and value [-f field1:value1,value2;field2:value1,value2;...]")
    protected String filter;

    @CommandLine.Option(names = {"-v", "--verbose"},
            paramLabel = "FILTER",
            description = "filter by json field [-v field1,field2,field3:filed1:field2,field3...] ")
    protected String arguments;

    protected String[] args;

    public AbsResolver(String[] args) {
        this.args = args;
    }

    protected List<String> getOptionList() {
        return parseOptions(arguments, ",");
    }

    @Override
    public void resolve(List<E> list) {
        try {
            CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
            Composite composite = new Composite(doFilter(list));
            composite.execute(getOptionList());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void printError(JsonNode body, int status) {
        System.out.printf("Err: %s, %d", body, status);
    }

    public List<E> doFilter(List<E> entities) {
        if (entities.size() == 0) return entities;

        Map<String, List<String>> map = parseFilterOptions();
        List<Map<String, Object>> lst = getObjList(entities);

        lst.forEach(x -> map.forEach((k, v) -> {
            if (!v.contains((String) x.get(k))) {
                entities.removeIf(i -> getPredicate(x, i).test(i));
            }
        }));
        return entities;
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> getObjList(List<E> entities) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (E e : entities) {
            maps.add(JsonMapper.convert(e, HashMap.class));
        }
        return maps;
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

    protected abstract Predicate<E> getPredicate(Map<String, Object> objList, E entityList);

}
