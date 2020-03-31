package com.example.pillsreminder.helpers;

import android.text.Editable;
import android.text.TextWatcher;

public class TimeTextWatcher implements TextWatcher {

    public static final int MAX_FORMAT_LEN = 4;
    public static final int MIN_FORMAT_LEN = 2;

    private String updatedText;
    private boolean editing;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().equals(updatedText) || editing) return;

        String digitsOnly = s.toString().replaceAll("\\D", "");
        int digitLen = digitsOnly.length();


        if (digitLen <= MIN_FORMAT_LEN) {
            updatedText = digitsOnly;
            return;
        }

        if (digitLen > MAX_FORMAT_LEN) {
            digitsOnly = digitsOnly.substring(0, MAX_FORMAT_LEN);
        }

        String hours = digitsOnly.substring(0, 2);
        String mins = digitsOnly.substring(2);
        updatedText = String.format("%s:%s", hours, mins);

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editing) return;

        editing = true;
        editable.clear();
        editable.insert(0, updatedText);
        editing = false;
    }
}
