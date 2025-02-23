package ru.mrrex.enfusionstructparser.type.primitive;

import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class EnfusionString implements EnfusionObject {

    private String value;

    public EnfusionString(String value) {
        this.value = value;
    }

    public EnfusionString() {
        this(null);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EnfusionString [value=" + toEnfusionValue() + "]";
    }

    @Override
    public int hashCode() {
        return 31 * 1 + ((value == null) ? 0 : value.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (getClass() != object.getClass())
            return false;

        EnfusionString other = (EnfusionString) object;

        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        String preparedValue = (value != null) ? value.replace("\"", "\\\"") : "null";
        return String.format("\"%s\"", preparedValue);
    }
}
