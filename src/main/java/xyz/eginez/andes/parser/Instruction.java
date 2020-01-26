package xyz.eginez.andes.parser;

import xyz.eginez.andes.Operation;

import java.util.ArrayList;
import java.util.List;

public interface Instruction {

    //     kind           control    successors
    //   ------------------------------------------
    //     Exit        return mem                []
    //    Plain               nil            [next]
    //       If   a boolean Value      [then, else]
    //    Defer               mem  [nopanic, panic]  (control opcode should be OpStaticCall to
    // runtime.deferproc

    class Block implements Instruction {

        private final int id;
        private String returnValueId;
        private List<Instruction> instructions = new ArrayList<>();

        public Block(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
        public String getReturnValueId() {
            return returnValueId;
        }

        public void setReturnValueId(String returnValueId) {
            this.returnValueId = returnValueId;
        }

        public void addInstruction(Instruction instruction) {
            instructions.add(instruction);
        }

    }

    class ValueInstruction implements Instruction {
        private final int id;
        private final String numLine;
        private final Operation operation;
        private final String type;
        private final GoSSAParser.Arguments args;
        private final Block block;

        public ValueInstruction(
                Block block, int id, String numLine, Operation operation, String type, GoSSAParser.Arguments args) {
            this.block = block;
            this.id = id;
            this.numLine = numLine;
            this.operation = operation;
            this.type = type;
            this.args = args;
        }

        public int getId() {
            return id;
        }

        public String getNumLine() {
            return numLine;
        }

        public Operation getOperation() {
            return operation;
        }

        public String getType() {
            return type;
        }

        public GoSSAParser.Arguments getArgs() {
            return args;
        }

        public Block getBlock() {
            return block;
        }

        @Override
        public String toString() {
            return "ValueInstruction{" +
                    "id=" + id +
                    ", numLine='" + numLine + '\'' +
                    ", operation='" + operation + '\'' +
                    ", type='" + type + '\'' +
                    ", args='" + args + '\'' +
                    ", block=" + block +
                    '}';
        }

    }

    class Function implements Instruction {
    }

}
