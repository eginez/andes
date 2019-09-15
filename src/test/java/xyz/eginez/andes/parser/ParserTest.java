package xyz.eginez.andes.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

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
}
