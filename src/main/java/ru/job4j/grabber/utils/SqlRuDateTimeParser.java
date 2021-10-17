package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сен", "09"),
            Map.entry("окт", "10"),
            Map.entry("ноя", "11"),
            Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        var s = (parse.split("[ :,]"));
        LocalDateTime localDateTime = LocalDateTime.now();
        if (s[0].equals("сегодня")) {
            localDateTime = LocalDateTime.of(LocalDate.now(),
                    LocalTime.of(Integer.parseInt(s[2]), Integer.parseInt(s[3])));
        } else if (s[0].equals("вчера")) {
            localDateTime = LocalDateTime.of(LocalDate.now().minusDays(1),
                    LocalTime.of(Integer.parseInt(s[2]), Integer.parseInt(s[3])));
        } else {
            localDateTime = LocalDateTime.of(Integer.parseInt("20" + s[2]),
                    Integer.parseInt(MONTHS.get(s[1])),
                    Integer.parseInt(s[0]), Integer.parseInt(s[4]), Integer.parseInt(s[5]));
        }
        return localDateTime;
    }

    public static void main(String[] args) {
        SqlRuDateTimeParser s = new SqlRuDateTimeParser();
        System.out.println(s.parse("сегодня, 22:29"));
        System.out.println(s.parse("вчера, 22:29"));
        System.out.println(s.parse("12 май 20, 08:17"));
    }

}