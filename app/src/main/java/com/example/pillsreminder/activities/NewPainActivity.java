package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.accessibility.AccessibilityManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.helpers.PainLevels;
import com.example.pillsreminder.repositories.PainRepository;

import java.util.Calendar;

public class NewPainActivity extends AppCompatActivity {

    private Pain newPain;
    private TextView textViewPainLevel;
    private TextView textViewDate;
    private TextView textViewTime;
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

        textViewPainLevel = findViewById(R.id.new_pain_level);
        textViewPainLevel.setText(painLevel.getName());

        imagePlus = findViewById(R.id.new_pain_plus);
        imageMinus = findViewById(R.id.new_pain_minus);
        buttonSave = findViewById(R.id.new_pain_save);
        checkBoxSurge = findViewById(R.id.new_pain_long_duration);
        setButtonCallbacks();
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
                textViewPainLevel.setText(painLevel.getName());
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
                textViewPainLevel.setText(painLevel.getName());
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

    private boolean createPainFromSurvey() {
        if (painLevel.equals(PainLevels.UNDIFINED))
            return false;
        else {
            this.newPain = new Pain(painLevel, checkBoxSurge.isChecked(), Calendar.getInstance());
            return true;
        }
    }

    private void insertPainToDatabase() {
        PainRepository painRepository = new PainRepository(getApplication());
        painRepository.insert(newPain);
    }

}
