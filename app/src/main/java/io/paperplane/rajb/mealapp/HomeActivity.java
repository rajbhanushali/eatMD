package io.paperplane.rajb.mealapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    public static Context ctx;

    private TextView mTextMessage;

    private FirebaseUser user;
    private TextView text;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private Intent cameFrom;

    private String origin;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setContentView(R.layout.activity_home);
                    //mTextMessage.setText("Home");
                    return true;
                case R.id.navigation_dashboard:
                    setContentView(R.layout.dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("Notifications");
                    //setContentView(R.layout.notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Today's Meals");
        ctx = HomeActivity.this;

        try{

            cameFrom = getIntent();
            origin = cameFrom.getStringExtra("cameFrom");
            origin.equals("registering");

        }catch (Exception e){
            origin = "signing up";
        }

        Log.d(MainActivity.TAG, "cbhsfdSDF" + origin);

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
            List<RecipeActivity> anemiaList;

            //the recyclerview
            RecyclerView recyclerView;

            //getting the recyclerview from xml
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //initializing the productlist
            anemiaList = new ArrayList<>();

            //adding some items to our list
            anemiaList.add(
                    new RecipeActivity(
                            1,
                            "Veggie Stir Fry with Marinated Tofu or Chicken and Edamame",
                            "Prep Time: 5 mins, Cook Time: 10 mins",
                            4,
                            6,
                            R.drawable.thaisatay,
                            "http://www.bbcgoodfood.com/recipes/3219/thai-satay-stirfry/ "));

            anemiaList.add(
                    new RecipeActivity(
                            1,
                            "Quinoa salad with tofu, toasted almonds, cranberries and tahini dressing",
                            "Prep Time: 10 mins, Cook Time: 20 mins",
                            5,
                            4,
                            R.drawable.quinoasalad,"http://www.shelikesfood.com/1/post/2016/04/kale-quinoa-salad-cherry-tomatoes-lemon-tahini-dressing.html"));

            anemiaList.add(
                    new RecipeActivity(
                            1,
                            "Pesto pasta with beans, peas and roasted capsicum",
                            "Prep Time: 20 mins, Cook: 12 mins",
                            5,
                            5,
                            R.drawable.pestopasta,
                            "http://www.foodnetwork.com/recipes/ina-garten/pasta-pesto-and-peas-recipe2.html"));

            //creating recyclerview adapter
            RecipeAdapter adapter = new RecipeAdapter(this, anemiaList);

            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);


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

        public void openURL(String url){
            Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
            startActivity(openURL);
        }

    }

