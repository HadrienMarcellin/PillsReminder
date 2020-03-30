package com.example.pillsreminder.helpers;

public class StringHelpers {

    public static String oneDigitToTwoDigitString(String str) {
        if(str.length() < 2)
            str = "0" + str;
        return str;
    }
}
