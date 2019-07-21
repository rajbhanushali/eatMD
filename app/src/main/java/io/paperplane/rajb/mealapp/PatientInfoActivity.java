package io.paperplane.rajb.mealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientInfoActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseUser user;

    private EditText feet, inches, weight, docemail;
    private RadioGroup gender;

    private FloatingActionButton infoSubmitFAB;

    private Intent toHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        getSupportActionBar().hide();

        toHome = new Intent(PatientInfoActivity.this, HomeActivity.class);
        toHome.putExtra("cameFrom", "registering");

        db = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = db.getReference(user.getUid());

        feet = findViewById(R.id.feet_edit_text);
        inches = findViewById(R.id.inches_edit_text);
        weight = findViewById(R.id.weight_edit_text);
        docemail = findViewById(R.id.docemail_edit_text);
        gender = findViewById(R.id.gender);
        infoSubmitFAB = findViewById(R.id.submitFAB);

        gender.getCheckedRadioButtonId();

        infoSubmitFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int genid=gender.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(genid);
                String gender=radioButton.getText().toString();

                Toast.makeText(PatientInfoActivity.this, "submitting", Toast.LENGTH_SHORT).show();
                ref.child("feet").setValue(feet.getText().toString());
                ref.child("inches").setValue(inches.getText().toString());
                ref.child("weight").setValue(weight.getText().toString());
                ref.child("gender").setValue(gender);
                ref.child("docemail").setValue(docemail.getText().toString());
                ref.child("docPaired").setValue(false);

                // Read from the database
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        startActivity(toHome);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(PatientInfoActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
