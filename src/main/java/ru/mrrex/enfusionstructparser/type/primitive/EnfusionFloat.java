package ru.mrrex.enfusionstructparser.type.primitive;

import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class EnfusionFloat implements EnfusionObject {

    private double value;

    public EnfusionFloat(double value) {
        this.value = value;
    }

    public EnfusionFloat() {
        this(0);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EnfusionFloat [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (getClass() != object.getClass())
            return false;

        EnfusionFloat other = (EnfusionFloat) object;

        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        return BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).toString();
    }
}
