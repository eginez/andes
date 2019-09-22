package xyz.eginez.andes;

import xyz.eginez.andes.nodes.ConstString;

import java.util.*;

public class Operations {
    public static Map<String, Operation> ALL_OPERATIONS = new HashMap<>();

    static {
        ALL_OPERATIONS.put("ConstString", new ConstString());
    }

    public abstract static class DefaultOperation implements Operation {
        private final String name;
        //private final BiFunction<String[], State, Object> interpretClosure;

        public DefaultOperation(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
