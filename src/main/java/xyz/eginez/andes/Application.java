package xyz.eginez.andes;

import xyz.eginez.andes.parser.GoSSAParser;

class Application {

    public static void main(String[] args) {
        State state = new State();
        GoSSAParser.parseSSA(null, "b32:");
        Interpreter interpreter = new Interpreter(state);
        interpreter.interpret(args);
    }
}
