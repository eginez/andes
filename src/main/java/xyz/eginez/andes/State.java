package xyz.eginez.andes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class State {
    Map<Integer, Value> currentValues;
    Object[] stack;
    int sp, bp;

    public State() {
        currentValues = new HashMap<>();
        stack = new Object[255];
        sp = 0;
        bp = 255;
    }

    public Optional<Value> getValue(int valueId) {
        return Optional.ofNullable(currentValues.get(valueId));
    }
}
