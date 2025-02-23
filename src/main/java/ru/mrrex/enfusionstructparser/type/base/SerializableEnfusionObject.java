package ru.mrrex.enfusionstructparser.type.base;

public interface SerializableEnfusionObject {

    String serialize(int lineDepth);

    default String serialize() {
        return serialize(0);
    }
}
