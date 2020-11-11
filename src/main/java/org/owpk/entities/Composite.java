package org.owpk.entities;

import lombok.Getter;
import lombok.Setter;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.Resources;

import java.util.List;

@Getter
@Setter
public class Composite implements Component {
    protected List<? extends Component> list;
    public Composite(List<? extends Component> list) {
        this.list = list;
    }
    private boolean delimiter;

    @Override
    public void execute(List<String> options) {
        String delimiter = (String) Resources.ConfigReader.getProps().get("delimiter");
        list.forEach(x -> {
            x.execute(options);
            System.out.print(this.delimiter ? delimiter : "");
        });
    }

    @Override
    public void execute(JsonConfig jsonConfig) {
        list.forEach(x -> x.execute(jsonConfig));
    }

    public void useDelimiter(boolean delimiter) {
        this.delimiter = delimiter;
    }
}
