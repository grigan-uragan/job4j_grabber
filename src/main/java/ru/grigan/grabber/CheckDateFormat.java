package ru.grigan.grabber;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class CheckDateFormat {
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        Date date = Date.from(Instant.ofEpochMilli(currentTime));
        System.out.println(date.toInstant());
    }
}
