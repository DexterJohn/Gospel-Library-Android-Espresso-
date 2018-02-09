package org.lds.ldssa.util;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import javax.annotation.Nonnull;

public final class ThreeTenUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault());
    private static final int DAWN_OF_TIME_YEAR = 1970;

    private ThreeTenUtil() {
    }

    public static long toMillis(@Nonnull LocalDateTime d) {
        return d.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime fromMillis(long l) {
        return Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String formatIso(@Nonnull LocalDateTime d) {
        return FORMATTER.format(ZonedDateTime.ofLocal(d, ZoneId.systemDefault(), null));
    }

    public static LocalDateTime parseIso(@Nonnull String text) {
        return LocalDateTime.parse(text, FORMATTER);
    }

    public static LocalDateTime getDawnOfTime() {
        return LocalDate.of(DAWN_OF_TIME_YEAR, 1, 1).atStartOfDay();
    }
}
