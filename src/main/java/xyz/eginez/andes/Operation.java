package xyz.eginez.andes;

import xyz.eginez.andes.parser.GoSSAParser;

import java.util.regex.Matcher;


public interface Operation {
    String getName();

    GoSSAParser.Arguments parseArguments(String tokens);

    Object execute(GoSSAParser.Arguments arguments, State state);

    abstract class DefaultOperation implements Operation {
        private final String name;

        public DefaultOperation(String name) {
            this.name = name;
        }

        public abstract Matcher getArgsMatcher(String tokens);
        public abstract GoSSAParser.Arguments matchArguments(Matcher matcher);

        public GoSSAParser.Arguments parseArguments(String tokens) {
            Matcher matcher = getArgsMatcher(tokens);
            if (matcher == null)
                return matchArguments(null);
            if (!matcher.matches())
                throw new GoSSAInterpreterException("Can not parse instruction: " + getName());

            return matchArguments(matcher);
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
