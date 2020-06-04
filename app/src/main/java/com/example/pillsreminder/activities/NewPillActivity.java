package com.example.pillsreminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pillsreminder.R;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.helpers.DateTextWatcher;
import com.example.pillsreminder.helpers.TimeTextWatcher;
import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.pill.Pill;
import com.example.pillsreminder.viewModels.TreatmentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewPillActivity extends AppCompatActivity {


    public static final String EXTRA_REPLY = "com.example.android.pillsreminder.REPLY";

//    private DrugViewModel drugViewModel;
//    private PillViewModel pillViewModel;
    private TreatmentViewModel treatmentViewModel;

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
    private static final Logger LOGGER = Logger.getLogger(NewPillActivity.class.getName());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pill);

        assignTagsToInterface();
        setButtonCallbacks();



        final ArrayAdapter<String> drugAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_spinner_item);
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

//
//        drugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
//        pillViewModel = ViewModelProviders.of(this).get(PillViewModel.class);
        treatmentViewModel = ViewModelProviders.of(this).get(TreatmentViewModel.class);


        allDrugs = treatmentViewModel.getAllDrugs();
        allDrugs.observe(this, new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                drugAdapter.clear();
                drugAdapter.addAll(getDrugNamesFromDrugs(drugs));
                drugAdapter.notifyDataSetChanged();
            }
        });


        editTextTime.addTextChangedListener(new TimeTextWatcher());
        editTextDate.addTextChangedListener(new DateTextWatcher());

        setDefaultValues();
    }


    private List<String> getDrugNamesFromDrugs(List<Drug> drugs) {

        List<String> drugs_names = new ArrayList<>();

        for(Drug drug : drugs) {
            drugs_names.add(drug.getName());
        }
        return drugs_names;
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

        LOGGER.log(Level.INFO, treatmentViewModel.pillToString(newPill));
        treatmentViewModel.printAllDrugs();
        treatmentViewModel.insertPill(newPill);
    }

    private boolean createPillFromSurvey() {
        if (pillQuantity < 0)
            return false;
//        if(TextUtils.isEmpty(editTextDrugPill.getText()))
//            return false;

        LOGGER.log(Level.INFO, "Drug Database is Empty: " + Objects.requireNonNull(treatmentViewModel.getAllDrugs().getValue()).isEmpty());
        LOGGER.log(Level.INFO, "Drug Database is Empty: " + Objects.requireNonNull(treatmentViewModel.getAllDrugs().getValue()).size());
        Drug drug = treatmentViewModel.selectDrugFromName(allDrugs.getValue().get(drugSpinner.getSelectedItemPosition()).getName());

        if (drug.getName().equals("Undefined"))
            return false;

        if(TextUtils.isEmpty(editTextDate.getText()) || TextUtils.isEmpty(editTextTime.getText()))
            return false;

        Calendar cal = CalendarHelpers.stringToCalendar(editTextDate.getText().toString(), editTextTime.getText().toString());
        newPill = new Pill(drug.getId(), cal, pillQuantity);

        return true;

    }
}
