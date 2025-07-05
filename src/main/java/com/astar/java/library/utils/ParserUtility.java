package com.astar.java.library.utils;

import com.astar.java.library.enums.LengthScaleUnit;
import com.astar.java.library.enums.MassScaleUnit;
import com.astar.java.library.enums.TemperatureScaleUnit;
import com.astar.java.library.enums.TimeScaleUnit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

public abstract class ParserUtility {

    public static Number parseTime(TimeScaleUnit from, TimeScaleUnit target, String value) {
        try {
            BigDecimal numericValue = new BigDecimal(value);
            BigDecimal seconds = from.toSeconds(numericValue);
            return target.fromSeconds(seconds);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value, e);
        }
    }

    public static Number parseWeight(MassScaleUnit from, MassScaleUnit target, String value) {
        try {
            BigDecimal numericValue = new BigDecimal(value);
            BigDecimal kilogram = from.toKilograms(numericValue);
            return target.fromKilograms(kilogram);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value, e);
        }
    }

    public static Number parseLength(LengthScaleUnit from, LengthScaleUnit target, String value) {
        try {
            BigDecimal numericValue = new BigDecimal(value);
            BigDecimal meter = from.toMeters(numericValue);
            return target.fromMeters(meter);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value, e);
        }
    }

    public static Number parseTemperature(
            TemperatureScaleUnit from, TemperatureScaleUnit target, String value) {
        try {
            double numericValue = Double.parseDouble(value);
            double kelvin = from.toKelvin(numericValue);
            return target.fromKelvin(kelvin);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value, e);
        }
    }

    /**
     * Converts any temporal object (LocalDateTime, ZonedDateTime, Instant, Unix timestamp) to another time zone.
     *
     * @param dateTime The date-time to convert. Can be LocalDateTime, ZonedDateTime, Instant, or Long (Unix seconds).
     * @param fromZone The source time zone. (Ignored if dateTime is ZonedDateTime or Instant)
     * @param toZone   The target time zone.
     * @return The converted ZonedDateTime in the target time zone.
     */
    public static ZonedDateTime convertTimeZone(Object dateTime, ZoneId fromZone, ZoneId toZone) {
        return switch (dateTime) {
            case ZonedDateTime zonedDateTime -> zonedDateTime.withZoneSameInstant(toZone);
            case LocalDateTime localDateTime ->
                    localDateTime.atZone(fromZone).withZoneSameInstant(toZone);
            case Instant instant -> instant.atZone(toZone);
            case Long l ->  // Handle Unix timestamp (seconds)
                    Instant.ofEpochSecond(l).atZone(toZone);
            case null, default -> {
//                TODO RECHECK THIS
                assert dateTime != null;
                throw new IllegalArgumentException(
                        "Unsupported date-time type: " + dateTime.getClass().getSimpleName());
            }
        };
    }

    /**
     * Converts an Object to the target type.
     *
     * @param target The target class type.
     * @param value  The object value to convert.
     * @param <T>    The generic return type.
     * @return The converted value of type T.
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseTo(Class<T> target, Object value) {
        if (value == null) {
            return null;
        }
        if (target.isInstance(value)) {
            return (T) value;
        }
        String strValue = value.toString().trim();
        if (Number.class.isAssignableFrom(target)) {
            return (T) handleParseNumber(target, strValue);
        } else if (Temporal.class.isAssignableFrom(target)) {
            return (T) handleParseTime(target, strValue);
        } else if (target == Boolean.class) {
            return (T) handleParseBoolean(strValue);
        } else if (target == Character.class) {
            return (T) handleParseCharacter(strValue);
        } else if (target == String.class) {
            return (T) strValue;
        }
        throw new IllegalArgumentException("Unsupported type: " + target.getName());
    }

    private static Number handleParseNumber(Class<?> target, String value) {
        try {
            return switch (target.getSimpleName()) {
                case "Integer" -> Integer.valueOf(value);
                case "Long" -> Long.valueOf(value);
                case "Double" -> Double.valueOf(value);
                case "Float" -> Float.valueOf(value);
                case "Short" -> Short.valueOf(value);
                case "Byte" -> Byte.valueOf(value);
                case "BigInteger" -> new BigInteger(value);
                case "BigDecimal" -> new BigDecimal(value);
                default -> throw new IllegalArgumentException(
                        "Unsupported number type: " + target.getName());
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid number format for " + target.getSimpleName() + ": " + value, e);
        }
    }

    private static Boolean handleParseBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "1".equals(value);
    }

    private static Character handleParseCharacter(String value) {
        return !value.isEmpty() ? value.charAt(0) : null;
    }

    private static Temporal handleParseTime(Class<?> target, String value) {
        try {
            if (value.matches("^\\d{10}$")) {
                Instant instant = Instant.ofEpochSecond(Long.parseLong(value));
                return handleParseInstant(target, instant);
            } else if (value.matches("^\\d{13}$")) {
                Instant instant = Instant.ofEpochMilli(Long.parseLong(value));
                return handleParseInstant(target, instant);
            } else {
                return switch (target.getSimpleName()) {
                    case "LocalDate" -> LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
                    case "LocalTime" -> LocalTime.parse(value, DateTimeFormatter.ISO_TIME);
                    case "LocalDateTime" ->
                            LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                    case "Instant" -> Instant.parse(value);
                    case "ZonedDateTime" ->
                            ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    case "OffsetDateTime" ->
                            OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    case "OffsetTime" -> OffsetTime.parse(value, DateTimeFormatter.ISO_OFFSET_TIME);
                    default -> throw new IllegalArgumentException(
                            "Unsupported date/time type: " + target.getName());
                };
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date/time format for " + target.getSimpleName() + ": " + value, e);
        }
    }

    private static Temporal handleParseInstant(Class<?> target, Instant instant) {
        if (target == LocalDate.class) {
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (target == LocalTime.class) {
            return instant.atZone(ZoneId.systemDefault()).toLocalTime();
        } else if (target == LocalDateTime.class) {
            return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else if (target == ZonedDateTime.class) {
            return instant.atZone(ZoneId.systemDefault());
        } else if (target == OffsetDateTime.class) {
            return instant.atOffset(ZoneOffset.UTC);
        } else if (target == OffsetTime.class) {
            return instant.atOffset(ZoneOffset.UTC).toOffsetTime();
        } else {
            return instant;
        }
    }
}
