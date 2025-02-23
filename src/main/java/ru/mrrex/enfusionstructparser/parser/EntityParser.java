package ru.mrrex.enfusionstructparser.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.mrrex.enfusionstructparser.exception.EntityTemplateParsingException;
import ru.mrrex.enfusionstructparser.exception.NodeHeaderStructureException;
import ru.mrrex.enfusionstructparser.type.advanced.Prefab;
import ru.mrrex.enfusionstructparser.type.structure.Entity;
import ru.mrrex.enfusionstructparser.util.Patterns;

public abstract class EntityParser {

    public static Entity parseNode(EntityTemplateNode node) {
        if (node.getChildren() == null)
            throw new EntityTemplateParsingException(
                    "Entity template node is missing a block with child nodes");

        String header = node.getHeader();
        Matcher matcher = Patterns.ENTITY_HEADER_PATTERN.matcher(header);

        if (!matcher.find())
            throw new NodeHeaderStructureException(header);

        String className = matcher.group("ClassName");
        String name = matcher.group("Name");

        String prefabPath = matcher.group("PrefabPath");
        Prefab prefab = (prefabPath != null) ? new Prefab(prefabPath) : null;

        Entity entity = new Entity(className, name, prefab);
        String childNodeHeader;

        for (EntityTemplateNode childNode : node.getChildren()) {
            childNodeHeader = childNode.getHeader();

            if (childNodeHeader == null) {
                for (EntityTemplateNode childEntityNode : childNode.getChildren())
                    entity.addChild(parseNode(childEntityNode));

                continue;
            }

            if (childNodeHeader.equals("components")) {

                continue;
            }

            entity.addProperty(ObjectPropertyParser.parseNode(childNode));
        }

        return entity;
    }

    public static List<Entity> parseNodes(EntityTemplateNode... nodes) {
        return Stream.of(nodes).map(node -> parseNode(node)).collect(Collectors.toList());
    }

    public static List<Entity> parseNodes(List<EntityTemplateNode> nodes) {
        return nodes.stream().map(node -> parseNode(node)).collect(Collectors.toList());
    }

    public static List<Entity> parseString(String string) {
        return parseNodes(EntityTemplateParser.parseString(string));
    }

    public static List<Entity> parseFile(Path filePath) throws IOException {
        return parseNodes(EntityTemplateParser.parseFile(filePath));
    }

    public static List<Entity> parseFile(File file) throws IOException {
        return parseNodes(EntityTemplateParser.parseFile(file));
    }
}
