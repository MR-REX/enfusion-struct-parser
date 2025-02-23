package ru.mrrex.enfusionstructparser.type.structure;

import java.util.ArrayList;
import java.util.List;
import ru.mrrex.enfusionstructparser.type.advanced.GUID;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;
import ru.mrrex.enfusionstructparser.util.Patterns;
import ru.mrrex.enfusionstructparser.util.Whitespaces;

public class ObjectComponent implements SerializableEnfusionObject {

    private String name;
    private GUID guid;

    private List<SerializableEnfusionObject> objects;

    public ObjectComponent(String name, GUID guid, List<SerializableEnfusionObject> objects) {
        this.name = name;
        this.guid = guid;
        this.objects = objects;
    }

    public ObjectComponent(String name, GUID guid) {
        this(name, guid, new ArrayList<>());
    }

    public ObjectComponent(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name of the object component cannot be null");

        if (Patterns.CLASSNAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException(
                    "Name of the object component contains invalid characters");

        this.name = name;
    }

    public GUID getGuid() {
        return guid;
    }

    public void setGuid(GUID guid) {
        this.guid = guid;
    }

    public List<SerializableEnfusionObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SerializableEnfusionObject> objects) {
        if (objects == null)
            throw new IllegalArgumentException(
                    "Objects container of the object component cannot be null");

        this.objects = objects;
    }

    public boolean hasObject(SerializableEnfusionObject object) {
        return objects.contains(object);
    }

    public boolean addObject(SerializableEnfusionObject object) {
        return objects.add(object);
    }

    public boolean removeObject(SerializableEnfusionObject object) {
        return objects.remove(object);
    }

    public void clear() {
        objects.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ObjectComponent[name=\"");
        stringBuilder.append(name).append("\"");

        if (guid != null)
            stringBuilder.append(", guid=\"").append(guid.getValue()).append("\"");

        stringBuilder.append(", objects=").append(objects.size()).append("]");

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((guid == null) ? 0 : guid.hashCode());
        result = prime * result + ((objects == null) ? 0 : objects.hashCode());

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

        ObjectComponent other = (ObjectComponent) object;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;

        if (guid == null) {
            if (other.guid != null)
                return false;
        } else if (!guid.equals(other.guid))
            return false;

        if (objects == null) {
            if (other.objects != null)
                return false;
        } else if (!objects.equals(other.objects))
            return false;

        return true;
    }

    @Override
    public String serialize(int lineDepth) {
        String whitespaces = Whitespaces.get(lineDepth);
        String lineSeparator = System.lineSeparator();

        StringBuilder stringBuilder = new StringBuilder(whitespaces);
        stringBuilder.append(name);

        if (guid != null)
            stringBuilder.append(" \"").append(guid.toEnfusionValue()).append('"');

        stringBuilder.append(" {").append(lineSeparator);

        for (SerializableEnfusionObject object : objects)
            stringBuilder.append(object.serialize(lineDepth + 1));

        stringBuilder.append(whitespaces).append("}").append(lineSeparator);

        return stringBuilder.toString();
    }
}
