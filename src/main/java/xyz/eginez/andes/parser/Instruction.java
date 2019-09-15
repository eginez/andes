package xyz.eginez.andes.parser;

public interface Instruction {
    class Block implements Instruction{}
    class Function implements  Instruction{}
    class ValueInstruction implements Instruction{}
}


