package xyz.eginez.andes.parser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseBlock() {
        List<Instruction> instructions = GoSSAParser.parseSSA(null, "b32:");
        assertEquals(1, instructions.size());
    }

    @Test
    public void parseValue() {
        List<Instruction> instructions = GoSSAParser.parseSSA(null, "v9 (13) = Eq64 <bool> v5 v5");
        assertEquals(1, instructions.size());
    }

    @Test
    public void parseValueNoLine() {
        List<Instruction> instructions = GoSSAParser.parseSSA(null, "v9 (???) = Eq64 <bool> v5 v5");
        assertEquals(1, instructions.size());
        System.out.println(instructions);
    }
}
