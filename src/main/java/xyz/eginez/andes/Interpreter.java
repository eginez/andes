package xyz.eginez.andes;

import xyz.eginez.andes.parser.Instruction;

import static xyz.eginez.andes.Operations.ALL_OPERATIONS;

public class Interpreter {
    private final State state;

    public Interpreter(State state) {
        this.state = state;
    }

    public Value interpret(String[] lines) {
        Value v = null;
        for (int i = 0; i < lines.length; i++) {
            v = interpretLine(lines[i], state);
            state.currentValues.put(v.getId(), v);
        }
        return v;
    }

    public Instruction parse(String line) {
        return null;
    }

    public Value interpretLine(String line, State state) {
        String[] tokens = line.split(" ");
        assert tokens.length > 2;
        System.out.println(tokens[1]);

        String id = tokens[0].replace("v", "");
        String op = tokens[2];
        assert ALL_OPERATIONS.containsKey(op);

        int argSize = tokens.length - 3;
        String[] args = new String[argSize];
        for (int i = 0; i < argSize; i++) {
            args[i] = tokens[i + 3];
        }

        Object value = ALL_OPERATIONS.get(op).interpret(args, state);
        return new Value(Integer.parseInt(id), "", value);
    }
}
