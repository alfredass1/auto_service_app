package com.auto.autoservice;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class NewEntryActivity extends AppCompatActivity {

    public static final String INSERT_URL = "http://forfuncom.epizy.com/mobile/db.php";

    Auto auto;

    CheckBox periodicCB;
    CheckBox engineCB;
    CheckBox chassisCB;

    RadioGroup cityRG;

    EditText makeModelET;
    EditText yearET;

    Spinner paymentSpin;

    Button newEntryBtn;
    Button updateEntryBtn;
    Button deleteEntryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        // checking if it's new or existing entry
        Intent intent = getIntent();
        auto = (Auto) intent.getSerializableExtra(AdapterAuto.ENTRY);

        newEntryBtn = findViewById(R.id.btn_create);
        updateEntryBtn = findViewById(R.id.btn_update);
        deleteEntryBtn = findViewById(R.id.btn_delete);

        if (auto == null) { // new entry- values by default
            setTitle(R.string.new_entry_meniu_title);

            auto = new Auto(
                    -1, // because it's not in db
                    getResources().getString(R.string.new_entry_maintenance_type_engine),
                    getResources().getString(R.string.new_entry_maintenance_city_Kaunas),
                    getResources().getString(R.string.new_entry_car_makeModel),
                    getResources().getString(R.string.new_entry_year),
                    getResources().getStringArray(R.array.new_entry_dinner_payment_type).toString()
            );

            updateEntryBtn.setEnabled(false);
            deleteEntryBtn.setEnabled(false);
            newEntryBtn.setEnabled(true);
        } else { // existing entry- values by entry
            setTitle(R.string.existing_entry_meniu_title);

            //TODO: implement update, delete buttons
            updateEntryBtn.setEnabled(true);
            deleteEntryBtn.setEnabled(true);
            newEntryBtn.setEnabled(false);
        }

        periodicCB = findViewById(R.id.checkEngine);
        engineCB = findViewById(R.id.checkChassis);
        chassisCB = findViewById(R.id.checkPeriodic);

        cityRG = findViewById(R.id.new_entry_city_group);

        makeModelET = findViewById(R.id.new_entry_car_makeModel);
        yearET = findViewById(R.id.new_entry_year);


        paymentSpin = findViewById(R.id.payment);

        setDataFromEntry(auto);

        newEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auto autoFromForm = getDataFromForm();
                insertToDB(autoFromForm);
                /*Toast.makeText(NewEntryActivity.this,
                        "Auto type: " + auto.getMaintenanceType() + "\n" +
                                "Delivery type: " + auto.getCity() + "\n" +
                                "Price: " + auto.getMakeModel() + "\n" +
                                "Payment: " + auto.getPayment() + "\n",
                        Toast.LENGTH_SHORT).show();*/
            }
        });


        updateEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewEntryActivity.this,
                        "Needs to be implemented",
                        Toast.LENGTH_SHORT).show();
            }
        });

        deleteEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewEntryActivity.this,
                        "Needs to be implemented",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDataFromEntry(Auto auto) {
        boolean isChecked = false;
        if (auto.getMaintenanceType().
                contains(getResources().getString(R.string.new_entry_maintenance_type_periodic))) {
            periodicCB.setChecked(true);
            isChecked = true;
        }
        if (auto.getMaintenanceType().
                contains(getResources().getString(R.string.new_entry_maintenance_type_chassis))) {
            engineCB.setChecked(true);
            isChecked = true;
        }
        if (auto.getMaintenanceType().
                contains(getResources().getString(R.string.new_entry_maintenance_type_engine))) {
            chassisCB.setChecked(true);
            isChecked = true;
        }
        if (!isChecked) { // was new entry - nothing to check
            chassisCB.setChecked(true); //// lets say it will be default value for new entry
        }

        if(!auto.getCity(). // delivery - no
                equalsIgnoreCase(getResources().getString(R.string.new_entry_maintenance_city_Kaunas))){
            ((RadioButton) cityRG.getChildAt(0)).setChecked(true);
        } else { // delivery - yes
            ((RadioButton) cityRG.getChildAt(1)).setChecked(true);
        }

        makeModelET.setText(String.valueOf(auto.getMakeModel()));
        yearET.setText(String.valueOf(auto.getYear()));

        // Sets spinner value from object payment type
        if (auto.getPayment().equalsIgnoreCase(getResources().getString(R.string.new_entry_payment_cash))) {
            paymentSpin.setSelection(0);
        } else if (auto.getPayment().equalsIgnoreCase(getResources().getString(R.string.new_entry_payment_card))) {
            paymentSpin.setSelection(1);
        } else if (auto.getPayment().equalsIgnoreCase(getResources().getString(R.string.new_entry_payment_moq))) {
            paymentSpin.setSelection(2);
        } else if (auto.getPayment().equalsIgnoreCase(getResources().getString(R.string.new_entry_payment_bitcoin))) {
            paymentSpin.setSelection(3);
        }

    }

    private Auto getDataFromForm() {
        String maintenanceTypes = "";
        if (periodicCB.isChecked()) {
            maintenanceTypes = maintenanceTypes + periodicCB.getText().toString() + " ";
        }
        if (engineCB.isChecked()) {
            maintenanceTypes = maintenanceTypes + engineCB.getText().toString() + " ";
        }
        if (chassisCB.isChecked()) {
            maintenanceTypes = maintenanceTypes + chassisCB.getText().toString() + " ";
        }

        int selectedDeliveryType = cityRG.getCheckedRadioButtonId();
        RadioButton deliveryType = findViewById(selectedDeliveryType);
        String selectedDeliveryTypeBtnName = deliveryType.getText().toString();

        String makeModel = String.valueOf(makeModelET.getText().toString());

        String year = String.valueOf(yearET.getText().toString());

        String payment = String.valueOf(paymentSpin.getSelectedItem());

        Auto auto = new Auto(maintenanceTypes, selectedDeliveryTypeBtnName, makeModel, year, payment);

        return auto;
    }

    private void insertToDB (Auto auto){
        class NewEntry extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            DB db = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this,
                        getResources().getString(R.string.new_entry_database_info),
                        null, true, true);
            }

            @Override
            protected String doInBackground(String... strings) {
                // Pirmas string yra raktas, antras - reikšmė.
                HashMap<String, String> auto = new HashMap<String, String>();
                auto.put("make_model", strings[0]);
                auto.put("year", strings[1]);
                auto.put("maintenance_type", strings[2]);
                auto.put("city", strings[3]);
                auto.put("payment", strings[4]);
                auto.put("action", "insert");

                String result = db.sendPostRequest(INSERT_URL, auto);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(NewEntryActivity.this,
                        s,
                        Toast.LENGTH_SHORT).show();
                Intent eitiIPaieskosLanga = new Intent(NewEntryActivity.this,SearchActivity.class);
                startActivity(eitiIPaieskosLanga);
            }

        }
        NewEntry newEntry = new NewEntry();
        newEntry.execute(
                auto.getMakeModel(),
                auto.getYear(),
                auto.getMaintenanceType(),
                auto.getCity(),
                auto.getPayment()
        );

    }


}
