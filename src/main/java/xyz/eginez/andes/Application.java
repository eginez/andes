package xyz.eginez.andes;

import java.util.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

class Application {

  public static class Value {
    private int id;
    private String type;
    private Object value;

    public Value(int id, String type, Object value) {
      this.id = id;
      this.value = value;
    }

    public int getId() {
      return id;
    }

    public Object getValue() {
      return value;
    }
  };

  public static class Operation {
    private final String name;
    private final int argsLen;
    private final Optional<Object> aux;
    private final BiFunction<String[], State, Object> interpreter;

    public Operation(
        String name, int argsLen, Object aux, BiFunction<String[], State, Object> interpreter) {
      this.name = name;
      this.argsLen = argsLen;
      this.aux = Optional.ofNullable(aux);
      this.interpreter = interpreter;
    }

    public String getName() {
      return name;
    }

    public int getArgsLen() {
      return argsLen;
    }

    public Optional<Object> getAux() {
      return aux;
    }

    public Object interpret(String[] arguments, State state) {
      return interpreter.apply(arguments, state);
    }
  }

  private static class State {
    Map<Integer, Value> currentValues;
    Object[] stack;
    int sp, bp;

    public State() {
      currentValues = new HashMap<>();
      stack = new Object[255];
      sp = 0;
      bp = 255;
    }
  };

  public static Map<String, Operation> ALL_OPERATIONS = new HashMap<String, Operation>();

  static {
    ALL_OPERATIONS.put(
        "InitMem", new Operation("InitMem", 0, null, (arguments, state) -> new Object()));
    ALL_OPERATIONS.put(
        "ConstString",
        new Operation(
            "ConstString",
            0,
            "String",
            (arguments, state) -> {
              for (String s : arguments) {
                System.out.println(s);
              }
              return new Object();
            }));
  }

  public Application() {}

  public Value interpret(String[] lines) {
    Value v = null;
    State state = new State();

    for (int i = 0; i < lines.length; i++) {
      v = interpretLine(lines[i], state);
      state.currentValues.put(v.getId(), v);
    }
    return v;
  }

  public Value interpretLine(String line, State state) {
    String[] tokens = line.split(" ");
    assert tokens.length > 2;
    System.out.println(tokens[1]);

    String id = tokens[0].replace("v", "");
    String op = tokens[2];
    assert ALL_OPERATIONS.containsKey(op);

    int argSize = tokens.length - 3;
    String[] args = new String[argSize];
    for (int i = 0; i < argSize; i++) {
      args[i] = tokens[i + 3];
    }

    Object value = ALL_OPERATIONS.get(op).interpret(args, state);
    return new Value(Integer.parseInt(id), "", value);
  }
}
