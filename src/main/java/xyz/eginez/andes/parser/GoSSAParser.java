package xyz.eginez.andes.parser;

import com.oracle.truffle.api.TruffleLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class GoSSAParser {
    static Pattern BLOCK = Pattern.compile("\\s*b(?<bId>\\d+):.*");
    static Pattern VALUE =
            Pattern.compile(
                    "\\s*v(?<vId>\\d+) \\((?<numline>[?|\\d]+)\\) = (?<op>\\p{Alnum}+) <(?<type>\\p{Alnum}+)> (?<rest>.*)$");
    private static Logger logger = Logger.getGlobal();

    public static List<Instruction> parseSSA(TruffleLanguage<?> language, String ssaSource) {
        // Go's ssa is made of functions, that contain blocks, that contain values or branching
        // TODO add function parsing
        List<Instruction> allInstructions = new ArrayList<>();
        String[] statements = ssaSource.split("\n");
        Instruction.Block currentBlock = null;
        for (String currStatement : statements) {
            logger.fine(format("matching statement [%s]", currStatement));
            Matcher matcher;
            if ((matcher = BLOCK.matcher(currStatement)).matches()) {
                String blockId = matcher.group("bId");
                logger.fine(format("blockID is %s", blockId));
                currentBlock = new Instruction.Block(Integer.parseInt(blockId));
                allInstructions.add(currentBlock);
                continue;
            }

            if ((matcher = VALUE.matcher(currStatement)).matches()) {
                String valueId = matcher.group("vId");
                String operation = matcher.group("op");
                String numLine = matcher.group("numline");
                String type = matcher.group("type");
                String rest = matcher.group("rest");
                logger.fine(format("value = %s, %s, %s, %s, %s", valueId, numLine, operation, type, rest));
                allInstructions.add(
                        new Instruction.ValueInstruction(
                                currentBlock, parseInt(valueId), numLine, operation, type, rest));
                continue;
            }
        }
        return allInstructions;
    }
}
