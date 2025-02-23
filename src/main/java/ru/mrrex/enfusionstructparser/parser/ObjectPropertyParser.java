package ru.mrrex.enfusionstructparser.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import ru.mrrex.enfusionstructparser.exception.NodeHeaderStructureException;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.structure.ObjectProperty;
import ru.mrrex.enfusionstructparser.util.Patterns;

public abstract class ObjectPropertyParser {

    public static ObjectProperty<? extends EnfusionObject> parseNode(EntityTemplateNode node) {
        String header = node.getHeader();
        Matcher matcher = Patterns.OBJECT_PROPERTY_PATTERN.matcher(header);

        if (!matcher.find())
            throw new NodeHeaderStructureException(header);

        String simpleKey = matcher.group("SimpleKey");
        String advancedKey = matcher.group("AdvancedKey");

        String key = (simpleKey != null) ? simpleKey : advancedKey;
        String enfusionValue = matcher.group("Value");

        return new ObjectProperty<>(key, EnfusionObjectParser.parseEnfusionValue(enfusionValue));
    }

    public static List<ObjectProperty<? extends EnfusionObject>> parseNodes(List<EntityTemplateNode> nodes) {
        return nodes.stream().map(ObjectPropertyParser::parseNode).collect(Collectors.toList());
    }

    public static List<ObjectProperty<? extends EnfusionObject>> parseNodes(EntityTemplateNode... nodes) {
        return parseNodes(Arrays.asList(nodes));
    }
}
