package com.example.pillsreminder.room.converters;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {

    @TypeConverter
    public static Calendar longToCalendar(Long dateLong) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateLong);
        return dateLong == null ? null : cal;
    }

    @TypeConverter
    public static Long dateToLong(Calendar dateDate) {
        return dateDate == null ? null : dateDate.getTimeInMillis();
    }
}
