package com.astar.common.library.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO ADD MORE
public abstract class ArrayUtility extends ArrayUtils {


    public static <T extends Number> double sum(List<T> arr) {
        return arr.stream().mapToDouble(Number::doubleValue).sum();
    }

    public static <T extends Number> List<Double> addToElement(List<T> arr, double addition) {
        return arr.stream().map(ele -> (double) ele + addition).collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> multiplyToElement(List<T> arr, double multi) {
        return arr.stream().map(ele -> (double) ele * multi).collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> divideToElement(List<T> arr, double divide) {
        return arr.stream().map(ele -> (double) ele / divide).collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> powerToElement(List<T> arr, double pow) {
        return arr.stream().map(ele -> Math.pow((double) ele, pow)).collect(Collectors.toList());
    }

    public static <T extends Number> List<Double> mapToElement(
            List<T> arr, Function<T, Double> mapper) {
        return arr.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> List<T> slice(List<T> queryResult, int startIndex, int endIndex) {
        if (startIndex > endIndex)
            throw new IllegalArgumentException("Illegal startIndex or endIndex");
        try {
            return queryResult.subList(startIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return List.of();
        }
    }

    public static String byteArrToString(byte[] arr) {
        return ArrayUtils.toString(arr);
    }

    public static void main(String[] args) {
        String a = "hahaha";
        byte[] arr = a.getBytes();
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("");
        String b = ArrayUtils.toString(arr);
        System.out.println(b);
    }

    public static <T> List<T> toList(T[] arr) {
        return List.of(arr);

    }
}
