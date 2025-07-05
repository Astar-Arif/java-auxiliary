package com.astar.java.library.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;

//TODO ADD MORE
public abstract class RandomUtility extends RandomUtils {

    private final static RandomStringGenerator.Builder ALPHABET_BUILDER = new RandomStringGenerator.Builder().withinRange(
            32, 126);

    public static String genRandomInteger(Integer start, Integer end) {
//        TODO !!!!
        if (start == null || end == null) throw new IllegalArgumentException("Error type shi");
        RandomUtils rand = RandomUtils.secure();
        return String.valueOf(rand.randomInt(start, end + 1));
    }

    public static String genRandomString(int length) {
        return ALPHABET_BUILDER
                .get()
                .generate(length);
    }

    public static String genRandomString(int length, char[] arr) {
        return new RandomStringGenerator.Builder()
                .selectFrom(arr)
                .get()
                .generate(length);
    }
}
