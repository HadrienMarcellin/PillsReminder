package com.example.pillsreminder.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pillsreminder.R;
import com.example.pillsreminder.entities.Drug;
import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.helpers.DateTextWatcher;
import com.example.pillsreminder.helpers.StringHelpers;
import com.example.pillsreminder.helpers.TimeTextWatcher;
import com.example.pillsreminder.repositories.PillRepository;
import com.example.pillsreminder.viewModels.DrugViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewPillActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.pillsreminder.REPLY";

    private DrugViewModel drugViewModel;

    private EditText editTextDrugPill;
    private EditText editTextDate;
    private EditText editTextTime;
    private Button buttonSave;
    private ImageView imagePlus;
    private ImageView imageMinus;
    private Spinner drugSpinner;
    private TextView textQuantity;
    private Pill newPill;
    private int pillQuantity = 0;

    private LiveData<List<Drug>> allDrugs;
    private List<String> allDrugNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);

        assignTagsToInterface();
        setButtonCallbacks();



        final ArrayAdapter<String> drugAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList("Undefined", "Antalnox")));
        drugAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        drugSpinner.setAdapter(drugAdapter);
        drugSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
        allDrugs = drugViewModel.getAllDrugs();
        allDrugs.observe(this, new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                //TODO: Implement adapter update for spinner.
                allDrugNames.clear();
                for(Drug drug : drugs) {
                    allDrugNames.add(drug.getName());
                }
                drugAdapter.notifyDataSetChanged();
            }
        });

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
//        editTextDrugPill = findViewById(R.id.editText_drug_pill);
        editTextDate = findViewById(R.id.editText_date_pill);
        editTextTime = findViewById(R.id.editText_time_pill);
        drugSpinner = findViewById(R.id.spinner_drug_pill);
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
//        if(TextUtils.isEmpty(editTextDrugPill.getText()))
//            return false;

        Drug drug = drugViewModel.selectDrugFromName(allDrugNames.get(drugSpinner.getSelectedItemPosition()));
        if (drug.getName().equals("Undefined"))
            return false;

        if(TextUtils.isEmpty(editTextDate.getText()) || TextUtils.isEmpty(editTextTime.getText()))
            return false;

        Calendar cal = CalendarHelpers.stringToCalendar(editTextDate.getText().toString(), editTextTime.getText().toString());
        newPill = new Pill(drug.getDrug_id(), cal, pillQuantity);

        return true;

    }
}
