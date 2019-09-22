package xyz.eginez.andes.parser;

import org.junit.Test;
import xyz.eginez.andes.nodes.ConstString;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseBlock() {
        List<Instruction> instructions = GoSSAParser.parseSSA(null, "b32:");
        assertEquals(1, instructions.size());
    }

    @Test
    public void parseConstString() {
        String inst = "v10 (?) = ConstString <string> {\"hello\"}";
        List<Instruction> instructions = GoSSAParser.parseSSA(null, inst);
        assertEquals(1, instructions.size());
        assertEquals(Instruction.ValueInstruction.class, instructions.get(0).getClass());
        Instruction.ValueInstruction valueInstruction = (Instruction.ValueInstruction) instructions.get(0);
        assertEquals(10, valueInstruction.getId());
        assertEquals(ConstString.class,valueInstruction.getOperation().getClass());
        assertEquals("hello", valueInstruction.getArgs().getAux()[0].toString());
    }
    @Test
    public void parseEscapedConsString() {
        String inst = "v10 (?) = ConstString <string> {\"\\\"hello\\\"\"}";
        List<Instruction> instructions = GoSSAParser.parseSSA(null, inst);
        assertEquals(1, instructions.size());
        assertEquals(Instruction.ValueInstruction.class, instructions.get(0).getClass());
        Instruction.ValueInstruction valueInstruction = (Instruction.ValueInstruction) instructions.get(0);
        assertEquals(ConstString.class,valueInstruction.getOperation().getClass());
        assertEquals("\"hello\"", valueInstruction.getArgs().getAux()[0].toString());
    }
}
