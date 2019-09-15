package xyz.eginez.andes.parser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.truffle.api.TruffleLanguage;

import static java.lang.String.format;

public class GoSSAParser {
  static Pattern BLOCK = Pattern.compile("\\s*b(?<bId>\\d):.*");
  private static Logger logger = Logger.getGlobal();

  public static void parseSSA(TruffleLanguage<?> language, String ssaSource) {
    // Go's ssa is made of functions, that contain blocks, that contain values or branching
    // TODO add function parsing
    System.out.println("heEGZ!!");
    String[] statements = ssaSource.split("\n");
    for (String currStatement : statements) {
      logger.fine(format("matching statement: %s", currStatement));
      Matcher matcher = null;
      if ((matcher = BLOCK.matcher(currStatement)).matches()) {
        String blockId = matcher.group("bId");
        logger.fine(format("blockID is %s", blockId));
        continue;
      }
    }
  }
}
