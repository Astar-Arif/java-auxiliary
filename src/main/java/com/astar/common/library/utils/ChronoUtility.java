package com.astar.common.library.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.time.*;
import java.util.Date;

//TODO ADD MORE
public abstract class ChronoUtility extends DateUtils {

    public static ZonedDateTime toZonedDateTime(Instant instant, ZoneId zone) {
        return ZonedDateTime.ofInstant(instant, zone);
    }

    public static LocalDate toZoneLocalDate(Instant instant, ZoneId zone) {
        return LocalDate.ofInstant(instant, zone);
    }

    public static LocalDateTime toZoneLocalDateTime(Instant instant, ZoneId zone) {
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static boolean isBefore(Date target, Date compared) {
        return target.before(compared);
    }

    public static boolean isBefore(Instant target, Instant compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(LocalDate target, LocalDate compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(LocalDateTime target, LocalDateTime compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(ZonedDateTime target, ZonedDateTime compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(OffsetDateTime target, OffsetDateTime compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(YearMonth target, YearMonth compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(Year target, Year compared) {
        return target.isBefore(compared);
    }

    public static boolean isBefore(MonthDay target, MonthDay compared) {
        return target.isBefore(compared);
    }

    public static boolean isAfter(Date target, Date compared) {
        return target.after(compared);
    }

    public static boolean isAfter(Instant target, Instant compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(LocalDate target, LocalDate compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(LocalDateTime target, LocalDateTime compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(ZonedDateTime target, ZonedDateTime compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(OffsetDateTime target, OffsetDateTime compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(YearMonth target, YearMonth compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(Year target, Year compared) {
        return target.isAfter(compared);
    }

    public static boolean isAfter(MonthDay target, MonthDay compared) {
        return target.isAfter(compared);
    }
}
