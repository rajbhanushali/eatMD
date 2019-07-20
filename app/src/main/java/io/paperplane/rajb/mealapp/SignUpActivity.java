package io.paperplane.rajb.mealapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    DatePickerDialog picker;
    private EditText email, password, dobText, name;
    FloatingActionButton fab;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);
        name = findViewById(R.id.name_edit_text);
        dobText = findViewById(R.id.dob_edit_text);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

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
                                    dobText.setText(month1+1 + "/" + day1 + "/" + year1);
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

                //signUp(email.getText().toString(), password.getText().toString());

                Toast.makeText(SignUpActivity.this, "hi", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignUpActivity.this, PatientInfoActivity.class);
                startActivity(i);
            }
        });

    }

    private void signUp(final String emailEntered, String passwordEntered){
        mAuth.createUserWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(MainActivity.TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(MainActivity.TAG, "User profile updated.");
                                    }
                                }
                            });

                            ref = db.getReference(user.getUid());
                            ref.child("dob").setValue(dobText.getText().toString());
                            ref.child("name").setValue(name.getText().toString());
                            ref.child("email").setValue(emailEntered);

                            Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(MainActivity.TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
