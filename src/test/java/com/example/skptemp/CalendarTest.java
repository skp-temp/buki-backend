package com.example.skptemp;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class CalendarTest {


    @Test
    void calendarTest() {
        int year = 2024;
        int month = 4;
        int weekOfMonth = 1;

        List<LocalDate> datesOfWeek = getDatesOfWeek(year, month, weekOfMonth);

        datesOfWeek.forEach(System.out::println);
    }

    public static List<LocalDate> getDatesOfWeek(int year, int month, int week) {
        List<LocalDate> datesOfWeek = new ArrayList<>();

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        LocalDate startOfWeek = firstDayOfMonth.with(TemporalAdjusters.dayOfWeekInMonth(week, firstDayOfMonth.getDayOfWeek()));

        startOfWeek = startOfWeek.minusDays(startOfWeek.getDayOfWeek().getValue());


        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            datesOfWeek.add(date);
        }

        return datesOfWeek;
    }
}
