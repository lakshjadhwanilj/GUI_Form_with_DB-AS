package com.example.contactformwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name, email, dob, phone;
    Spinner country;
    RadioGroup gender;
    RadioButton selectedGender;
    Button reset, next, show;

    String[] countries = {"Select Country", "China", "France", "Germany", "India", "Italy", "Japan", "Pakistan", "Russia", "Sri Lanka", "USA"};

    String nameDB, emailDB, dobDB, phoneDB, countryDB, genderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Edit Texts
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        phone = findViewById(R.id.phone);

        // Spinner
        country = findViewById(R.id.country);
        country.setOnItemSelectedListener(this);

        // Array Adapter
        ArrayAdapter countryNames = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        countryNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryNames);

        // Radio
        gender = findViewById(R.id.gender);

        // Buttons
        reset = findViewById(R.id.reset);
        next = findViewById(R.id.next);
        show = findViewById(R.id.show);

        // Databases
        SQLiteDatabase contactFormDB = this.openOrCreateDatabase("ContactForm", MODE_PRIVATE, null);
        contactFormDB.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, email VARCHAR, dob VARCHAR, phone NUMBER(10), country VARCHAR, gender VARCHAR)");

        // Show submissions
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transfer to data activity
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(intent);
            }
        });

        // Reset all fields
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clearing Fields
                name.setText("");
                email.setText("");
                dob.setText("");
                phone.setText("");
                country.setSelection(0);
                gender.clearCheck();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adding to data to DB
                nameDB = name.getText().toString();
                emailDB = email.getText().toString();
                dobDB = dob.getText().toString();
                phoneDB = phone.getText().toString();

                countryDB = country.getSelectedItem().toString();

                int selectedId = gender.getCheckedRadioButtonId();
                selectedGender = findViewById(selectedId);
                genderDB = selectedGender.getText().toString();

                // Storing data in database
                contactFormDB.execSQL("INSERT INTO users VALUES ('" + nameDB + "', '" + emailDB + "', '" + dobDB + "', " + phoneDB + ", '" + countryDB + "', '" + genderDB +"');");

                // Transfer to data activity
                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                startActivity(intent);

                // Clearing Fields
                name.setText("");
                email.setText("");
                dob.setText("");
                phone.setText("");
                country.setSelection(0);
                gender.clearCheck();

                Toast.makeText(getApplicationContext(), "Your form has been submitted. Thank You!", Toast.LENGTH_LONG).show();

            }
        });

        Cursor c = contactFormDB.rawQuery("SELECT * FROM users", null);
        int nameIndex = c.getColumnIndex("name");
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i("name", c.getString(nameIndex));
            c.moveToNext();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}