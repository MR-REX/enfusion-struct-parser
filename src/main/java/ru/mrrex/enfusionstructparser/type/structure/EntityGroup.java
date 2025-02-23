package ru.mrrex.enfusionstructparser.type.structure;

import java.util.ArrayList;
import java.util.List;
import ru.mrrex.enfusionstructparser.type.advanced.Prefab;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionString;
import ru.mrrex.enfusionstructparser.util.Patterns;
import ru.mrrex.enfusionstructparser.util.Whitespaces;

public class EntityGroup implements SerializableEnfusionObject {

    private String className;
    private Prefab prefab;

    private List<Entity> entities;

    public EntityGroup(String className, Prefab prefab, List<Entity> entities) {
        setClassName(className);

        this.prefab = prefab;
        this.entities = entities;
    }

    public EntityGroup(String className, Prefab prefab) {
        this(className, prefab, new ArrayList<>());
    }

    public EntityGroup(String className) {
        this(className, null);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        if (!Patterns.CLASSNAME_PATTERN.matcher(className).matches())
            throw new IllegalArgumentException(
                    "Class name of the entity group contains invalid characters");

        this.className = className;
    }

    public Prefab getPrefab() {
        return prefab;
    }

    public void setPrefab(Prefab prefab) {
        this.prefab = prefab;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        if (entities == null)
            throw new IllegalArgumentException(
                    "Entity group should not have a null container for entities");

        this.entities = entities;
    }

    public boolean hasEntity(Entity entity) {
        return entities.contains(entity);
    }

    public boolean addEntity(Entity entity) {
        if (!entity.getClassName().equals(className))
            throw new IllegalArgumentException(
                    "Only entities with the same class name can be combined into an entity group");

        return entities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    @Override
    public String toString() {
        String prefabValue = (prefab != null) ? prefab.toEnfusionValue() : "null";

        return "EntityGroup [className=\"" + className + "\", prefab=" + prefabValue + ", entities="
                + entities.size() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((prefab == null) ? 0 : prefab.hashCode());
        result = prime * result + ((entities == null) ? 0 : entities.hashCode());

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

        EntityGroup other = (EntityGroup) object;

        if (className == null) {
            if (other.className != null)
                return false;
        } else if (!className.equals(other.className))
            return false;

        if (prefab == null) {
            if (other.prefab != null)
                return false;
        } else if (!prefab.equals(other.prefab))
            return false;

        if (entities == null) {
            if (other.entities != null)
                return false;
        } else if (!entities.equals(other.entities))
            return false;

        return true;
    }

    @Override
    public String serialize(int lineDepth) {
        String whitespaces = Whitespaces.get(lineDepth);
        String lineSeparator = System.lineSeparator();

        StringBuilder stringBuilder = new StringBuilder(whitespaces);
        stringBuilder.append("$grp ").append(className);

        if (prefab != null)
            stringBuilder.append(" : ").append(prefab.toEnfusionValue());

        stringBuilder.append(" {").append(lineSeparator);

        List<Entity> children;

        for (Entity entity : entities) {
            stringBuilder.append(whitespaces);

            if (entity.getName() != null)
                stringBuilder.append(' ')
                        .append(new EnfusionString(entity.getName()).toEnfusionValue());

            stringBuilder.append(" {").append(lineSeparator);

            for (ObjectProperty<? extends EnfusionObject> property : entity.getProperties())
                stringBuilder.append(property.serialize(lineDepth + 2)).append(lineSeparator);

            children = entity.getChildren();

            if (!children.isEmpty()) {
                stringBuilder.append(whitespaces).append("  {").append(lineSeparator);

                for (Entity child : children)
                    stringBuilder.append(child.serialize(lineDepth + 3));

                stringBuilder.append(whitespaces).append("  }").append(lineSeparator);
            }

            stringBuilder.append(whitespaces).append(" }").append(lineSeparator);
        }

        stringBuilder.append(whitespaces).append("}").append(lineSeparator);

        return stringBuilder.toString();
    }
}
