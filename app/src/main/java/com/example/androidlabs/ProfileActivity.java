package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageView mImageButton;
    EditText emailEditText;
    Button chatButton, weatherButton, toolBarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(ACTIVITY_NAME, "In Function: " + "onCreate()");

        Intent fromMain = getIntent();

        emailEditText = findViewById(R.id.profile_email);
        String email = fromMain.getStringExtra("EMAIL");
        emailEditText.setText(email);

        mImageButton = findViewById(R.id.ImageButton);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageButton.setOnClickListener(e -> {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        chatButton = findViewById(R.id.chat);
        Intent chatIntent = new Intent();
        chatButton.setOnClickListener( e -> {
            Intent goToChatRoom = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(goToChatRoom);
        });

        weatherButton = findViewById(R.id.weather);
        Intent weatherIntent = new Intent();
        weatherButton.setOnClickListener( e -> {
            Intent goToWeatherForecast = new Intent(ProfileActivity.this, WeatherForecastActivity.class);
            startActivity(goToWeatherForecast);
        });

        toolBarButton = findViewById(R.id.toolbarButton);
        Intent toolBarIntent = new Intent();
        toolBarButton.setOnClickListener( e -> {
            Intent goToToolBar = new Intent(ProfileActivity.this, TestToolbar.class);
            startActivity(goToToolBar);
        });

     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ACTIVITY_NAME, "In Function: " + "onActivityResult()");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
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
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In Function: " + "onPause()");
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