package ru.mrrex.enfusionstructparser.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Whitespaces {
    
    private static final Map<Integer, String> cache = new HashMap<>();

    public static String get(int length) {
        String whitespaces = cache.get(length);

        if (whitespaces == null) {
            whitespaces = create(length);
            cache.put(length, whitespaces);
        }

        return whitespaces;
    }

    public static String create(int length) {
        return new String(new char[length]).replace("\0", " ");
    }
}
