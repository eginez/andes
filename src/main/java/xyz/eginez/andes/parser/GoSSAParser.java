package xyz.eginez.andes.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.truffle.api.TruffleLanguage;

import static java.lang.String.format;

public class GoSSAParser {
  static Pattern BLOCK = Pattern.compile("\\s*b(?<bId>\\d+):.*");
  static Pattern VALUE = Pattern.compile("\\s*v(?<vId>\\d+) \\((?<numline>\\d+)\\) = (?<op>\\p{Alnum}+) <(?<type>\\p{Alnum}+)> (?<rest>.*)$");
  private static Logger logger = Logger.getGlobal();

  public static List<Instruction> parseSSA(TruffleLanguage<?> language, String ssaSource) {
    // Go's ssa is made of functions, that contain blocks, that contain values or branching
    // TODO add function parsing
    List<Instruction> allInstructions = new ArrayList<>();
    String[] statements = ssaSource.split("\n");
    for (String currStatement : statements) {
      logger.fine(format("matching statement [%s]", currStatement));
      Matcher matcher;
      if ((matcher = BLOCK.matcher(currStatement)).matches()) {
        String blockId = matcher.group("bId");
        logger.fine(format("blockID is %s", blockId));
        allInstructions.add(new Instruction.Block(Integer.parseInt(blockId)));
        continue;
      }

      if ((matcher = VALUE.matcher(currStatement)).matches()) {
        String valueId = matcher.group("vId");
        String operation = matcher.group("op");
        String numLine = matcher.group("numline");
        String type = matcher.group("type");
        String rest = matcher.group("rest");
        logger.fine(format("value = %s, %s, %s, %s, %s", valueId, numLine, operation, type, rest));
      }
    }
   return allInstructions;
  }
}
