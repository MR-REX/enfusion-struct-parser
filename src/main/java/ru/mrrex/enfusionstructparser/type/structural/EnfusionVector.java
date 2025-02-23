package ru.mrrex.enfusionstructparser.type.structural;

import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionFloat;

public class EnfusionVector implements EnfusionObject {

    private double x, y, z;

    public EnfusionVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EnfusionVector() {
        this(0, 0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "EnfusionVector [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;

        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(z);
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

        EnfusionVector other = (EnfusionVector) object;

        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;

        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;

        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        EnfusionArray<EnfusionFloat> array = new EnfusionArray<>();

        array.add(new EnfusionFloat(x));
        array.add(new EnfusionFloat(y));
        array.add(new EnfusionFloat(z));

        return array.toEnfusionValue();
    }
}
