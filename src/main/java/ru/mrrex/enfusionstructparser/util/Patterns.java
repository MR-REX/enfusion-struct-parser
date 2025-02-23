package ru.mrrex.enfusionstructparser.util;

import java.util.regex.Pattern;

public abstract class Patterns {

        public static final Pattern CLASSNAME_PATTERN = Pattern.compile("[A-Za-z0-9_]+");

        public static final Pattern ENTITY_HEADER_PATTERN = Pattern.compile(
                        "^(?<ClassName>[\\w_]+)(\\s)?((?<Name>[\\w_]+)\\s)?(:\\s\\\"(?<PrefabPath>[\\w{}/.\\s]+)\\\")?");

        public static final Pattern ENTITY_GROUP_HEADER_PATTERN = Pattern.compile(
                "^\\$grp\\s(?<ClassName>[\\w_]+)(\\s)?(:\\s\\\"(?<PrefabPath>[\\w{}/.\\s]+)\\\")?");

        public static final Pattern OBJECT_PROPERTY_PATTERN = Pattern.compile(
                        "^(?<SimpleKey>(\\w+))?(?<AdvancedKey>(\\\"[\\w\\W]+\\\"))?\\s(?<Value>([\\w\\W]+))$");

        public static final Pattern OBJECT_COMPONENT_PATTERN = Pattern
                        .compile("^(?<Name>[A-z0-9_]+)(?<GUID>\\s\\\"\\{([0-9A-F]{16})\\}\\\")?");

        public static final Pattern BOOL_VALUE_PATTERN = Pattern.compile("^[01]$");
        public static final Pattern INT_VALUE_PATTERN = Pattern.compile("[\\-0-9]+");
        public static final Pattern FLOAT_VALUE_PATTERN = Pattern.compile("[\\-0-9\\.]+");
        public static final Pattern STRING_VALUE_PATTERN = Pattern.compile("\"[\\w\\W]*\"");

        public static final Pattern VECTOR_VALUE_PATTERN = Pattern
                        .compile("^(?<X>[\\-0-9\\.]+)\\s(?<Y>[\\-0-9\\.]+)\\s(?<Z>[\\-0-9\\.]+)$");

        public static final Pattern ARRAY_VALUE_PATTERN =
                        Pattern.compile("\\\"(.*?)\\\"|([\\-\\d\\.]+)");
}
