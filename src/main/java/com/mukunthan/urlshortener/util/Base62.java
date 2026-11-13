package com.mukunthan.urlshortener.util;

public final class Base62 {

    private static final String CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = CHARS.length(); // 62

    private Base62() { } // utility class: nobody should create an object of it

    public static String encode(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        if (number == 0) {
            return String.valueOf(CHARS.charAt(0));
        }
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % BASE);
            sb.append(CHARS.charAt(remainder));
            number /= BASE;
        }
        return sb.reverse().toString();
    }

    public static long decode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code must not be empty");
        }
        long result = 0;
        for (char c : code.toCharArray()) {
            int index = CHARS.indexOf(c);
            if (index < 0) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
            result = result * BASE + index;
        }
        return result;
    }
}