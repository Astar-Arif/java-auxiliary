package com.astar.common.library.utils.enums;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * A comprehensive enum of mass (weight) units from various scientific, historical,
 * and cultural contexts, with conversion capabilities.
 * All conversions are based on the kilogram (kg) as the reference unit.
 */
public enum MassScaleUnit {
    // Metric system - SI and derivatives
    YOCTOGRAM("yg", new BigDecimal("1E-27")),             // 10^-24 gram
    ZEPTOGRAM("zg", new BigDecimal("1E-24")),             // 10^-21 gram
    ATTOGRAM("ag", new BigDecimal("1E-21")),              // 10^-18 gram
    FEMTOGRAM("fg", new BigDecimal("1E-18")),             // 10^-15 gram
    PICOGRAM("pg", new BigDecimal("1E-15")),              // 10^-12 gram
    NANOGRAM("ng", new BigDecimal("1E-12")),              // 10^-9 gram
    MICROGRAM("µg", new BigDecimal("1E-9")),              // 10^-6 gram
    MILLIGRAM("mg", new BigDecimal("1E-6")),              // 10^-3 gram
    CENTIGRAM("cg", new BigDecimal("1E-5")),              // 10^-2 gram
    DECIGRAM("dg", new BigDecimal("1E-4")),               // 10^-1 gram
    GRAM("g", new BigDecimal("1E-3")),                    // Base SI unit
    DECAGRAM("dag", new BigDecimal("1E-2")),              // 10 grams
    HECTOGRAM("hg", new BigDecimal("1E-1")),              // 100 grams
    KILOGRAM("kg", BigDecimal.ONE),                       // SI base unit
    METRIC_POUND("metric_lb", new BigDecimal("0.5")),     // 500 grams
    MYRIAGRAM("myg", new BigDecimal("10")),               // 10 kg
    QUINTAL("q", new BigDecimal("100")),                  // 100 kg
    TONNE("t", new BigDecimal("1000")),                   // 1000 kg (metric ton)
    MEGATONNE("Mt", new BigDecimal("1E9")),               // Million tonnes
    GIGATONNE("Gt", new BigDecimal("1E12")),              // Billion tonnes

    // Atomic mass units
    DALTON("Da", new BigDecimal("1.66053906660E-27")),    // Atomic mass unit
    ELECTRON_MASS("me", new BigDecimal("9.1093837015E-31")), // Electron mass in kg

    // Avoirdupois (US/Imperial)
    GRAIN("gr", new BigDecimal("6.47989E-5")),            // 1/7000 pound
    DRAM("dr", new BigDecimal("1.7718451953125E-3")),     // 1/16 ounce
    OUNCE("oz", new BigDecimal("0.028349523125")),        // 1/16 pound
    POUND("lb", new BigDecimal("0.45359237")),            // 16 ounces
    STONE("st", new BigDecimal("6.35029318")),            // 14 pounds
    QUARTER("qr", new BigDecimal("12.70058636")),         // 28 pounds
    HUNDREDWEIGHT_US("cwt_us", new BigDecimal("45.359237")), // 100 pounds (US)
    HUNDREDWEIGHT_UK("cwt_uk", new BigDecimal("50.80234544")), // 112 pounds (UK)
    TON_US("ton_us", new BigDecimal("907.18474")),         // 2000 pounds (US)
    TON_UK("ton_uk", new BigDecimal("1016.0469088")),      // 2240 pounds (UK)

    // Troy weight (precious metals)
    GRAIN_TROY("gr_t", new BigDecimal("6.47989E-5")),     // Same as avoirdupois grain
    PENNYWEIGHT("dwt", new BigDecimal("1.55517384E-3")),  // 24 grains
    OUNCE_TROY("oz_t", new BigDecimal("0.0311034768")),   // 20 pennyweights
    POUND_TROY("lb_t", new BigDecimal("0.3732417216")),   // 12 troy ounces

    // Apothecaries' (pharmaceutical)
    SCRUPLE("s", new BigDecimal("1.2959782E-3")),         // 20 grains
    DRAM_APO("dr_ap", new BigDecimal("3.8879346E-3")),    // 3 scruples
    OUNCE_APO("oz_ap", new BigDecimal("0.0311034768")),   // 8 drams (same as troy ounce)
    POUND_APO("lb_ap", new BigDecimal("0.3732417216")),   // 12 ounces (same as troy pound)

    // Chinese units
    LIANG("两/liǎng", new BigDecimal("0.05")),           // Chinese tael/liang (modern: 50g)
    JIN("斤/jīn", new BigDecimal("0.5")),                // Chinese catty/jin (modern: 500g)
    DAN("担/dān", new BigDecimal("50")),                 // Chinese picul/dan (modern: 50kg)

    // Traditional Chinese units (historical)
    ZHU_ANCIENT("铢/zhū", new BigDecimal("0.0002559825")), // ~1/24 liang (historical)
    LIANG_ANCIENT("两/liǎng", new BigDecimal("0.0156")),  // Historical liang (~15.6g)
    JIN_ANCIENT("斤/jīn", new BigDecimal("0.5971")),     // Historical jin (~597g)

    // Japanese units
    MONME("匁/momme", new BigDecimal("0.00375")),        // Japanese unit (~3.75g)
    KAN("貫/kan", new BigDecimal("3.75")),               // 1000 monme
    KWAN("貫/kwan", new BigDecimal("3.75")),             // Alternative spelling of kan

    // Korean units
    DON("돈/don", new BigDecimal("0.00375")),           // Korean unit (same as momme)
    GEUN("근/geun", new BigDecimal("0.6")),             // Korean unit (600g)

    // Indian units
    RATTI("ratti", new BigDecimal("0.00018144")),        // ~0.18g, used for gems
    MASHA("masha", new BigDecimal("0.00097")),           // 8 rattis
    TOLA("tola", new BigDecimal("0.01166")),             // ~11.66g, used for gold
    SEER("seer", new BigDecimal("0.93")),                // ~0.93kg (regional variations exist)
    MAUND("maund", new BigDecimal("37.3242")),           // 40 seers (~37.3kg)

    // Middle Eastern units
    DIRHAM("dirham", new BigDecimal("0.00318")),         // Arab weight (~3.18g)
    MITHQAL("mithqal", new BigDecimal("0.00425")),       // Arab weight (~4.25g)
    RATL("ratl", new BigDecimal("0.45")),                // Arab pound (~450g)
    QINTAR("qintar", new BigDecimal("45")),              // 100 ratls

    // Roman units
    UNCIA_ROMAN("uncia", new BigDecimal("0.027288")),    // Roman ounce
    LIBRA_ROMAN("libra", new BigDecimal("0.3274")),      // Roman pound

    // Miscellaneous historical
    CARAT_WEIGHT("ct", new BigDecimal("0.0002")),        // 200mg (gem weight)
    CARAT_GOLD("kt", new BigDecimal("0.0002")),          // Same as carat weight
    GAMMA_WEIGHT("γ", new BigDecimal("1E-9")),           // Alternate name for microgram
    POINT("pt", new BigDecimal("0.0000002")),            // 1/100 carat

    // Astronomical masses
    EARTH_MASS("M⊕", new BigDecimal("5.9722E24")),       // Earth's mass
    SOLAR_MASS("M☉", new BigDecimal("1.989E30")),        // Sun's mass

    // Abstract/customizable unit
    CUSTOM("custom", BigDecimal.ONE);                    // Placeholder for custom units

    private final String symbol;
    private final BigDecimal toKilogramFactor;
    private static final Map<String, MassScaleUnit> symbolMap = new HashMap<>();

    static {
        for (MassScaleUnit unit : values()) {
            symbolMap.put(unit.symbol.toLowerCase(), unit);
            // Also add without special characters for easier lookup
            String simplifiedSymbol = unit.symbol.replaceAll("[^a-zA-Z0-9_]", "");
            if (!simplifiedSymbol.equals(unit.symbol)) {
                symbolMap.put(simplifiedSymbol.toLowerCase(), unit);
            }
        }
    }

    MassScaleUnit(String symbol, BigDecimal toKilogramFactor) {
        this.symbol = symbol;
        this.toKilogramFactor = toKilogramFactor;
    }

    /**
     * Converts a value in this unit to kilograms
     */
    public BigDecimal toKilograms(BigDecimal value) {
        return value.multiply(toKilogramFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value in kilograms to this unit
     */
    public BigDecimal fromKilograms(BigDecimal kilograms) {
        return kilograms.divide(toKilogramFactor, MathContext.DECIMAL128);
    }

    /**
     * Converts a value from one mass unit to another
     */
    public static BigDecimal convert(BigDecimal value, MassScaleUnit from, MassScaleUnit to) {
        BigDecimal kilograms = from.toKilograms(value);
        return to.fromKilograms(kilograms);
    }

    /**
     * Formats a mass value with its unit symbol
     */
    public String format(BigDecimal value) {
        return value.toString() + " " + symbol;
    }

    /**
     * Formats a mass value with its unit symbol, using specified precision
     */
    public String format(BigDecimal value, int precision) {
        return value.setScale(precision, RoundingMode.HALF_UP).toString() + " " + symbol;
    }

    /**
     * Gets a MassScaleUnit by its symbol
     */
    public static MassScaleUnit fromSymbol(String symbol) {
        MassScaleUnit unit = symbolMap.get(symbol.toLowerCase());
        if (unit == null) {
            throw new IllegalArgumentException("Unknown mass unit symbol: " + symbol);
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
     * Returns the conversion factor to kilograms
     */
    public BigDecimal getConversionFactor() {
        return toKilogramFactor;
    }

    /**
     * Returns true if this is a standard SI or derived metric unit
     */
    public boolean isMetricUnit() {
        return this.compareTo(YOCTOGRAM) >= 0 && this.compareTo(GIGATONNE) <= 0;
    }

    /**
     * Returns true if this is a US/Imperial unit
     */
    public boolean isImperialUnit() {
        return (this.compareTo(GRAIN) >= 0 && this.compareTo(TON_UK) <= 0) ||
                (this.compareTo(GRAIN_TROY) >= 0 && this.compareTo(POUND_APO) <= 0);
    }

    /**
     * Returns true if this is an Asian unit (Chinese, Japanese, Korean)
     */
    public boolean isAsianUnit() {
        return (this.compareTo(LIANG) >= 0 && this.compareTo(DAN) <= 0) ||
                (this.compareTo(ZHU_ANCIENT) >= 0 && this.compareTo(JIN_ANCIENT) <= 0) ||
                (this.compareTo(MONME) >= 0 && this.compareTo(GEUN) <= 0);
    }

    /**
     * Returns true if this is a tiny unit (less than a milligram)
     */
    public boolean isMicroscopic() {
        return this.toKilogramFactor.compareTo(new BigDecimal("1E-6")) < 0;
    }

    /**
     * Returns true if this is an astronomical unit
     */
    public boolean isAstronomical() {
        return this.compareTo(EARTH_MASS) >= 0;
    }

    /**
     * Returns true if this unit is primarily used for precious metals or gems
     */
    public boolean isPreciousMetalUnit() {
        return this == GRAIN_TROY || this == PENNYWEIGHT || this == OUNCE_TROY ||
                this == POUND_TROY || this == CARAT_WEIGHT || this == CARAT_GOLD ||
                this == POINT;
    }

    public static MassScaleUnit createCustomUnit(String symbol, BigDecimal toKilogramFactor) {
        try {
            java.lang.reflect.Field symbolField = MassScaleUnit.class.getDeclaredField("symbol");
            symbolField.setAccessible(true);
            symbolField.set(CUSTOM, symbol);

            java.lang.reflect.Field factorField = MassScaleUnit.class.getDeclaredField("toKilogramFactor");
            factorField.setAccessible(true);
            factorField.set(CUSTOM, toKilogramFactor);

            return CUSTOM;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create custom unit", e);
        }
    }
}