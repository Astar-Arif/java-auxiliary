package com.astar.java.library;


import com.astar.java.library.utils.ArrayUtility;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayUtilsTest {

    @Test
    void testSum() {
        System.out.println("Running Java - Aux Test");
        List<Double> list = new ArrayList<>();
        list.add((double) 1);
        list.add((double) 2);
        list.add((double) 3);
        list.add((double) 4);
        list.add((double) 5);
        list.add((double) 6);
        double sum = 0;
        for (double ele : list) {
            sum += ele;
        }
        assertTrue(ArrayUtility.sum(list) == sum, ArrayUtility.sum(list) + " != " + sum);
    }

}
