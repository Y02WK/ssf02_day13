package main.day13.utils;

import java.util.UUID;

public class IDGenerator {
    public static String generateID(int numchars) {
        return UUID.randomUUID().toString().substring(0, numchars);
    }
}
