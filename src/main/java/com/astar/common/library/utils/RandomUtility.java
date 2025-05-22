package com.astar.common.library.utils;

import com.astar.common.library.pojo.Base;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;
import java.util.function.Supplier;

//TODO ADD MORE
public abstract class RandomUtility extends RandomUtils {

    public static String genRandomInteger(Integer start, Integer end) {
//        TODO !!!!
        if (start == null || end == null) throw new IllegalArgumentException("Error type shi");
        RandomUtils rand = RandomUtils.secure();
        return String.valueOf(rand.randomInt(start, end + 1));
    }

    public static String genRandomString(int length){
        return new RandomStringGenerator.Builder()
                .withinRange(32,126)
                .get()
                .generate(length);
    }

    public static String genRandomString(int length, char[] arr){
        return new RandomStringGenerator.Builder()
                .selectFrom(arr)
                .get()
                .generate(length);
    }
}
