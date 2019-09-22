package xyz.eginez.andes.nodes;

import xyz.eginez.andes.Operations;
import xyz.eginez.andes.State;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstString extends Operations.DefaultOperation {
    private static Pattern STRING_CONTENT = Pattern.compile("\\{\"(?<val>.*)\"\\}");
    public ConstString() {
        super("ConstString");
    }

    @Override
    public GoSSAParser.Arguments parseArguments(String[] tokens) {
        Matcher matcher = STRING_CONTENT.matcher(tokens[0]);
        String valueString = null;
        if (matcher.matches()) {
            valueString = matcher.group("val");
        }
        return new GoSSAParser.Arguments(null, new Object[]{valueString});
    }

    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        assert arguments.getAux()[0] != null;
        return arguments.getAux()[0];
    }
}
