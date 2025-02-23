package ru.mrrex.enfusionstructparser.type.structure;

import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;
import ru.mrrex.enfusionstructparser.util.Whitespaces;

public class ObjectProperty<T extends EnfusionObject>
        implements SerializableEnfusionObject {

    private String key;
    private T value;

    public ObjectProperty(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public ObjectProperty(String key) {
        this(key, null);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null)
            throw new IllegalArgumentException("Key of an entity property cannot be null");

        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EntityProperty [key=\"" + key + "\", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());

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

        ObjectProperty<?> other = (ObjectProperty<?>) object;

        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;

        return true;
    }

    @Override
    public String serialize(int lineDepth) {
        String whitespaces = Whitespaces.get(lineDepth);
        String preparedKey = key.contains(" ") ? String.format("\"%s\"", key) : key;
        String serializedValue = (value != null) ? value.toEnfusionValue() : "null";

        return String.format("%s%s %s", whitespaces, preparedKey, serializedValue);
    }
}
