package xyz.eginez.andes.nodes;

import xyz.eginez.andes.Operation;
import xyz.eginez.andes.State;
import xyz.eginez.andes.annotations.SSAOperation;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SSAOperation
public class ConstString extends Operation.DefaultOperation {
    private static Pattern STRING_CONTENT = Pattern.compile("\\{\"(?<val>.*)\"\\}");
    public ConstString() {
        super("ConstString");
    }

    @Override
    public Matcher getArgsMatcher(String tokens) {
        return STRING_CONTENT.matcher(tokens);
    }

    @Override
    public GoSSAParser.Arguments matchArguments(Matcher matcher) {
        String valueString = matcher.group("val");
        return new GoSSAParser.Arguments(null, new Object[]{unescape(valueString)});
    }

    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        assert arguments.getAux()[0] != null;
        return arguments.getAux()[0];
    }

    private String unescape(String escaped) {
        return escaped.replace("\\", "");
    }
}
