package xyz.eginez.andes;

import static xyz.eginez.andes.Application.Value;

import org.junit.Test;

public class ApplicationTest {

  @Test
  public void parseValueIns1() {
    Application app = new Application();
    Value v = app.interpret(new String[] {"v6 = ConstString <string> {\"hello\"}"});
  }
}
