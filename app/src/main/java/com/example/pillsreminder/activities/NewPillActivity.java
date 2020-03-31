package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.helpers.DateTextWatcher;
import com.example.pillsreminder.helpers.TimeTextWatcher;
import com.example.pillsreminder.repositories.PillRepository;

import java.util.Calendar;

public class NewPillActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.pillsreminder.REPLY";

    private EditText editTextDrugPill;
    private EditText editTextDate;
    private EditText editTextTime;
    private Button buttonSave;
    private ImageView imagePlus;
    private ImageView imageMinus;
    private TextView textQuantity;
    private Pill newPill;
    private int pillQuantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);

        assignTagsToInterface();
        setButtonCallbacks();

        editTextTime.addTextChangedListener(new TimeTextWatcher());
        editTextDate.addTextChangedListener(new DateTextWatcher());

        setDefaultValues();
    }

    private void setButtonCallbacks() {
        this.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pillQuantity++;
                textQuantity.setText(String.valueOf(pillQuantity));
            }
        });

        this.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pillQuantity = (pillQuantity > 0) ? --pillQuantity : pillQuantity;
                textQuantity.setText(String.valueOf(pillQuantity));
            }
        });

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (createPillFromSurvey()) {
                    insertPillToDatabase();
                    setResult(RESULT_OK, replyIntent);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });
    }

    private void assignTagsToInterface() {
        imageMinus = findViewById(R.id.new_pill_minus);
        imagePlus = findViewById(R.id.new_pill_plus);
        buttonSave = findViewById(R.id.button_save_pill);
        textQuantity = findViewById(R.id.new_pill_quantity);
        editTextDrugPill = findViewById(R.id.editText_drug_pill);
        editTextDate = findViewById(R.id.editText_date_pill);
        editTextTime = findViewById(R.id.editText_time_pill);
    }

    private void setDefaultValues() {
        Calendar now = Calendar.getInstance();
        String date = CalendarHelpers.calendarToDateString(now);
        String time = CalendarHelpers.calendarToTimeString(now);

        editTextDate.setText(date);
        editTextTime.setText(time);
        textQuantity.setText(String.valueOf(pillQuantity));


    }

    private void insertPillToDatabase() {
        PillRepository pillRepository = new PillRepository(getApplication());
        pillRepository.insert(newPill);
    }

    private boolean createPillFromSurvey() {
        if (pillQuantity < 0)
            return false;
        if(TextUtils.isEmpty(editTextDrugPill.getText()))
            return false;

        if(TextUtils.isEmpty(editTextDate.getText()) || TextUtils.isEmpty(editTextTime.getText()))
            return false;

        Calendar cal = CalendarHelpers.stringToCalendar(editTextDate.getText().toString(), editTextTime.getText().toString());
        newPill = new Pill(editTextDrugPill.getText().toString(), cal);
        return true;

    }
}
