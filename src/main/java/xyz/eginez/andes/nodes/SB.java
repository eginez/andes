package xyz.eginez.andes.nodes;

import xyz.eginez.andes.Operation;
import xyz.eginez.andes.State;
import xyz.eginez.andes.annotations.SSAOperation;
import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;

/**
 * Returns the address of the static base pointer a.k.a. global variables
 */
@SSAOperation
public class SB extends Operation.DefaultOperation {
    public SB() {
        super("SB");
    }

    @Override
    public Matcher getArgsMatcher(String tokens) {
        return null;
    }

    @Override
    public GoSSAParser.Arguments matchArguments(Matcher matcher) {
        return null;
    }

    @Override
    public Object execute(GoSSAParser.Arguments arguments, State state) {
        return null;
    }
}
