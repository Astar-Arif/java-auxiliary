package com.astar.common.library.utils.enums;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public enum TimeScaleUnit {
    PLANCK_TIME("tₚ", new BigDecimal("5.39116E-44")),
    YOCTOSECOND("ys", new BigDecimal("1E-24")),
    ZEPTOSECOND("zs", new BigDecimal("1E-21")),
    ATTOSECOND("as", new BigDecimal("1E-18")),
    FEMTOSECOND("fs", new BigDecimal("1E-15")),
    PICOSECOND("ps", new BigDecimal("1E-12")),
    NANOSECOND("ns", new BigDecimal("1E-9")),
    MICROSECOND("µs", new BigDecimal("1E-6")),
    MILLISECOND("ms", new BigDecimal("1E-3")),
    CENTISECOND("cs", new BigDecimal("1E-2")),
    DECISECOND("ds", new BigDecimal("1E-1")),
    SECOND("s", BigDecimal.ONE),
    MINUTE("min", new BigDecimal("60")),
    HOUR("h", new BigDecimal("3600")),
    WATCH("watch", new BigDecimal("14400")),
    QUARTER_DAY("qd", new BigDecimal("21600")),
    HALF_DAY("hd", new BigDecimal("43200")),
    DAY("d", new BigDecimal("86400")),
    WEEK("wk", new BigDecimal("604800")),
    FORTNIGHT("fortnight", new BigDecimal("1209600")),
    MONTH("mo", new BigDecimal("2629746")),
    LUNAR_MONTH("lunar", new BigDecimal("2551443")),
    QUARTER("quarter", new BigDecimal("7889238")),
    SEASON("season", new BigDecimal("7889238")),
    SEMESTER("semester", new BigDecimal("15778476")),
    YEAR("yr", new BigDecimal("31556952")),
    TROPICAL_YEAR("trop_yr", new BigDecimal("31556925")),
    SIDEREAL_YEAR("sid_yr", new BigDecimal("31558150")),
    LEAP_YEAR("leap_yr", new BigDecimal("31622400")),
    OLYMPIAD("olympiad", new BigDecimal("126227808")),
    LUSTRUM("lustrum", new BigDecimal("157784760")),
    DECADE("decade", new BigDecimal("315569520")),
    INDICTION("indiction", new BigDecimal("473354280")),
    SCORE("score", new BigDecimal("631139040")),
    GENERATION("generation", new BigDecimal("946708560")),
    CENTURY("century", new BigDecimal("3155695200")),
    MILLENNIUM("millennium", new BigDecimal("31556952000")),
    COSMIC_YEAR("cosmic_yr", new BigDecimal("7.26E15")),
    EON("eon", new BigDecimal("31556952000000")),
    KE("刻/kè", new BigDecimal("900")),
    GENG("更/gēng", new BigDecimal("7200")),
    SHI_CHEN("时辰/shíchén", new BigDecimal("7200")),
    XUNXI("旬/xún", new BigDecimal("864000")),
    GHATI("ghati", new BigDecimal("1440")),
    MUHURTA("muhurta", new BigDecimal("2880")),
    NAKSHATRA_DAY("nakshatrad", new BigDecimal("86995")),
    PAKSHA("paksha", new BigDecimal("1290000")),
    WAQT("waqt", new BigDecimal("3600")),
    QAMARI_MONTH("qamari", new BigDecimal("2551443")),
    HIJRI_YEAR("hijri_yr", new BigDecimal("30617316")),
    ANCIENT_HOUR("anc_hour", new BigDecimal("3600")),
    SEASONAL_HOUR("seas_hour", new BigDecimal("3600")),


    CPU_CYCLE("cycle", new BigDecimal("1E-9")),
    JIFFY("jiffy", new BigDecimal("0.01")),


    MOMENT("moment", new BigDecimal("90")),
    INSTANT("instant", new BigDecimal("0.1")),
    SHAKE("shake", new BigDecimal("1E-8")),
    BLINK("blink", new BigDecimal("0.3")),


    EPOCH("epoch", BigDecimal.ONE),

    CUSTOM("custom", BigDecimal.ONE);                    // Placeholder for custom units


    private final String symbol;
    private final BigDecimal toSecondsFactor;
    private static final Map<String, TimeScaleUnit> symbolMap = new HashMap<>();

    static {
        for (TimeScaleUnit unit : values()) {
            symbolMap.put(unit.symbol.toLowerCase(), unit);
        }
    }

    TimeScaleUnit(String symbol, BigDecimal toSecondsFactor) {
        this.symbol = symbol;
        this.toSecondsFactor = toSecondsFactor;
    }

    /**
     * Converts a value in this unit to seconds
     */
    public BigDecimal toSeconds(BigDecimal value) {
        return value.multiply(toSecondsFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value in seconds to this unit
     */
    public BigDecimal fromSeconds(BigDecimal seconds) {
        return seconds.divide(toSecondsFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value from one time unit to another
     */
    public static BigDecimal convert(BigDecimal value, TimeScaleUnit from, TimeScaleUnit to) {
        BigDecimal seconds = from.toSeconds(value);
        return to.fromSeconds(seconds);
    }

    /**
     * Formats a time value with its unit symbol
     */
    public String format(BigDecimal value) {
        return value.toString() + " " + symbol;
    }

    /**
     * Formats a time value with its unit symbol, using specified precision
     */
    public String format(BigDecimal value, int precision) {
        return value.setScale(precision, RoundingMode.HALF_UP).toString() + " " + symbol;
    }

    /**
     * Gets a TimeScaleUnit by its symbol
     */
    public static TimeScaleUnit fromSymbol(String symbol) {
        TimeScaleUnit unit = symbolMap.get(symbol.toLowerCase());
        if (unit == null) {
            throw new IllegalArgumentException("Unknown time unit symbol: " + symbol);
        }
        return unit;
    }

    /**
     * Returns the unit symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the conversion factor to seconds
     */
    public BigDecimal getConversionFactor() {
        return toSecondsFactor;
    }

    /**
     * Returns true if this is a standard SI unit
     */
    public boolean isStandardUnit() {
        return this.ordinal() <= YEAR.ordinal();
    }

    /**
     * Returns true if this is a very small unit (less than a millisecond)
     */
    public boolean isMicroscopic() {
        return this.compareTo(MILLISECOND) < 0;
    }

    /**
     * Returns true if this is a very large unit (larger than a century)
     */
    public boolean isAstronomical() {
        return this.compareTo(CENTURY) > 0;
    }

    public static TimeScaleUnit createCustomUnit(String symbol, BigDecimal toSecondsFactor) {
        try {
            java.lang.reflect.Field symbolField = TimeScaleUnit.class.getDeclaredField("symbol");
            symbolField.setAccessible(true);
            symbolField.set(CUSTOM, symbol);

            java.lang.reflect.Field factorField = TimeScaleUnit.class.getDeclaredField("toSecondsFactor");
            factorField.setAccessible(true);
            factorField.set(CUSTOM, toSecondsFactor);

            return CUSTOM;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create custom unit", e);
        }
    }
}