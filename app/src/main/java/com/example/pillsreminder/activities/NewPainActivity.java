package com.example.pillsreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pillsreminder.R;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.helpers.DateTextWatcher;
import com.example.pillsreminder.helpers.PainLevels;
import com.example.pillsreminder.helpers.TimeTextWatcher;
import com.example.pillsreminder.room.pain.Pain;
import com.example.pillsreminder.room.pain.PainRepository;

import java.util.Calendar;

public class NewPainActivity extends AppCompatActivity {

    private Pain newPain;
    private TextView textViewPainLevel;
    private TextView editTextViewDate;
    private TextView editTextViewTime;
    private PainLevels painLevel;
    private ImageView imagePlus;
    private ImageView imageMinus;
    private Button buttonSave;
    private PainLevels[] painLevels;
    private CheckBox checkBoxSurge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pain);

        painLevels = PainLevels.values();
        painLevel = painLevels[0];


        assignTagsToInterface();
        setButtonCallbacks();

        editTextViewTime.addTextChangedListener(new TimeTextWatcher());
        editTextViewDate.addTextChangedListener(new DateTextWatcher());

        setDefaultValues();
    }

    private void setButtonCallbacks() {

        this.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    painLevel = painLevel.next();
                } catch (Exception e) {
                    painLevel = PainLevels.FATAL;
                }
                textViewPainLevel.setText(painLevel.getFrenchName());
            }
        });

        this.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    painLevel = painLevel.previous();
                } catch (Exception e) {
                    painLevel = PainLevels.NONE;
                }
                textViewPainLevel.setText(painLevel.getFrenchName());
            }
        });

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(createPainFromSurvey()) {
                    insertPainToDatabase();
                    setResult(RESULT_OK, replyIntent);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });
    }

    private void assignTagsToInterface() {
        textViewPainLevel = findViewById(R.id.new_pain_level);
        editTextViewDate = findViewById(R.id.new_pain_date);
        editTextViewTime = findViewById(R.id.new_pain_time);
        imagePlus = findViewById(R.id.new_pain_plus);
        imageMinus = findViewById(R.id.new_pain_minus);
        buttonSave = findViewById(R.id.new_pain_save);
        checkBoxSurge = findViewById(R.id.new_pain_long_duration);
    }

    private void setDefaultValues() {
        Calendar now = Calendar.getInstance();
        String date = CalendarHelpers.calendarToDateString(now);
        String time = CalendarHelpers.calendarToTimeString(now);

        editTextViewDate.setText(date);
        editTextViewTime.setText(time);
        textViewPainLevel.setText(painLevel.getFrenchName());
    }

    private boolean createPainFromSurvey() {
        if (painLevel.equals(PainLevels.UNDIFINED))
            return false;

        if(TextUtils.isEmpty(editTextViewDate.getText()) || TextUtils.isEmpty(editTextViewTime.getText()))
            return false;

        Calendar cal = CalendarHelpers.stringToCalendar(editTextViewDate.getText().toString(), editTextViewTime.getText().toString());
        this.newPain = new Pain(painLevel, checkBoxSurge.isChecked(), cal);
        return true;

    }

    private void insertPainToDatabase() {
        PainRepository painRepository = new PainRepository(getApplication());
        painRepository.insert(newPain);
    }

}
