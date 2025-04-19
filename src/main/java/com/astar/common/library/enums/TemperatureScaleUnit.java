package com.astar.common.library.enums;

import java.util.HashMap;
import java.util.Map;

public enum TemperatureScaleUnit {
    CELSIUS("°C") {
        public double toKelvin(double value) {
            return value + KELVIN_OFFSET;
        }

        public double fromKelvin(double kelvin) {
            return kelvin - KELVIN_OFFSET;
        }
    },
    FAHRENHEIT("°F") {
        public double toKelvin(double value) {
            return (value - FAHRENHEIT_OFFSET) * FAHRENHEIT_SCALE;
        }

        public double fromKelvin(double kelvin) {
            return (kelvin / FAHRENHEIT_SCALE) + FAHRENHEIT_OFFSET;
        }
    },
    KELVIN("K") {
        public double toKelvin(double value) {
            return value;
        }

        public double fromKelvin(double kelvin) {
            return kelvin;
        }
    },
    RANKINE("°R") {
        public double toKelvin(double value) {
            return value * RANKINE_SCALE;
        }

        public double fromKelvin(double kelvin) {
            return kelvin / RANKINE_SCALE;
        }
    },
    DELISLE("°D") {
        public double toKelvin(double value) {
            return CELSIUS.toKelvin(100 - (value * (2.0 / 3.0)));
        }

        public double fromKelvin(double kelvin) {
            return (100 - CELSIUS.fromKelvin(kelvin)) * (3.0 / 2.0);
        }
    },
    NEWTON("°N") {
        public double toKelvin(double value) {
            return CELSIUS.toKelvin(value * (100.0 / 33.0));
        }

        public double fromKelvin(double kelvin) {
            return CELSIUS.fromKelvin(kelvin) * (33.0 / 100.0);
        }
    };

    private static final double KELVIN_OFFSET = 273.15;
    private static final double FAHRENHEIT_OFFSET = 32;
    private static final double FAHRENHEIT_SCALE = 5.0 / 9.0;
    private static final double RANKINE_SCALE = 5.0 / 9.0;

    private final String symbol;
    private static final Map<String, TemperatureScaleUnit> symbolMap = new HashMap<>();

    static {
        for (TemperatureScaleUnit unit : values()) {
            symbolMap.put(unit.symbol, unit);
        }
    }

    TemperatureScaleUnit(String symbol) {
        this.symbol = symbol;
    }

    public abstract double toKelvin(double value);

    public abstract double fromKelvin(double kelvin);

    public static double convert(double value, TemperatureScaleUnit from, TemperatureScaleUnit to) {
        double kelvin = from.toKelvin(value);
        return to.fromKelvin(kelvin);
    }

    public String getSymbol() {
        return symbol;
    }

    public static TemperatureScaleUnit fromSymbol(String symbol) {
        TemperatureScaleUnit unit = symbolMap.get(symbol);
        if (unit == null) {
            throw new IllegalArgumentException("Unknown temperature unit: " + symbol);
        }
        return unit;
    }
}
