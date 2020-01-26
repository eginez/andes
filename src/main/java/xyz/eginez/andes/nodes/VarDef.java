package xyz.eginez.andes.nodes;

import xyz.eginez.andes.Operation;
import xyz.eginez.andes.State;
import xyz.eginez.andes.annotations.SSAOperation;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * aux is a *gc.Node of a variable that is about to be initialized.  arg0=mem, returns mem
 * VarDef <mem> {~r0} v1
 */
@SSAOperation
public class VarDef extends Operation.DefaultOperation {
    private static Pattern ARGS = Pattern.compile("\\{(?<node>.+)\\} v(?<mem>\\d+)$");
    public VarDef() {
        super("VarDef");
    }

    @Override
    public Matcher getArgsMatcher(String tokens) {
        return ARGS.matcher(tokens);
    }

    @Override
    public GoSSAParser.Arguments matchArguments(Matcher matcher) {
        return new GoSSAParser.Arguments(new int[]{
                Integer.parseInt(matcher.group("mem"))
        }, new String[]{matcher.group("node")});
    }


    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        return null;
    }
}
