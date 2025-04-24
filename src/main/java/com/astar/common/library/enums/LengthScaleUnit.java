package com.astar.common.library.enums;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public enum LengthScaleUnit {
    PLANCK_LENGTH("lₚ", new BigDecimal("1.616255E-35")),
    YOCTOMETER("ym", new BigDecimal("1E-24")),
    ZEPTOMETER("zm", new BigDecimal("1E-21")),
    ATTOMETER("am", new BigDecimal("1E-18")),
    FEMTOMETER("fm", new BigDecimal("1E-15")),
    PICOMETER("pm", new BigDecimal("1E-12")),
    NANOMETER("nm", new BigDecimal("1E-9")),
    MICROMETER("µm", new BigDecimal("1E-6")),
    MILLIMETER("mm", new BigDecimal("1E-3")),
    CENTIMETER("cm", new BigDecimal("1E-2")),
    DECIMETER("dm", new BigDecimal("1E-1")),
    METER("m", BigDecimal.ONE),
    DEKAMETER("dam", new BigDecimal("1E1")),
    HECTOMETER("hm", new BigDecimal("1E2")),
    KILOMETER("km", new BigDecimal("1E3")),
    MEGAMETER("Mm", new BigDecimal("1E6")),
    GIGAMETER("Gm", new BigDecimal("1E9")),
    TERAMETER("Tm", new BigDecimal("1E12")),
    PETAMETER("Pm", new BigDecimal("1E15")),

    INCH("in", new BigDecimal("0.0254")),
    FOOT("ft", new BigDecimal("0.3048")),
    YARD("yd", new BigDecimal("0.9144")),
    MILE("mi", new BigDecimal("1609.344")),
    NAUTICAL_MILE("nmi", new BigDecimal("1852")),

    ASTRONOMICAL_UNIT("au", new BigDecimal("1.495978707E11")),
    LIGHT_YEAR("ly", new BigDecimal("9.4607E15")),
    PARSEC("pc", new BigDecimal("3.085677581E16")),
    KILOPARSEC("kpc", new BigDecimal("3.085677581E19")),
    MEGAPARSEC("Mpc", new BigDecimal("3.085677581E22")),

    CHINESE_CUN("cun", new BigDecimal("0.033333")),
    CHINESE_ZHANG("zhang", new BigDecimal("3.3333")),
    CHINESE_LI("li", new BigDecimal("500")),

    ANCIENT_CUBIT("cubit", new BigDecimal("0.4572")),
    ROMAN_PES("pes", new BigDecimal("0.296")),
    EGYPTIAN_CUBIT("egy_cubit", new BigDecimal("0.5236")),

    CUSTOM("custom", BigDecimal.ONE); // Placeholder for custom units

    private final String symbol;
    private final BigDecimal toMetersFactor;
    private static final Map<String, LengthScaleUnit> symbolMap = new HashMap<>();

    static {
        for (LengthScaleUnit unit : values()) {
            symbolMap.put(unit.symbol.toLowerCase(), unit);
        }
    }

    LengthScaleUnit(String symbol, BigDecimal toMetersFactor) {
        this.symbol = symbol;
        this.toMetersFactor = toMetersFactor;
    }

    /**
     * Converts a value in this unit to meters
     */
    public BigDecimal toMeters(BigDecimal value) {
        return value.multiply(toMetersFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value in meters to this unit
     */
    public BigDecimal fromMeters(BigDecimal meters) {
        return meters.divide(toMetersFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value from one length unit to another
     */
    public static BigDecimal convert(BigDecimal value, LengthScaleUnit from, LengthScaleUnit to) {
        BigDecimal meters = from.toMeters(value);
        return to.fromMeters(meters);
    }

    /**
     * Formats a length value with its unit symbol
     */
    public String format(BigDecimal value) {
        return value.toString() + " " + symbol;
    }

    /**
     * Formats a length value with specified precision
     */
    public String format(BigDecimal value, int precision) {
        return value.setScale(precision, RoundingMode.HALF_UP).toString() + " " + symbol;
    }

    /**
     * Gets a LengthScaleUnit by its symbol
     */
    public static LengthScaleUnit fromSymbol(String symbol) {
        LengthScaleUnit unit = symbolMap.get(symbol.toLowerCase());
        if (unit == null) {
            throw new IllegalArgumentException("Unknown length unit symbol: " + symbol);
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
     * Returns the conversion factor to meters
     */
    public BigDecimal getConversionFactor() {
        return toMetersFactor;
    }

    /**
     * Returns true if this is a standard SI unit
     */
    public boolean isStandardUnit() {
        return this.ordinal() <= METER.ordinal();
    }

    /**
     * Returns true if this is a microscopic unit (less than a micrometer)
     */
    public boolean isMicroscopic() {
        return this.compareTo(MICROMETER) < 0;
    }

    /**
     * Returns true if this is an astronomical unit (larger than a kilometer)
     */
    public boolean isAstronomical() {
        return this.compareTo(KILOMETER) > 0;
    }

    /**
     * Dynamically creates a custom unit
     */
    public static LengthScaleUnit createCustomUnit(String symbol, BigDecimal toMetersFactor) {
        try {
            java.lang.reflect.Field symbolField = LengthScaleUnit.class.getDeclaredField("symbol");
            symbolField.setAccessible(true);
            symbolField.set(CUSTOM, symbol);

            java.lang.reflect.Field factorField = LengthScaleUnit.class.getDeclaredField(
                    "toMetersFactor");
            factorField.setAccessible(true);
            factorField.set(CUSTOM, toMetersFactor);

            return CUSTOM;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create custom unit", e);
        }
    }
}
