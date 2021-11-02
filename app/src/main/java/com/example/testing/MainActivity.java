package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String haslo = "com.example.myfirstapp.MESSAGE";
    public  String password = "jp2gmd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.wpisaniehasla);
        String message = editText.getText().toString();
        if(message.equals(password))
        {
                intent.putExtra(haslo, message);
                startActivity(intent);
        }
        else
        {
            TextView textView = findViewById(R.id.ErrorPassword);
            textView.setVisibility(View.VISIBLE);
        }
    }
    public void createdAccountAction(View view)
    {
        Intent intent = new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
    }
}