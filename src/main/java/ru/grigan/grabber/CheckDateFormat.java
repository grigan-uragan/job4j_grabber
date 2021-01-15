package ru.grigan.grabber;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckDateFormat {
    public static void main(String[] args) throws ParseException {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        Date date = Date.from(Instant.ofEpochMilli(currentTime));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy H:mm",
                new Locale("ru"));
        System.out.println(date);
        System.out.println(new RussianDateFormat().getDateFromRusFormat(
                "31 июл 20, 13:53", RussianDateFormat.PATTERN));
        String forParse = "Thu Dec 24 15:02:00 MSK 2020";
        SimpleDateFormat format = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        System.out.println(format.parse(forParse));
    }
}
