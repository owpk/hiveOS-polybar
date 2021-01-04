package org.owpk.core.resolver;

import picocli.CommandLine;

public class RawDataResolver extends AbsResolver<String> {
    @CommandLine.Option(names = {"-p", "--pretty"},
            description = "pretty output")
    private boolean pretty;
    private int pos;
    private char[] chars;

    public RawDataResolver(String[] args) {
        super(args);
    }

    @Override
    public void resolve(String data) {
        chars = data.toCharArray();
        pretty("");
    }

    private void pretty(String tab) {
        term(tab);
        while (pos < chars.length) {
            if (chars[pos] == '}') {
                break;
            }
            else pos++;
            term(tab);
        }
    }

    private void term(String tab) {
        char current = chars[pos];
        if (current == '[') {
            System.out.print(current + "\n" + tab);
            pos++;
            pretty(tab);
        } else if (current == '{') {
            pos++;
            String tempTab = tab;
            tab = tab + "\t";
            System.out.print(current + "\n" + tab);
            pretty(tab);
            char closingBracket = chars[pos];
            pos++;
            System.out.print("\n" + tempTab + closingBracket);
            if (pos < chars.length) {
                char next = chars[pos];
                System.out.print(next + (next == ']' ? "" : "\n" + tempTab));
            }
        } else if(current == ',') {
            System.out.print(current + "\n" + tab);
        }  else {
            if (current != '}')
                System.out.print(current);
        }
    }
}
