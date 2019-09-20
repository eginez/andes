package xyz.eginez.andes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class Operations {
    public static Map<String, Operation> ALL_OPERATIONS = new HashMap<>();

    static {
        ALL_OPERATIONS.put("ConstString", new DefaultOperation("ConstString", 0, null, null));
    }

    public static class DefaultOperation implements Operation {
        private final String name;
        private final int argsLen;
        private final BiFunction<String[], State, Value> interpretClosure;
        private Object aux;

        public DefaultOperation(
                String name, int argsLen, Object aux, BiFunction<String[], State, Value> closure) {
            this.name = name;
            this.argsLen = argsLen;
            this.aux = aux;
            this.interpretClosure = closure;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int getArgsLen() {
            return 0;
        }

        @Override
        public Optional<Object> getAux() {
            return Optional.ofNullable(aux);
        }

        @Override
        public Value interpret(String[] arguments, State state) {
            return interpretClosure.apply(arguments, state);
        }
    }
}
