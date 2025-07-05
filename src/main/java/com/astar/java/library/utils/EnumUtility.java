package com.astar.java.library.utils;

import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class EnumUtility extends EnumUtils {
    public static <E extends Enum<E>> List<E> ToEnumArray(
            List<String> list, Class<E> clazz, boolean isIgnoreInvalid) {
        List<E> resultList = new ArrayList<>();
        for (String ele : list) {
            E enumEle = null;
            try {
                enumEle = Enum.valueOf(clazz, ele);
//                TODO MAKE BETTER
            } catch (IllegalArgumentException e) {
                if (isIgnoreInvalid) continue;
            }
            resultList.add(enumEle);
        }
        return resultList;
    }
}
