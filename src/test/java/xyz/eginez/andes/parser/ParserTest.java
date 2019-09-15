package xyz.eginez.andes.parser;

import java.util.logging.*;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {

    @Before
    public void setUp() {
        Logger.getGlobal().setLevel(Level.ALL);
        StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
        handler.setLevel(Level.ALL);
        Logger.getGlobal().addHandler(handler);
    }
  @Test
  public void parseBlock() {
      Logger.getGlobal().setLevel(Level.ALL);
      StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter());
      handler.setLevel(Level.ALL);
      Logger.getGlobal().addHandler(handler);
      GoSSAParser.parseSSA(null, "b32:");
  }
}
