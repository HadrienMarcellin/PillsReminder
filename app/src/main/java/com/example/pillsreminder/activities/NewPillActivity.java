package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.repositories.PillRepository;

import java.util.Calendar;

public class NewPillActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.pillsreminder.REPLY";

    private EditText editTextDrugPill;
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

        imageMinus = findViewById(R.id.new_pill_minus);
        imagePlus = findViewById(R.id.new_pill_plus);
        buttonSave = findViewById(R.id.button_save_pill);
        textQuantity = findViewById(R.id.new_pill_quantity);
        textQuantity.setText(String.valueOf(pillQuantity));
        editTextDrugPill = findViewById(R.id.editText_drug_pill);

        setButtonCallbacks();
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

    private void insertPillToDatabase() {
        PillRepository pillRepository = new PillRepository(getApplication());
        pillRepository.insert(newPill);
    }
    
    private boolean createPillFromSurvey() {
        if (pillQuantity < 0)
            return false;
        if(TextUtils.isEmpty(editTextDrugPill.getText()))
            return false;

        newPill = new Pill(editTextDrugPill.getText().toString(), Calendar.getInstance());
        return true;

    }
}
