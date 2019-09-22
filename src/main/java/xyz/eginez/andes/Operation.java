package xyz.eginez.andes;

import xyz.eginez.andes.parser.GoSSAParser;


public interface Operation {
    String getName();

    GoSSAParser.Arguments parseArguments(String[] tokens);

    Object execute(GoSSAParser.Arguments arguments, State state);
}
