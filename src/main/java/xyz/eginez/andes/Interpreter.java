package xyz.eginez.andes;

import xyz.eginez.andes.parser.Instruction;

import java.util.List;

public class Interpreter {
    private final State state;

    public Interpreter(State state) {
        this.state = state;
    }

    public void interpret(List<Instruction> instructions) {
        Value v = null;
        for (int i = 0; i < instructions.size(); i++) {
            interpretInstruction(instructions.get(i), state);
        }
    }

    public void interpretInstruction(Instruction instruction, State state) {
        if (instruction instanceof Instruction.ValueInstruction) {
            Instruction.ValueInstruction valueInstruction = (Instruction.ValueInstruction) instruction;
            Object valueContent = valueInstruction.getOperation().execute(valueInstruction.getArgs(), state);
            Value value = new Value(valueInstruction.getId(), valueInstruction.getType(), valueContent);
            state.currentValues.put(value.getId(), value);
            return;
        }

        if (instruction instanceof Instruction.Block) {
            Instruction.Block block = (Instruction.Block) instruction;

        }
    }
}
