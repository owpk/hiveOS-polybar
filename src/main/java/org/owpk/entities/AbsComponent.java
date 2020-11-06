package org.owpk.entities;

import org.owpk.utils.JsonMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsComponent implements Component {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(List<String> options) {
        Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
        objGraph.forEach(standardConsumer(options));
    }

    protected BiConsumer<String, Object> standardConsumer(List<String> options) {
        return (k,v) -> {
            if (options.size() == 0) {
                System.out.println(k + " : " + v);
            } else if (options.contains(k)) {
                System.out.println(k + " : " + v);
            } else if (subClassPredicate(standardPredicate(), k)) {
                standardOutput(options, k, v);
            }
        };
    }

    protected void standardOutput(List<String> options,
                                  String key, Object value) {
        System.out.println(key + " : " + value);
    }

    protected Predicate<String> standardPredicate() {
        return x -> false;
    }

    protected boolean subClassPredicate(Predicate<String> predicate, Object o) {
        return predicate.test(o.toString());
    }
}
