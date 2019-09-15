package xyz.eginez.andes.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.junit.Test;

public class ParserTest {

  @Test
  public void parseBlock() {
    Logger.getGlobal().setLevel(Level.ALL);
    StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
    handler.setLevel(Level.ALL);
    Logger.getGlobal().addHandler(handler);
    List<Instruction> instructions = GoSSAParser.parseSSA(null, "b32:");
    assertEquals(1, instructions.size());
  }
}
