package xyz.eginez.andes.nodes;

import xyz.eginez.andes.GoSSAInterpreterException;
import xyz.eginez.andes.Operation;
import xyz.eginez.andes.State;
import xyz.eginez.andes.annotations.SSAOperation;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Store arg1 to arg0.  arg2=memory, aux=type.  Returns memory.
 * Store <mem> {string} v4 v6 v7
 */
@SSAOperation
public class Store extends Operation.DefaultOperation {
    private static Pattern ARGS = Pattern.compile("\\{(?<type>.+)\\} v(?<dest>\\d+) v(?<src>\\d+) v(?<mem>\\d+)$");
    public Store() {
        super("Store");
    }

    @Override
    public Matcher getArgsMatcher(String tokens) {
         return ARGS.matcher(tokens);
    }

    @Override
    public GoSSAParser.Arguments matchArguments(Matcher matcher) {
        return new GoSSAParser.Arguments(new int[]{
                Integer.parseInt(matcher.group("dest")),
                Integer.parseInt(matcher.group("src")),
                Integer.parseInt(matcher.group("mem"))
        }, new String[]{matcher.group("type")});
    }

    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        return null;
    }
}
