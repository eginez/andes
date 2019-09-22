package xyz.eginez.andes;

import java.util.Objects;

public class Value {
    private int id;
    private String type;
    private Object value;

    public Value(int id, String type, Object value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
