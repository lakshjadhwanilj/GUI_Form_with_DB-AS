package com.example.contactformwithdb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    ArrayList<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ListView listView = findViewById(R.id.listView);

        SQLiteDatabase contactFormDB = this.openOrCreateDatabase("ContactForm", MODE_PRIVATE, null);

        Cursor c = contactFormDB.rawQuery("SELECT * FROM users", null);

        int nameIndex = c.getColumnIndex("name");
        int emailIndex = c.getColumnIndex("email");
        int dobIndex = c.getColumnIndex("dob");
        int phoneIndex = c.getColumnIndex("phone");
        int countryIndex = c.getColumnIndex("country");
        int genderIndex = c.getColumnIndex("gender");

        c.moveToFirst();

        while (!c.isAfterLast()){
            dataList.add(
                    "Name: " + c.getString(nameIndex) +
                    "\nEmail: " + c.getString(emailIndex) +
                    "\nDate of Birth: " + c.getString(dobIndex) +
                    "\nPhone Number: " + c.getString(phoneIndex) +
                    "\nCountry: " + c.getString(countryIndex) +
                    "\nGender: " + c.getString(genderIndex)
            );
            c.moveToNext();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(arrayAdapter);

    }
}