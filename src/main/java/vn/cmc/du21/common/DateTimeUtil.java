package vn.cmc.du21.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtil {
    private static final String datePattern = "dd/MM/yyyy";
    private static final String dateTimePattern = "dd/MM/yyyy HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.getDefault());
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern, Locale.getDefault());

    private DateTimeUtil() {
        super();
    }

    public static LocalDate stringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, dateTimeFormatter);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }

    public static Date localDateToSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate sqlDateToLocalDate(Date sqlDate) {
        return sqlDate.toLocalDate();
    }

    public static Timestamp localDateTimeToSqlTimestamp(LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime sqlTimestampToLocalDateTime(java.sql.Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
