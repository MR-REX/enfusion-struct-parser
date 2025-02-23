package ru.mrrex.enfusionstructparser.type.structure;

import java.util.ArrayList;
import java.util.List;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;

public class EntityLayer implements SerializableEnfusionObject {

    private String name;
    private List<Entity> entities;

    public EntityLayer(String name, List<Entity> entities) {
        this.name = name;
        this.entities = entities;
    }

    public EntityLayer(String name) {
        this(name, new ArrayList<>());
    }

    public EntityLayer(List<Entity> entities) {
        this(null, entities);
    }

    public EntityLayer() {
        this(null, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        if (entities == null)
            throw new IllegalArgumentException("Assigned list of entities must not be null");

        this.entities = entities;
    }

    public boolean hasEntities() {
        return !entities.isEmpty();
    }

    public boolean addEntity(Entity entity) {
        return entities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    public void clear() {
        entities.clear();
    }

    @Override
    public String toString() {
        String nameValue = (name != null) ? String.format("\"%s\"", name) : "null";
        return "EntityLayer [name=" + nameValue + ", entities=" + entities.size() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((name == null) ? 0 : name.hashCode());
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

        EntityLayer other = (EntityLayer) object;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
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
        StringBuilder stringBuilder = new StringBuilder();

        for (Entity entity : entities)
            stringBuilder.append(entity.serialize(lineDepth));

        return stringBuilder.toString();
    }
}
