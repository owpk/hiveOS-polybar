package org.owpk.entities;

import org.owpk.utils.JsonMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbsComponent implements Component {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(List<String> options) {
        Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
        objGraph.forEach(getSubClassConsumer(options));
    }

    protected BiConsumer<String, Object> getSubClassConsumer(List<String> options) {
        return (k,v) -> {
            if (options.size() == 0) {
                System.out.println(k + " : " + v);
            } else if (options.contains(k)) {
                System.out.println(k + " : " + v);
            }
        };
    }
}
