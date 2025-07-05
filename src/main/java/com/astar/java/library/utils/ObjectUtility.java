package com.astar.java.library.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;
import java.util.Set;

//TODO ADD MORE
public class ObjectUtility extends ObjectUtils {


    public static boolean isMatch(Object value, Object... target) {
        for (Object ele : target) {
            if (Objects.equals(value, ele)) return true;
        }
        return false;
    }
}
