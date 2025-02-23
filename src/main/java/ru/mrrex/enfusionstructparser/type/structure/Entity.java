package ru.mrrex.enfusionstructparser.type.structure;

import java.util.ArrayList;
import java.util.List;
import ru.mrrex.enfusionstructparser.exception.EntityPropertyAlreadyExistsException;
import ru.mrrex.enfusionstructparser.type.advanced.Prefab;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;
import ru.mrrex.enfusionstructparser.util.Patterns;
import ru.mrrex.enfusionstructparser.util.Whitespaces;

public class Entity implements SerializableEnfusionObject {

    private String className;
    private String name;
    private Prefab prefab;

    private List<ObjectComponent> components;
    private List<ObjectProperty<? extends EnfusionObject>> properties;

    private List<Entity> children;

    public Entity(String className, String name, Prefab prefab) {
        setClassName(className);

        this.name = name;
        this.prefab = prefab;

        this.components = new ArrayList<>();
        this.properties = new ArrayList<>();

        this.children = new ArrayList<>();
    }

    public Entity(String className, String name) {
        this(className, name, null);
    }

    public Entity(String className) {
        this(className, null);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if (!Patterns.CLASSNAME_PATTERN.matcher(className).matches())
            throw new IllegalArgumentException(
                    "Class name of the entity contains invalid characters");

        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (Patterns.CLASSNAME_PATTERN.matcher(name).matches())
            throw new IllegalArgumentException("Name of the entity contains invalid characters");

        this.name = name;
    }

    public Prefab getPrefab() {
        return prefab;
    }

    public void setPrefab(Prefab prefab) {
        this.prefab = prefab;
    }

    public List<ObjectComponent> getComponents() {
        return components;
    }

    public void setComponents(List<ObjectComponent> components) {
        if (components == null)
            throw new IllegalArgumentException("Components container of the entity cannot be null");

        this.components = components;
    }

    public boolean hasComponent(ObjectComponent component) {
        return components.contains(component);
    }

    public boolean hasComponents() {
        return !components.isEmpty();
    }

    public boolean addComponent(ObjectComponent component) {
        return components.add(component);
    }

    public boolean removeComponent(ObjectComponent component) {
        return components.remove(component);
    }

    public void clearComponents() {
        components.clear();
    }

    public List<ObjectProperty<? extends EnfusionObject>> getProperties() {
        return properties;
    }

    public void setProperties(List<ObjectProperty<? extends EnfusionObject>> properties) {
        if (properties == null)
            throw new IllegalArgumentException("Properties container of the entity cannot be null");

        this.properties = properties;
    }

    public boolean hasProperties() {
        return !properties.isEmpty();
    }

    public boolean hasProperty(ObjectProperty<? extends EnfusionObject> property) {
        return properties.contains(property);
    }

    public boolean addProperty(ObjectProperty<? extends EnfusionObject> property) {
        if (property == null)
            throw new IllegalArgumentException("Object property for the entity cannot be null");

        if (hasProperty(property))
            throw new EntityPropertyAlreadyExistsException(this, property);

        for (ObjectProperty<? extends EnfusionObject> addedProperty : properties)
            if (addedProperty.getKey().equals(property.getKey()))
                throw new EntityPropertyAlreadyExistsException(this, property);

        return properties.add(property);
    }

    public boolean removeProperty(ObjectProperty<? extends EnfusionObject> property) {
        return properties.remove(property);
    }

    public void clearProperties() {
        properties.clear();
    }

    public List<Entity> getChildren() {
        return children;
    }

    public void setChildren(List<Entity> children) {
        if (children == null)
            throw new IllegalArgumentException("Children container of the entity cannot be null");

        this.children = children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public boolean addChild(Entity entity) {
        return children.add(entity);
    }

    public boolean hasChild(Entity entity) {
        return children.contains(entity);
    }

    public boolean removeChild(Entity entity) {
        return children.remove(entity);
    }

    public void clearChildren() {
        children.clear();
    }

    public void clear() {
        clearComponents();
        clearProperties();
        clearChildren();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Entity [");
        stringBuilder.append("className=\"").append(className).append('"');

        if (name != null)
            stringBuilder.append(", name=\"").append(name).append('"');

        if (prefab != null)
            stringBuilder.append(", prefab=").append(prefab.toEnfusionValue());

        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((prefab == null) ? 0 : prefab.hashCode());
        result = prime * result + ((components == null) ? 0 : components.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((children == null) ? 0 : children.hashCode());

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

        Entity other = (Entity) object;

        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;

        if (prefab == null) {
            if (other.prefab != null)
                return false;
        } else if (!prefab.equals(other.prefab))
            return false;

        if (components == null) {
            if (other.components != null)
                return false;
        } else if (!components.equals(other.components))
            return false;

        if (properties == null) {
            if (other.properties != null)
                return false;
        } else if (!properties.equals(other.properties))
            return false;

        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;

        return true;
    }

    @Override
    public String serialize(int lineDepth) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        String whitespaces = Whitespaces.get(lineDepth);
        stringBuilder.append(whitespaces).append(className);

        if (name != null)
            stringBuilder.append(" ").append(name);

        if (prefab != null) {
            String serializedPrefab = prefab.toEnfusionValue();
            stringBuilder.append(" : ").append(serializedPrefab);
        }

        stringBuilder.append(" {").append(lineSeparator);

        if (hasComponents()) {
            stringBuilder.append(whitespaces).append(" components {").append(lineSeparator);

            for (ObjectComponent component : components)
                stringBuilder.append(component.serialize(lineDepth + 2));

            stringBuilder.append(whitespaces).append(" }").append(lineSeparator);
        }

        for (ObjectProperty<?> property : properties)
            stringBuilder.append(property.serialize(lineDepth + 1)).append(lineSeparator);

        if (hasChildren()) {
            stringBuilder.append(whitespaces).append(" {").append(lineSeparator);

            for (Entity child : children)
                stringBuilder.append(child.serialize(lineDepth + 2));

            stringBuilder.append(whitespaces).append(" }").append(lineSeparator);
        }

        stringBuilder.append(whitespaces).append("}").append(lineSeparator);

        return stringBuilder.toString();
    }
}
