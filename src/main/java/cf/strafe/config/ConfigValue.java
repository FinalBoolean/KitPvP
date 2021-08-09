package cf.strafe.config;

import lombok.*;

@RequiredArgsConstructor @Getter
public class ConfigValue {
    private final Object value;
    private final ValueType valueType;
    private final String name;

    public boolean getBoolean() {
        return (boolean) value;
    }

    public double getDouble() {
        return (double) value;
    }

    public int getInt() {
        return (int) value;
    }

    public long getLong() {
        return (long) value;
    }

    public String getString() {
        return (String) value;
    }

    public enum ValueType {
        INTEGER, DOUBLE, LONG, STRING;
    }
}
