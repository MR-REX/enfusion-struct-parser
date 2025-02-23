package ru.mrrex.enfusionstructparser.type.advanced;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;

public class Prefab implements EnfusionObject {

    private static final Pattern GUID_PATTERN = Pattern.compile("^\\{([0-9A-Fa-f]{16})\\}");

    private Path path;

    public Prefab(Path path) {
        this.path = path;
    }

    public Prefab(String path) {
        this.path = Paths.get(path);
    }

    public Prefab() {
        path = null;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        if (path == null)
            return null;

        String fileName = path.getFileName().toString();
        int extensionStartsAt = fileName.indexOf(".");

        if (extensionStartsAt != -1)
            fileName = fileName.substring(0, extensionStartsAt);

        return fileName;
    }

    public GUID getGUID() {
        Matcher matcher = GUID_PATTERN.matcher(path.toString());

        if (!matcher.find())
            return null;

        return GUID.fromString(matcher.group(0));
    }

    public Path getPathWithoutGUID() {
        String stringPath = path.toString();
        return Paths.get(stringPath.substring(stringPath.indexOf('}') + 1));
    }

    @Override
    public String toString() {
        String nameValue = getName();
        nameValue = (nameValue != null) ? String.format("\"%s\"", nameValue) : "null";

        String pathValue = (path != null) ? String.format("\"%s\"", path) : "null";

        return "Prefab [name=" + nameValue + ", path=" + pathValue + "]";
    }

    @Override
    public int hashCode() {
        return 31 * 1 + ((path == null) ? 0 : path.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null)
            return false;

        if (getClass() != object.getClass())
            return false;

        Prefab other = (Prefab) object;

        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;

        return true;
    }

    @Override
    public String toEnfusionValue() {
        GUID guid = getGUID();
        String serializedGuid = (guid != null) ? guid.toEnfusionValue() : "";
        String pathWithoutGUID = getPathWithoutGUID().toString().replace("\\", "/");

        return String.format("\"%s%s\"", serializedGuid, pathWithoutGUID);
    }
}
