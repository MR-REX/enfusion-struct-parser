package ru.mrrex.enfusionstructparser.type.advanced;

import java.util.UUID;
import java.util.regex.Pattern;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class GUID implements EnfusionObject {

    private static final Pattern GUID_PATTERN = Pattern.compile("^[0-9A-F]{16}$");

    public static boolean isValid(String value) {
        return GUID_PATTERN.matcher(value).matches();
    }

    public static GUID fromString(String value) {
        String preparedString = value.replaceAll("[\\[\\](){}]", "").toUpperCase();

        if (!isValid(preparedString))
            return null;

        return new GUID(preparedString);
    }

    public static GUID generate() {
        String uuidValue = UUID.randomUUID().toString();
        String guidValue = uuidValue.replace("-", "")
                                    .substring(0, 16)
                                    .toUpperCase();

        return new GUID(guidValue);
    }

    private String value;

    private GUID(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (!isValid(value))
            throw new IllegalArgumentException("Invalid GUID format");

        this.value = value;
    }

    @Override
    public String toString() {
        return "GUID [value=\"" + value + "\"]";
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

        GUID other = (GUID) object;

        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        return String.format("{%s}", value);
    }
}
