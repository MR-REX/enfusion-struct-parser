package ru.mrrex.enfusionstructparser.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import ru.mrrex.enfusionstructparser.exception.EntityTemplateParsingException;
import ru.mrrex.enfusionstructparser.type.advanced.Prefab;
import ru.mrrex.enfusionstructparser.util.Patterns;

public abstract class EntityTemplateParser {

    private static String getEntityNodeHeader(String className, String name, Prefab prefab) {
        StringBuilder stringBuilder = new StringBuilder(className);

        if (name != null)
            stringBuilder.append(' ').append(name);

        if (prefab != null)
            stringBuilder.append(" : ").append(prefab.toEnfusionValue());

        return stringBuilder.toString();
    }

    private static List<EntityTemplateNode> unwrapGroup(EntityTemplateNode node) {
        String header = node.getHeader();
        Matcher matcher = Patterns.ENTITY_GROUP_HEADER_PATTERN.matcher(header);

        if (!matcher.find())
            throw new EntityTemplateParsingException("Failed to unwrap group: " + header);

        String className = matcher.group("ClassName");

        String prefabPath = matcher.group("PrefabPath");
        Prefab prefab = (prefabPath != null) ? new Prefab(prefabPath) : null;

        List<EntityTemplateNode> unwrappedNodes = new ArrayList<>();
        String entityNodeName, entityNodeHeader;

        for (EntityTemplateNode childNode : node.getChildren()) {
            entityNodeName = childNode.getHeader();

            if (entityNodeName != null) {
                entityNodeName = entityNodeName.replace("$grp", "").trim();

                if (entityNodeName.startsWith("\""))
                    entityNodeName = entityNodeName.substring(1, entityNodeName.length() - 1);
            }

            entityNodeHeader = getEntityNodeHeader(className, entityNodeName, prefab);
            unwrappedNodes.add(new EntityTemplateNode(entityNodeHeader, childNode.getChildren()));
        }

        return unwrappedNodes;
    }

    private static List<EntityTemplateNode> unwrapGroups(List<EntityTemplateNode> nodes) {
        List<EntityTemplateNode> unwrappedNodes = new ArrayList<>();

        EntityTemplateNode node;
        String nodeHeader;

        for (int i = 0; i < nodes.size(); i++) {
            node = nodes.get(i);
            nodeHeader = node.getHeader();

            if (nodeHeader == null || !nodeHeader.startsWith("$grp")) {
                unwrappedNodes.add(node);
                continue;
            }

            for (EntityTemplateNode unwrappedNode : unwrapGroup(node))
                unwrappedNodes.add(unwrappedNode);
        }

        return unwrappedNodes;
    }

    public static List<EntityTemplateNode> parseString(String string)
            throws EntityTemplateParsingException {
        List<EntityTemplateNode> globalNodes = new ArrayList<>();
        Stack<EntityTemplateNode> nodes = new Stack<>();

        try (Scanner scanner = new Scanner(string)) {
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.endsWith("{")) {
                    String header = line.substring(0, line.lastIndexOf("{")).trim();

                    if (header.isEmpty())
                        header = null;

                    EntityTemplateNode nextNode = new EntityTemplateNode(header, new ArrayList<>());
                    nodes.push(nextNode);

                    continue;
                }

                if (line.endsWith("}")) {
                    EntityTemplateNode currentNode = nodes.pop();

                    if (nodes.empty())
                        globalNodes.add(currentNode);
                    else
                        nodes.peek().addChild(currentNode);

                    continue;
                }

                EntityTemplateNode nextNode = new EntityTemplateNode(line.trim());
                nodes.peek().addChild(nextNode);
            }
        }

        if (!nodes.empty())
            throw new EntityTemplateParsingException(
                    "Nodes stack is not empty at the end of parsing process");

        return unwrapGroups(globalNodes);
    }

    public static List<EntityTemplateNode> parseFile(Path filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileContent = new String(fileBytes, StandardCharsets.UTF_8);

        return parseString(fileContent);
    }

    public static List<EntityTemplateNode> parseFile(File file) throws IOException {
        return parseFile(file.toPath());
    }
}
