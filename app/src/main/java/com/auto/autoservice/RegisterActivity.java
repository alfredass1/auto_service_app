package com.auto.autoservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    public static final String DB_INSERT = "http://forfuncom.epizy.com/mobile/db.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nameET = findViewById(R.id.name1);
        final EditText emailET = findViewById(R.id.email);
        final EditText phoneET = findViewById(R.id.phone);
        final EditText passwordET = findViewById(R.id.password1);

        Button registerBtn1 = findViewById(R.id.register_btn1);

        registerBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //čia rašomas kodas, kuris bus vykdomas ant mygtuko paspaudimo//
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String phone = phoneET.getText().toString();
                String password = passwordET.getText().toString();

                emailET.setError(null);

                if (EmailValidation.isEmailValid(email)){
                    //----------------------------------------------------iš kur-------------į kur---------//
                    Intent gotoLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(gotoLoginActivity);
                } else {

                    emailET.setError(getResources().getString(R.string.register_invalid_email_message));
                    emailET.requestFocus();
                }

                Users users = new Users(name, email, password, phone);
                insertToDB(users);

                //Toast.makeText(RegisterActivity.this, "Name: " + name + "\n" + "email: "
                        //+ email + "\n" + "pasword: " + password, Toast.LENGTH_SHORT).show();
                //----------------------------------------------------iš kur-------------į kur---------//

            }
        });


    }

    private void insertToDB (Users users){
        class Register extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            DB db = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,
                        getResources().getString(R.string.new_entry_database_info),
                        null, true, true);
            }

            @Override
            protected String doInBackground(String... strings) {
                // Pirmas string yra raktas, antras - reikšmė.
                HashMap<String, String> users = new HashMap<String, String>();
                users.put("name", strings[0]);
                users.put("email", strings[1]);
                users.put("password", strings[2]);
                users.put("phone", strings[3]);
                users.put("action1", "insert1");

                String result = db.sendPostRequest(DB_INSERT, users);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(RegisterActivity.this,
                        s,
                        Toast.LENGTH_SHORT).show();
                Intent eitiIRegistracijosLanga = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(eitiIRegistracijosLanga);
            }
        }

        Register register = new Register();
        register.execute(
                users.getName(),
                users.getEmail(),
                users.getPassword(),
                users.getPhone()
        );

    }


}




