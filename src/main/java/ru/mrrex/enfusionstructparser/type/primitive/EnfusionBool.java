package ru.mrrex.enfusionstructparser.type.primitive;

import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class EnfusionBool implements EnfusionObject {

    private boolean value;

    public EnfusionBool(boolean value) {
        this.value = value;
    }

    public EnfusionBool() {
        this(false);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EnfusionBool [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return 31 * 1 + (value ? 1231 : 1237);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (getClass() != object.getClass())
            return false;

        EnfusionBool other = (EnfusionBool) object;

        if (value != other.value)
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        return value ? "1" : "0";
    }
}
