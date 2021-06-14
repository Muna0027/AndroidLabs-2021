package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    EditText email;
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("FileName", "Please Enter your email");

        Button loginButton = findViewById(R.id.login);
        email = findViewById(R.id.email);
        email.setText(savedString);

        loginButton.setOnClickListener( e -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            String em = email.getText().toString();
            goToProfile.putExtra("EMAIL", em);
            startActivity(goToProfile);
        });

    }

    private void saveData(String stringToSave) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FileName", stringToSave);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData(email.getText().toString());
        Log.e(ACTIVITY_NAME, "In Function: " + "onPause()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In Function: " + "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In Function: " + "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In Function: " + "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In Function: " + "onDestroy()");
    }

}