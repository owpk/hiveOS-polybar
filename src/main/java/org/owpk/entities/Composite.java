package org.owpk.entities;

import lombok.Getter;
import org.owpk.utils.Resources;

import java.util.List;

@Getter
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

    public void useDelimiter(boolean delimiter) {
        this.delimiter = delimiter;
    }
}
