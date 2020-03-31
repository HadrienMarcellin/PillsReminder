package com.example.pillsreminder.helpers;

import java.util.Calendar;

public class CalendarHelpers {

    public static String calendarToDateString(Calendar cal) {

        String day_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        String month_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.MONTH) +  1));
        String year_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.YEAR)));

        String str = day_str + "." + month_str + "." + year_str;
        return str;
    }

    public static String calendarToTimeString(Calendar cal) {
        String hours = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        String min = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.MINUTE)));
        return String.format("%s:%s", hours, min);
    }

    public static Calendar stringToCalendar(String date, String time) {

        String dateDigits = date.replaceAll("\\D", "");
        int day_of_month = Integer.parseInt(dateDigits.substring(0, 2));
        int month = Integer.parseInt(dateDigits.substring(2, 4));
        int year = Integer.parseInt(dateDigits.substring(4));

        String timeDigits = time.replaceAll("\\D", "");
        int hour = Integer.parseInt(timeDigits.substring(0, 2));
        int mins = Integer.parseInt(timeDigits.substring(2));

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day_of_month, hour, mins);

        return cal;
    }

}

