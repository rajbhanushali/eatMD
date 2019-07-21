package io.paperplane.rajb.mealapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private String emailEntered, passwordEntered;
    private View v;

    public static final String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize login
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_edit_text);
        password = findViewById(R.id.password_edit_text);

        FloatingActionButton fab = findViewById(R.id.login);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v = view;

                emailEntered = email.getText().toString();
                passwordEntered = password.getText().toString();

                if(emailEntered != "" && passwordEntered != "") {

                    signIn(emailEntered, passwordEntered);

                }

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void goToSignUp(View v){
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        i.putExtra("cameFrom", "signing in");
        startActivity(i);
    }

    private void signIn(String emailEntered, String passwordEntered){

        mAuth.signInWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Snackbar.make(v, "Succesful Login!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(v, "Sign in failed! Try again.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
