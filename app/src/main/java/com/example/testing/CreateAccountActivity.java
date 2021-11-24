package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                EditText userNameView = findViewById(R.id.createAccountUserNameId);

                if(userNameView.getVisibility() == View.VISIBLE){
                    EditText userPasswordView = findViewById(R.id.createAccountPasswordId);
                    EditText userRepeatPasswordView = findViewById(R.id.createAccountRepeatPasswordId);

                    String userNameFromView = userNameView.getText().toString();
                    String userPasswordFromView = userPasswordView.getText().toString();
                    String userRepeatPasswordFromView = userRepeatPasswordView.getText().toString();

                    ArrayList<KeyValuePair> conditions = new ArrayList<>();
                    conditions.add(new KeyValuePair(DBConnection.checkUserName(userNameFromView) == 1, "Nie można użyć nazwy"));
                    conditions.add(new KeyValuePair(!userPasswordFromView.isEmpty(), "Hasło nie może być puste"));
                    conditions.add(new KeyValuePair(userPasswordFromView.length() > 6, "Hasło jest za krótkie"));
                    conditions.add(new KeyValuePair(userPasswordFromView.equals(userRepeatPasswordFromView), "Hasła nie są równe"));

                    KeyValuePair condition = getFirstPairWithFalseKey(conditions);
                    if(condition != null){
                        ((TextView)findViewById(R.id.conditionTextId)).setText(condition.value);
                    }else{
                        ((TextView)findViewById(R.id.conditionTextId)).setText("");
                        findViewById(R.id.createButtonId).setVisibility(View.VISIBLE);
                    }
                }

                handler.postDelayed(this,200);
            }
        });
    }

    public void createAccountAction(View view){
        EditText userNameView = findViewById(R.id.createAccountUserNameId);
        String userNameFromView = userNameView.getText().toString();
        userNameView.setVisibility(View.INVISIBLE);

        EditText userPasswordView = findViewById(R.id.createAccountPasswordId);
        String userPasswordFromView = userPasswordView.getText().toString();
        userPasswordView.setVisibility(View.INVISIBLE);

        ((EditText)findViewById(R.id.createAccountRepeatPasswordId)).setVisibility(View.INVISIBLE);

        DBConnection.createAccount(userNameFromView, userPasswordFromView);
        ((TextView)findViewById(R.id.conditionTextId)).setText("Konto zostało utworzone");
        findViewById(R.id.createButtonId).setVisibility(View.INVISIBLE);
    }

    private class KeyValuePair{
        public boolean key;
        public String value;

        public KeyValuePair(boolean key, String value){
            this.key = key;
            this.value = value;
        }
    }

    private KeyValuePair getFirstPairWithFalseKey(ArrayList<KeyValuePair> pairs){
        for (KeyValuePair pair : pairs) {
            if(!pair.key){
                return pair;
            }
        }
        return null;
    }
}