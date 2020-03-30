package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.repositories.PillRepository;

public class NewPillActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.pillsreminder.REPLY";

    private EditText mEditTextView;
    private Pill newPill;


    private void createPillFromSurvey() {
        newPill =  new Pill("pill");
    }

    private void insertPillToDatabase() {
        PillRepository pillRepository = new PillRepository(getApplication());
        pillRepository.insert(newPill);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);

        mEditTextView = findViewById(R.id.edit_text_title_pill);

        final Button button = findViewById(R.id.button_save_pill);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mEditTextView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String title = mEditTextView.getText().toString();
                    createPillFromSurvey();
                    insertPillToDatabase();
                    replyIntent.putExtra(EXTRA_REPLY, title);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
