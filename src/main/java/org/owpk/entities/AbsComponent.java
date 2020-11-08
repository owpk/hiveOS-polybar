package org.owpk.entities;

import org.owpk.utils.JsonMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbsComponent implements Component {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(List<String> options) {
        Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
        objGraph.forEach(defaultBiConsumer(options));
    }

    protected BiConsumer<String, Object> defaultBiConsumer(List<String> options) {
        return (k,v) -> {
            if (options.size() == 0) {
                defaultOutput(k, v);
            } else if (options.contains(k)) {
                defaultOutput(k, v);
            } else
                maybeOtherConditions(options, k, v);
        };
    }

    protected void maybeOtherConditions(List<String> options, String key, Object value) {

    };

    protected void defaultOutput(String key, Object value) {
        System.out.println(key + " : " + value);
    }

    protected List<String> parseInheritedOptions(List<String> options, Predicate<String> predicate) {
        return  Arrays.stream(
                options.stream()
                        .filter(predicate)
                        .findAny()
                        .get()
                        .split(":"))
                .skip(1)
                .collect(Collectors.toList());
    }

    protected boolean checkIfValuePresent(List<String> options, String value) {
        return options.stream().anyMatch(x -> x.startsWith(value));
    }

    @Deprecated
    protected Predicate<String> defaultPredicate() {
        return x -> false;
    }

    @Deprecated
    protected boolean subClassPredicate(Predicate<String> predicate, Object o) {
        return predicate.test(o.toString());
    }

    @Deprecated
    protected List<String> parseInheritedOptions(List<String> options) {
        return  Arrays.stream(
                options.stream()
                        .filter(defaultPredicate())
                        .findAny()
                        .get()
                        .split(":"))
                .skip(1)
                .collect(Collectors.toList());
    }
}
