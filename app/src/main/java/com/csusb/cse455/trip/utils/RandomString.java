package com.csusb.cse455.trip.utils;

import java.util.Random;

// Generates a random string of specified length.
public class RandomString {
    // Holds an array of available symbols.
    private static final char[] symbols;

    // Generates symbols.
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    // Random generator.
    private final Random random = new Random();

    // Symbol buffer.
    private final char[] buf;

    // Constructor.
    public RandomString(int length) {
        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
    }

    // Get the next random string.
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}