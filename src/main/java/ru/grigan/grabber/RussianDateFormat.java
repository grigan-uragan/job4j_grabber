package ru.grigan.grabber;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RussianDateFormat {
    public static final String PATTERN = "d MMM yy, HH:mm";
    public static final String TODAY = "сегодня";
    public static final String YESTERDAY = "вчера";

    private DateFormatSymbols rusDateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"янв", "фев", "мар", "апр", "май", "июн",
                    "июл", "авг", "сен", "окт", "ноя", "дек"};
        }
    };

    public Date getDateFromRusFormat(String dateStr, String pattern) {
        try {
            SimpleDateFormat formatter;
            if (dateStr.contains(TODAY)) {

                formatter = new SimpleDateFormat("'сегодня,' HH:mm");
                return new Date(new Date().getTime() - formatter.parse(dateStr).getTime());

            } else if (dateStr.contains(YESTERDAY)) {
                formatter = new SimpleDateFormat("'вчера,' HH:mm");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(
                        new Date().getTime() - formatter.parse(dateStr).getTime());
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                return calendar.getTime();
            } else {
                formatter = new SimpleDateFormat(pattern, rusDateFormatSymbols);
                return formatter.parse(dateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
