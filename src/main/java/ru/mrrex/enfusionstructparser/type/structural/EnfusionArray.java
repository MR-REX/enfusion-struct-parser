package ru.mrrex.enfusionstructparser.type.structural;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class EnfusionArray<T extends EnfusionObject> extends ArrayList<T>
        implements EnfusionObject {

    @Override
    public String toString() {
        return "EnfusionArray [size=" + size() + "]";
    }

    @Override
    public String toEnfusionValue() {
        List<String> serializedValues =
                stream().map(value -> value.toEnfusionValue()).collect(Collectors.toList());

        return String.join(" ", serializedValues);
    }
}
