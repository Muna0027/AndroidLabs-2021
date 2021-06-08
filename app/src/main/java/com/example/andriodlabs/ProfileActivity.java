package com.example.andriodlabs;

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

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageView mImageButton;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(ACTIVITY_NAME, "In Function: " + "onCreate()");

        Intent fromMain = getIntent();

        emailEditText = findViewById(R.id.profile_email);
        String email = fromMain.getStringExtra("EMAIL");
        emailEditText.setText(email);

        mImageButton = (ImageButton)findViewById(R.id.ImageButton);
        mImageButton.setOnClickListener(e -> {
            dispatchTakePictureIntent();
        });

    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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