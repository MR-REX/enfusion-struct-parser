package ru.mrrex.enfusionstructparser.parser;

import java.util.List;
import ru.mrrex.enfusionstructparser.type.base.SerializableEnfusionObject;
import ru.mrrex.enfusionstructparser.util.Whitespaces;

public class EntityTemplateNode implements SerializableEnfusionObject {

    private String header;
    private List<EntityTemplateNode> children;

    public EntityTemplateNode(String header, List<EntityTemplateNode> children) {
        this.header = header;
        this.children = children;
    }

    public EntityTemplateNode(String header) {
        this(header, null);
    }

    public EntityTemplateNode(List<EntityTemplateNode> children) {
        this(null, children);
    }

    public EntityTemplateNode() {
        this(null, null);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<EntityTemplateNode> getChildren() {
        return children;
    }

    public void setChildren(List<EntityTemplateNode> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public boolean addChild(EntityTemplateNode entityTemplateNode) {
        if (children == null)
            throw new IllegalStateException("Node does not contain a block of child nodes");

        return children.add(entityTemplateNode);
    }

    public boolean removeChild(EntityTemplateNode entityTemplateNode) {
        if (children == null)
            throw new IllegalStateException("Node does not contain a block of child nodes");

        return children.remove(entityTemplateNode);
    }

    public void clear() {
        if (children == null)
            throw new IllegalStateException("Node does not contain a block of child nodes");

        children.clear();
    }

    @Override
    public String toString() {
        String headerValue = (header != null) ? String.format("\"%s\"", header) : "null";
        String childrenValue = (children != null) ? "" + children.size() : "null";

        return "EntityTemplateNode [header=" + headerValue + ", children=" + childrenValue + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((header == null) ? 0 : header.hashCode());
        result = prime * result + ((children == null) ? 0 : children.hashCode());

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

        EntityTemplateNode other = (EntityTemplateNode) object;

        if (header == null) {
            if (other.header != null)
                return false;
        } else if (!header.equals(other.header))
            return false;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;

        return true;
    }

    @Override
    public String serialize(int lineDepth) {
        StringBuilder stringBuilder = new StringBuilder();

        String whitespaces = Whitespaces.get(lineDepth);
        stringBuilder.append(whitespaces);

        if (header != null)
            stringBuilder.append(header);

        if (children != null) {
            if (header != null)
                stringBuilder.append(" ");

            stringBuilder.append("{").append(System.lineSeparator());

            for (EntityTemplateNode node : children)
                stringBuilder.append(node.serialize(lineDepth + 1));

            stringBuilder.append(whitespaces).append("}");
        }

        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
