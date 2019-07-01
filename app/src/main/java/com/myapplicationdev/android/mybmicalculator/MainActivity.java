package com.myapplicationdev.android.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvDate, tvBMI, tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvResult = findViewById(R.id.textViewResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double BMI = Math.round((Double.parseDouble(etWeight.getText().toString()) / Double.parseDouble(etHeight.getText().toString()) / Double.parseDouble(etHeight.getText().toString()))*1000.00)/1000.00;
                String msg = Double.toString(BMI);
                Calendar now = Calendar.getInstance(); //Create a Calendar object with current date and time

                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.append(datetime);
                tvBMI.append(msg);
                String result = "";
                if (BMI < 18.5) {
                    result = "You are underweight";
                } else if (BMI <= 18.5 && BMI < 25) {
                    result = "Your BMI is normal";
                } else if (BMI <= 25 && BMI < 30) {
                    result = "You are overweight";
                } else {
                    result = "You are obese";
                }

                tvResult.setText(result);

                //Step 2a: Obtain an instance of the SharedPreferences
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //Step 1b: Obtain an instance of the SharedPreference Editor for update later
                SharedPreferences.Editor prefEdit = prefs.edit();
                //Step 1c: Add the key-value pair
                prefEdit.putString("bmi", msg);
                prefEdit.putString("datetime", datetime);
                prefEdit.putString("result", result);
                //Step 1d: Call commit() method to save the changes into the SharedPreferences
                prefEdit.commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText(R.string.lcd);
                tvBMI.setText(R.string.lcb);
                etHeight.setText("");
                etWeight.setText("");
                tvResult.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data from the SharedPreferences object
        String msg = prefs.getString("bmi", "");
        String datetime = prefs.getString("datetime", "");
        String result = prefs.getString("result", "");

        //Step 2c: Update the UI element with the value
        tvDate.append(datetime);
        tvBMI.append(msg);
        tvResult.setText(result);
    }
}
