package xyz.eginez.andes.parser;

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

        public Block(int id) {
            this.id = id;
        }
    }

    class Function implements Instruction {
    }

    class ValueInstruction implements Instruction {
        private final int id;
        private final String numLine;
        private final String operation;
        private final String type;
        private final String args;
        private final Block block;

        public ValueInstruction(
                Block block, int id, String numLine, String operation, String type, String args) {
            this.block = block;
            this.id = id;
            this.numLine = numLine;
            this.operation = operation;
            this.type = type;
            this.args = args;
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
}
