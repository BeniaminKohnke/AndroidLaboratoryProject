package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String PASSWORD_VIEW_TEXT = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String passwordFromView = ((EditText)findViewById(R.id.passwordViewId)).getText().toString();
        String userNameFromView = ((EditText)findViewById(R.id.userNameViewId)).getText().toString();

        if(DBConnection.checkLoggingData(userNameFromView, passwordFromView) == 1){
            intent.putExtra(PASSWORD_VIEW_TEXT, passwordFromView);
            startActivity(intent);
        }else{
            findViewById(R.id.errorPassword).setVisibility(View.VISIBLE);
        }
    }
    public void createdAccountAction(View view){
        Intent intent = new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
    }
}