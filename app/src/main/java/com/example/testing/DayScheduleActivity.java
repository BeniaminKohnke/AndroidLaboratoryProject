package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DayScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.PASSWORD_VIEW_TEXT);

        TextView textView = findViewById(R.id.textViewId);
        textView.setText(message);
    }
}