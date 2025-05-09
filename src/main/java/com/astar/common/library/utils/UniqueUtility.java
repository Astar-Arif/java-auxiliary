package com.astar.common.library.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.UUIDGenerator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;

import java.math.BigInteger;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class UniqueUtility {
    private final AtomicReference<BigInteger> number = new AtomicReference<>(BigInteger.ZERO);


    private static final long SNOWFLAKE_EPOCH = Math.min(System.currentTimeMillis() - 10000L, 355827076000L);
    private static final short SNOWFLAKE_MACHINE_ID =  (short) 519; //10 bits
    private static final AtomicReference<Short> MACHINE_SEQUENCE_NUMBER = new AtomicReference<>((short)0);

    private static final byte SMI_BITS = 10;
    private static final byte MSN_BITS = 12;

    public <T extends Number> T getSequenceNumber(Class<T> clazz) {
        BigInteger res = number.getAndUpdate(n -> n.add(BigInteger.ONE));
        if (clazz == Integer.class) {
            return clazz.cast(res.intValue());
        } else if (clazz == Long.class) {
            return clazz.cast(res.longValue());
        } else if (clazz == Double.class) {
            return clazz.cast(res.doubleValue());
        } else if (clazz == Float.class) {
            return clazz.cast(res.floatValue());
        } else if (clazz == BigInteger.class) {
            return clazz.cast(res);
        } else {
            throw new IllegalArgumentException("Unsupported number class: " + clazz.getName());
        }
    }
    public static long generateSnowflake(){
        return ((System.currentTimeMillis() - SNOWFLAKE_EPOCH) << SMI_BITS | SNOWFLAKE_MACHINE_ID) << MSN_BITS | MACHINE_SEQUENCE_NUMBER.getAndUpdate(n -> (short)((n + 1) & 0x0FFF))   ;
    }
    // !UUID NOT THEORETICALLY UNIQUE, BUT WHATEVER
    public static UUID generateUUID(byte uuidVersion){
        return switch (uuidVersion) {
            case 1 -> Generators.timeBasedGenerator().generate();
            case 3 -> Generators.nameBasedGenerator().generate("ASTAR");
            case 4 -> Generators.randomBasedGenerator().generate();
            case 5 -> Generators.nameBasedGenerator(null, null).generate("ASTAR");
            case 6 -> Generators.timeBasedReorderedGenerator().generate();
            case 7 -> Generators.timeBasedEpochGenerator().generate();
            default -> Generators.randomBasedGenerator().generate(); // Default to random-based (v4) as fallback
        };
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println("ID : " + UniqueUtility.generateSnowflake());
        }
//        res               : 7324764711417835520
//        expected from wiki: 1541815603606036480
    }
}
