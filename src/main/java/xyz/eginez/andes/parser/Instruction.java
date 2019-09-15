package xyz.eginez.andes.parser;

public interface Instruction {

    //     kind           control    successors
    //   ------------------------------------------
    //     Exit        return mem                []
    //    Plain               nil            [next]
    //       If   a boolean Value      [then, else]
    //    Defer               mem  [nopanic, panic]  (control opcode should be OpStaticCall to runtime.deferproc
    class Block implements Instruction{
        private final int id;

        public Block(int id) {
            this.id = id;
        }
    }
    class Function implements  Instruction{}
    class ValueInstruction implements Instruction{}
}


