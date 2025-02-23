package ru.mrrex.enfusionstructparser.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import ru.mrrex.enfusionstructparser.type.base.EnfusionObject;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionBool;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionFloat;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionInt;
import ru.mrrex.enfusionstructparser.type.primitive.EnfusionString;
import ru.mrrex.enfusionstructparser.type.structural.EnfusionArray;
import ru.mrrex.enfusionstructparser.type.structural.EnfusionVector;
import ru.mrrex.enfusionstructparser.util.Patterns;

public abstract class EnfusionObjectParser {

    public static EnfusionBool parseEnfusionBool(String enfusionValue) {
        return new EnfusionBool(!enfusionValue.equals("0"));
    }

    public static EnfusionInt parseEnfusionInt(String enfusionValue) {
        int intValue = Integer.parseInt(enfusionValue);
        return new EnfusionInt(intValue);
    }

    public static EnfusionFloat parseEnfusionFloat(String enfusionValue) {
        double doubleValue = Double.parseDouble(enfusionValue);
        return new EnfusionFloat(doubleValue);
    }

    public static EnfusionVector parseEnfusionVector(String enfusionValue) {
        Matcher matcher = Patterns.VECTOR_VALUE_PATTERN.matcher(enfusionValue);

        if (!matcher.find())
            return null;

        double x = Double.parseDouble(matcher.group("X"));
        double y = Double.parseDouble(matcher.group("Y"));
        double z = Double.parseDouble(matcher.group("Z"));

        return new EnfusionVector(x, y, z);
    }

    public static EnfusionArray<EnfusionObject> parseEnfusionArray(String enfusionValue) {
        Matcher matcher = Patterns.ARRAY_VALUE_PATTERN.matcher(enfusionValue);

        List<String> enfusionValues = new ArrayList<>();
        String findedEnfusionValue;

        while (matcher.find()) {
            findedEnfusionValue = matcher.group(0);

            if (findedEnfusionValue == null)
                findedEnfusionValue = matcher.group(1);

            enfusionValues.add(findedEnfusionValue);
        }

        EnfusionArray<EnfusionObject> array = new EnfusionArray<>();
        enfusionValues.forEach(value -> array.add(parsePrimitiveEnfusionValue(value)));

        return array;
    }

    public static EnfusionString parseEnfusionString(String enfusionValue) {
        String value = enfusionValue;
        
        if (value.startsWith("\"") && value.endsWith("\""))
            value = value.substring(1, value.length() - 1);

        return new EnfusionString(value);
    }

    public static EnfusionObject parsePrimitiveEnfusionValue(String enfusionValue) {
        if (Patterns.INT_VALUE_PATTERN.matcher(enfusionValue).matches())
            return parseEnfusionInt(enfusionValue);

        if (Patterns.FLOAT_VALUE_PATTERN.matcher(enfusionValue).matches())
            return parseEnfusionFloat(enfusionValue);

        if (Patterns.BOOL_VALUE_PATTERN.matcher(enfusionValue).matches())
            return parseEnfusionBool(enfusionValue);

        if (Patterns.STRING_VALUE_PATTERN.matcher(enfusionValue).matches())
            return parseEnfusionString(enfusionValue);

        return null;
    }

    public static EnfusionObject parseStructuralEnfusionValue(String enfusionValue) {
        Matcher matcher = Patterns.VECTOR_VALUE_PATTERN.matcher(enfusionValue);

        if (matcher.find())
            return parseEnfusionVector(enfusionValue);

        matcher = Patterns.ARRAY_VALUE_PATTERN.matcher(enfusionValue);

        if (matcher.find())
            return parseEnfusionArray(enfusionValue);

        return null;
    }

    public static EnfusionObject parseEnfusionValue(String enfusionValue) {
        EnfusionObject enfusionObject = parsePrimitiveEnfusionValue(enfusionValue);

        if (enfusionObject != null) 
            return enfusionObject;

        enfusionObject = parseStructuralEnfusionValue(enfusionValue);

        if (enfusionObject != null) 
            return enfusionObject;

        return null;
    }
}
