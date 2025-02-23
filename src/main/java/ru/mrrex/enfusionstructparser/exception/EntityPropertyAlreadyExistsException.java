package ru.mrrex.enfusionstructparser.exception;

import ru.mrrex.enfusionstructparser.type.structure.Entity;
import ru.mrrex.enfusionstructparser.type.structure.ObjectProperty;

public class EntityPropertyAlreadyExistsException extends RuntimeException {

    public EntityPropertyAlreadyExistsException(Entity entity, ObjectProperty<?> property) {
        super(String.format("Attempt to add a property for \"%s\" with duplicate key \"%s\"",
                entity, property.getKey()));
    }
}
