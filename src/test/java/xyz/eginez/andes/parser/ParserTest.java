package xyz.eginez.andes.parser;

import org.junit.Before;
import org.junit.Test;
import xyz.eginez.andes.nodes.ConstString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ParserTest {

    static List<List<String>> PROGRAMS = null;

    public static List<List<String>> readSSAText(String fileName) throws IOException  {
        File f = new File(fileName);
        List<List<String>> allPrograms = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            List<String> currentProgram = new ArrayList<>();
            while((line = br.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#"))  continue;
                if (line.contains("---")) {
                    allPrograms.add(currentProgram);
                    currentProgram = new ArrayList<>();
                    continue;
                }
                currentProgram.add(line);
            }
            allPrograms.add(currentProgram);
        }
        return allPrograms;
    }

    @Before
    public void loadTestData() throws IOException {
        System.out.println(System.getProperty("user.dir"));
        if (PROGRAMS != null) return;
        PROGRAMS = readSSAText(Paths.get("src", "test", "java", "ssa.txt").toString());
    }

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
        System.out.println(inst);
        List<Instruction> instructions = GoSSAParser.parseSSA(null, inst);
        assertEquals(1, instructions.size());
        assertEquals(Instruction.ValueInstruction.class, instructions.get(0).getClass());
        Instruction.ValueInstruction valueInstruction = (Instruction.ValueInstruction) instructions.get(0);
        assertEquals(ConstString.class,valueInstruction.getOperation().getClass());
        assertEquals("\"hello\"", valueInstruction.getArgs().getAux()[0].toString());
    }

    @Test
    public void parseReturnInstruction() {
        String inst = "b1:\nRet v8 (+8)\n";
        System.out.println(inst);
        List<Instruction> instructions = GoSSAParser.parseSSA(null, inst);
        assertEquals(1, instructions.size());
        assertEquals("8", ((Instruction.Block) instructions.get(0)).getReturnValueId());
    }

    @Test
    public void parseLocalAddr() {
        String inst = "v4 (?) = LocalAddr <*string> {~r0} v2 v4";
        System.out.println(inst);
        List<Instruction> instructions = GoSSAParser.parseSSA(null, inst);
        assertEquals(1, instructions.size());
        assertNotNull(((Instruction.ValueInstruction) instructions.get(0)));
    }

    @Test
    public void parseProgram() {
        List<Instruction> instructions = GoSSAParser.parseSSA(null, String.join("\n", PROGRAMS.get(0)));
        assertTrue(!instructions.isEmpty());
    }
}
