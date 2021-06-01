package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener( v -> Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show());

        CheckBox myCheckBox = findViewById(R.id.checked);
        myCheckBox.setOnCheckedChangeListener( (checkboxView, checked) -> {
            Snackbar.make(checkboxView, checked ? R.string.checkedOn : R.string.checkedOff , Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, v -> myCheckBox.setChecked(!checked))
                    .show();
        });

        ImageButton imgButton = findViewById(R.id.imgButton);

        Switch mySwitch = findViewById(R.id.switched);
        mySwitch.setOnCheckedChangeListener((switchView, switchedOn) -> {
           Snackbar.make(switchView, switchedOn ? R.string.switcedOn : R.string.switcedOff, Snackbar.LENGTH_LONG)
                   .setAction(R.string.undo, v -> mySwitch.setChecked(!switchedOn))
                   .show();

        });


    }
}