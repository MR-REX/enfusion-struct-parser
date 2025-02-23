package ru.mrrex.enfusionstructparser.type.primitive;

import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class EnfusionInt implements EnfusionObject {
    
    private int value;

    public EnfusionInt(int value) {
        this.value = value;
    }

    public EnfusionInt() {
        this(0);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EnfusionInt [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return 31 * 1 + value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (getClass() != object.getClass())
            return false;

        EnfusionInt other = (EnfusionInt) object;

        if (value != other.value)
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        return Integer.toString(value);
    }
}
