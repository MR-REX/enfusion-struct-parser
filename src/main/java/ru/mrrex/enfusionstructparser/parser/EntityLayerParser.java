package ru.mrrex.enfusionstructparser.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import ru.mrrex.enfusionstructparser.type.structure.EntityLayer;

public abstract class EntityLayerParser {

    public static EntityLayer parseNode(EntityTemplateNode node) {
        return new EntityLayer(EntityParser.parseNodes(node));
    }

    public static EntityLayer parseNodes(List<EntityTemplateNode> nodes) {
        return new EntityLayer(EntityParser.parseNodes(nodes));
    }

    public static EntityLayer parseNodes(EntityTemplateNode... nodes) {
        return new EntityLayer(EntityParser.parseNodes(nodes));
    }

    public static EntityLayer parseString(String string) {
        return new EntityLayer(EntityParser.parseString(string));
    }

    private static String getFileName(String fileName) {
        int dotPlacedAt = fileName.lastIndexOf('.');

        if (dotPlacedAt == -1)
            return fileName;

        return fileName.substring(0, dotPlacedAt);
    }

    public static EntityLayer parseFile(Path filePath) throws IOException {
        String entityLayerName = getFileName(filePath.getFileName().toString());
        return new EntityLayer(entityLayerName, EntityParser.parseFile(filePath));
    }

    public static EntityLayer parseFile(File file) throws IOException {
        String entityLayerName = getFileName(file.getName());
        return new EntityLayer(entityLayerName, EntityParser.parseFile(file));
    }
}
