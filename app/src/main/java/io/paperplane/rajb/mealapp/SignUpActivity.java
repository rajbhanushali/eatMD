package io.paperplane.rajb.mealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText dobText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        dobText = findViewById(R.id.dob_edit_text);
        fab = findViewById(R.id.userDetailsFAB);

        dobText.setInputType(InputType.TYPE_NULL);

        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(
                        SignUpActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year1, int month1, int day1) {
                                    dobText.setText(month1 + "/" + day1 + "/" + year1);
                                }
                            },
                            year,
                            month,
                            day
                );

                picker.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(SignUpActivity.this, UserDetails.class);
                //startActivity(i);
                Toast.makeText(SignUpActivity.this, "hi", Toast.LENGTH_LONG).show();
            }
        });

    }


}
