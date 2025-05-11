package com.astar.common.library.utils.math;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * <p>
 * Byte.MIN_VALUE : -128
 * Byte.MAX_VALUE : 127
 * </p>
 * -
 * <p>
 * Short.MIN_VALUE : -32768
 * Short.MAX_VALUE : 32767
 * </p>
 * -
 * <p>
 * Integer.MIN_VALUE : -2147483648
 * Integer.MAX_VALUE : 2147483647
 * </p>
 * -
 * <p>
 * Long.MIN_VALUE : -9223372036854775808
 * Long.MAX_VALUE : 9223372036854775807
 * </p>
 * -
 * <p>
 * Float.MIN_VALUE : 1.4E-45
 * Float.MAX_VALUE : 3.4028235E38
 * </p>
 * -
 * <p>
 * Double.MIN_VALUE : 4.9E-324
 * Double.MAX_VALUE : 1.7976931348623157E308
 * </p>
 * -
 * <p>
 * Character.MIN_VALUE : '\u0000'
 * Character.MAX_VALUE : '\uFFFF'
 * </p>
 */

public abstract class NumberUtility extends NumberUtils {

    public static Logger LOGGER = LoggerFactory.getLogger(NumberUtility.class);

    public static final char[] CHARACTER_SET = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };


    public static String toBase(BigInteger number, byte base) throws Exception {
        if (number == null) throw new Exception("Number is null");
        if (base < 2 || base > 62) throw new Exception("Invalid Base Number");
        LOGGER.info("Converting {} to Base {}", number, base);
        if (number.equals(BigInteger.ZERO)) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divAndRemainder = number.divideAndRemainder(BigInteger.valueOf(base));
            number = divAndRemainder[0];
            int remainder = divAndRemainder[1].intValue();
            result.append(CHARACTER_SET[remainder]);
        }
        return result.reverse().toString();
    }


}
