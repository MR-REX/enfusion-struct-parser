package ru.mrrex.enfusionstructparser.parser;

import java.util.regex.Matcher;
import ru.mrrex.enfusionstructparser.exception.NodeHeaderStructureException;
import ru.mrrex.enfusionstructparser.type.advanced.GUID;
import ru.mrrex.enfusionstructparser.type.structure.ObjectComponent;
import ru.mrrex.enfusionstructparser.util.Patterns;

public abstract class ObjectComponentParser {

    public static ObjectComponent parseNode(EntityTemplateNode node) {
        String header = node.getHeader();
        Matcher matcher = Patterns.OBJECT_COMPONENT_PATTERN.matcher(header);

        if (!matcher.find())
            throw new NodeHeaderStructureException(header);

        String name = matcher.group("Name");

        String guidValue = matcher.group("GUID");
        GUID guid = (guidValue != null) ? GUID.fromString(guidValue) : null;

        ObjectComponent objectComponent = new ObjectComponent(name, guid);

        for (EntityTemplateNode childNode : node.getChildren()) {
            if (childNode.hasChildren()) {
                objectComponent.addObject(ObjectComponentParser.parseNode(childNode));
                continue;
            }

            objectComponent.addObject(ObjectPropertyParser.parseNode(childNode));
        }

        return objectComponent;
    }
}
