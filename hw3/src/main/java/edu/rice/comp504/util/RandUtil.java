package edu.rice.comp504.util;

public class RandUtil {
    public static int getRnd(int base, int limit) {
        return (int)Math.floor(Math.random() * limit + base);
    }
}
