package com.example.pillsreminder.helpers;

import java.util.Calendar;

public class CalendarHelpers {

    public static String calendarToString(Calendar cal) {

        String day_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        String month_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.MONTH)));
        String year_str = StringHelpers.oneDigitToTwoDigitString(String.valueOf(cal.get(Calendar.YEAR)));

        String str = day_str + "." + month_str + "." + year_str;
        return str;
    }

    public static Calendar stringToCalendar(String string) {
        return null;
    }

}

