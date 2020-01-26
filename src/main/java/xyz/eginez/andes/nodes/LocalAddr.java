package xyz.eginez.andes.nodes;

import xyz.eginez.andes.GoSSAInterpreterException;
import xyz.eginez.andes.Operation;
import xyz.eginez.andes.State;
import xyz.eginez.andes.annotations.SSAOperation;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The address of a variable
 * LocalAddr <*int> {a} v2 v1, per doc:
 * arg0 is SP, arg1 is memory and aux identifies the variable
 * Memory is used to order memory opertaions, they can be used or returned. basically
 * - memory flow
 * - memory access
 * https://quasilyte.dev/blog/post/go_ssa_rules/
 */

@SSAOperation
public class LocalAddr extends Operation.DefaultOperation {

    private static Pattern ARGS = Pattern.compile("\\{(?<varId>.+)\\} v(?<sp>\\d+) v(?<mem>\\d+)$");
    public static String NAME = "LocalAddr";

    public LocalAddr() {
        super(NAME);
    }

    @Override
    public Matcher getArgsMatcher(String tokens) {
        return ARGS.matcher(tokens);
    }

    @Override
    public GoSSAParser.Arguments matchArguments(Matcher matcher) {
        return new GoSSAParser.Arguments(new int[]{
                Integer.parseInt(matcher.group("sp")),
                Integer.parseInt(matcher.group("mem"))
        }, new String[]{matcher.group("varId")});
    }

    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        return null;
    }
}
