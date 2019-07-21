package io.paperplane.rajb.mealapp;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private FirebaseUser user;
    private TextView text;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private Intent cameFrom;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("Home");
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText("Dashboard");
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("Notifications");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Profile");
        String origin;

        try{

            cameFrom = getIntent();
            origin = cameFrom.getStringExtra("cameFrom");
            origin.equals("registering");

        }catch (Exception e){
            origin = "signing up";
        }

        if (origin.equals("registering")) {
            setContentView(R.layout.activity_pairdoc);
        } else {
            setContentView(R.layout.activity_home);

            user = FirebaseAuth.getInstance().getCurrentUser();
            db = FirebaseDatabase.getInstance();
            ref = db.getReference(user.getUid());

            text = findViewById(R.id.message);


            text.setText("Welcome, " + user.getDisplayName() + "!");

            BottomNavigationView navView = findViewById(R.id.nav_view);
            mTextMessage = findViewById(R.id.message);
            navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            // Read from the database

            List<RecipeActivity> recipeList;

            //the recyclerview
            RecyclerView recyclerView;

                //getting the recyclerview from xml
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                //initializing the productlist
                recipeList = new ArrayList<>();


                //adding some items to our list
            recipeList.add(
                        new RecipeActivity(
                                1,
                                "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                                "13.3 inch, Silver, 1.35 kg",
                                4.3,
                                60000,
                                R.drawable.macbook));

            recipeList.add(
                        new RecipeActivity(
                                1,
                                "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                                "14 inch, Gray, 1.659 kg",
                                4.3,
                                60000,
                                R.drawable.dellinspiron));

            recipeList.add(
                        new RecipeActivity(
                                1,
                                "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                                "13.3 inch, Silver, 1.35 kg",
                                4.3,
                                60000,
                                R.drawable.surface));

                //creating recyclerview adapter
                RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //String value = dataSnapshot.getValue(String.class);

                    //Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }

    }

