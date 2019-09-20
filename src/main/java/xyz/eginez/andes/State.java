package xyz.eginez.andes;

import java.util.HashMap;
import java.util.Map;

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
}
