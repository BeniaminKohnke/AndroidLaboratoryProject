package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        EditText userNameView = findViewById(R.id.editTextTextPersonNameNick);
        String userNameFromView = userNameView.getText().toString();
        EditText userPasswordView = findViewById(R.id.editTextTextPersonPassword);
        String userPasswordFromView = userPasswordView.getText().toString();

        byte response = DBConnection.addUser(userNameFromView, userPasswordFromView);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,500);
            }
        });
    }
}