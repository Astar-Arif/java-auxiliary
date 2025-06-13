package com.astar.common.library.utils.math;

import java.util.List;

public abstract class MathUtility {

    public static <T extends Number> double sum(List<T> num) {
        double res = 0L;
        for (T ele : num) {
            res += ele.doubleValue();
        }
        return res;
    }
    public static long sum(byte[] num) {
        long res = 0L;
        for (int i = 0; i < num.length; i++) {
            res += num[i];
        }
        return res;
    }
    public static long sum(short[] num) {
        long res = 0L;
        for (int i = 0; i < num.length; i++) {
            res += num[i];
        }
        return res;
    }
    public static long sum(int[] num) {
        long res = 0L;
        for (int i = 0; i < num.length; i++) {
            res += num[i];
        }
        return res;
    }
    public static long sum(long[] num) {
        long res = 0L;
        for (int i = 0; i < num.length; i++) {
            res += num[i];
        }
        return res;
    }
    public static double sum(double[] num) {
        long res = 0L;
        for (int i = 0; i < num.length; i++) {
            res += num[i];
        }
        return res;
    }
    public static <T extends Number> T min(List<T> num) {
//        TODO ENHANCE
        if (num.isEmpty() || num.getFirst() == null) throw new IllegalArgumentException("FUCK YOU");
        T res = num.getFirst();
        for (T ele : num) {
            if (ele.doubleValue() < res.doubleValue()) {
                res = ele;
            }
        }
        return res;
    }
    public static byte min(byte[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        byte res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] < res) {
                res = num[i];
            }
        }
        return res;
    }
    public static short min(short[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        short res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] < res) {
                res = num[i];
            }
        }
        return res;
    }
    public static int min(int[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        int res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] < res) {
                res = num[i];
            }
        }
        return res;
    }
    public static long min(long[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        long res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] < res) {
                res = num[i];
            }
        }
        return res;
    }
    public static <T extends Number> T max(List<T> num) {
//        TODO ENHANCE
        if (num.isEmpty() || num.getFirst() == null) throw new IllegalArgumentException("FUCK YOU");
        T res = num.getFirst();
        for (T ele : num) {
            if (ele.doubleValue() > res.doubleValue()) {
                res = ele;
            }
        }
        return res;
    }
    public static byte max(byte[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        byte res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] > res) {
                res = num[i];
            }
        }
        return res;
    }
    public static short max(short[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        short res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] > res) {
                res = num[i];
            }
        }
        return res;
    }
    public static int max(int[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        int res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] > res) {
                res = num[i];
            }
        }
        return res;
    }
    public static long max(long[] num) {
        if (num.length == 0) throw new IllegalArgumentException("Fuck you");
        long res = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] > res) {
                res = num[i];
            }
        }
        return res;
    }

}
