package xyz.eginez.andes.parser;

import com.oracle.truffle.api.TruffleLanguage;
import xyz.eginez.andes.GoSSAInterpreterException;
import xyz.eginez.andes.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static xyz.eginez.andes.AllOperations.ALL_OPERATIONS;

public class GoSSAParser {
    static Pattern BLOCK_INSTRUCTION = Pattern.compile("\\s*b(?<bId>\\d+):.*");
    static Pattern VALUE_INSTRUCTION =
            Pattern.compile(
                    "\\s*v(?<vId>\\d+) \\((?<numline>[?|\\d]+)\\) = (?<op>\\p{Alnum}+) <(?<type>.+)> (?<rest>.*)$");
    public static Pattern RET_TOKEN = Pattern.compile("^Ret v(?<vId>\\d+) (?<rest>.*)$");
    private static Logger logger = Logger.getGlobal();

    public static class Arguments {
        private final int[] valueIdArgs;
        private final Object[] aux;

        public Arguments(int[] valueIdArgs, Object[] aux) {
            this.valueIdArgs = valueIdArgs;
            this.aux = aux;
        }

        public Object[] getAux() {
            return aux;
        }
    }

    public static List<Instruction> parseSSA(TruffleLanguage<?> language, String ssaSource) {
        // Go's ssa is made of functions, that contain blocks, that contain values or branching
        // TODO add function parsing
        List<Instruction> allInstructions = new ArrayList<>();
        String[] statements = ssaSource.split("\n");
        Instruction.Block currentBlock = null;
        for (String currStatement : statements) {
            logger.fine(format("matching statement [%s]", currStatement));
            Matcher matcher;
            if ((matcher = BLOCK_INSTRUCTION.matcher(currStatement)).matches()) {
                String blockId = matcher.group("bId");
                logger.fine(format("blockID is %s", blockId));
                currentBlock = new Instruction.Block(Integer.parseInt(blockId));
                allInstructions.add(currentBlock);
                continue;
            }

            if ((matcher = RET_TOKEN.matcher(currStatement)).matches()) {
                String valID = matcher.group("vId");
                currentBlock.setReturnValueId(valID);
                continue;
            }

            if ((matcher = VALUE_INSTRUCTION.matcher(currStatement)).matches()) {
                String valueId = matcher.group("vId");
                String operationName = matcher.group("op");
                String numLine = matcher.group("numline");
                String type = matcher.group("type");
                String rest = matcher.group("rest");
                logger.fine(format("value = %s, %s, %s, %s, %s", valueId, numLine, operationName, type, rest));

                if (!ALL_OPERATIONS.containsKey(operationName)) {
                    throw new GoSSAInterpreterException(format("Operation name: %s has not been implemented", operationName));
                }
                Operation operation = ALL_OPERATIONS.get(operationName);
                Arguments arguments = operation.parseArguments(rest);
                Instruction inst = new Instruction.ValueInstruction(
                        currentBlock, parseInt(valueId), numLine, operation, type, arguments);
                allInstructions.add(inst);

                continue;
            }
        }
        return allInstructions;
    }
}
