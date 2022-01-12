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

                    StringBuilder errors = new StringBuilder("Błędy: ");
                    boolean isCorrect = true;

                    if(userNameFromView.isEmpty() || new DBConnection(CreateAccountActivity.this).checkUserName(userNameFromView)){
                        errors.append("nie można użyć nazwy, ");
                        findViewById(R.id.greenMarkNameOrNick).setVisibility(View.INVISIBLE);
                        findViewById(R.id.redCrossNameOrNick).setVisibility(View.VISIBLE);
                        isCorrect = false;
                    }else{
                        findViewById(R.id.greenMarkNameOrNick).setVisibility(View.VISIBLE);
                        findViewById(R.id.redCrossNameOrNick).setVisibility(View.INVISIBLE);
                    }

                    {
                        boolean isPasswordEmpty = userPasswordFromView.isEmpty();
                        boolean isPasswordToShort = !(userPasswordFromView.length() > 6);

                        if(isPasswordEmpty || isPasswordToShort){
                            if(isPasswordEmpty){
                                errors.append("hasło nie może być puste, ");
                            }
                            if(isPasswordToShort && !isPasswordEmpty){
                                errors.append("hasło jest za krótkie, ");
                            }

                            findViewById(R.id.greenMarkPassword).setVisibility(View.INVISIBLE);
                            findViewById(R.id.redCrossPassword).setVisibility(View.VISIBLE);
                            isCorrect = false;
                        }else{
                            findViewById(R.id.greenMarkPassword).setVisibility(View.VISIBLE);
                            findViewById(R.id.redCrossPassword).setVisibility(View.INVISIBLE);
                        }

                        boolean areEqual = userPasswordFromView.equals(userRepeatPasswordFromView);
                        if(!areEqual || isPasswordEmpty || isPasswordToShort) {
                            if(!areEqual){
                                errors.append("hasła nie są równe, ");
                            }
                            findViewById(R.id.greenMarkRepetPassword).setVisibility(View.INVISIBLE);
                            findViewById(R.id.redCrossRepetPassword).setVisibility(View.VISIBLE);
                            isCorrect = false;
                        }else{
                            findViewById(R.id.greenMarkRepetPassword).setVisibility(View.VISIBLE);
                            findViewById(R.id.redCrossRepetPassword).setVisibility(View.INVISIBLE);
                        }
                    }


                    if(isCorrect){
                        ((TextView)findViewById(R.id.conditionTextId)).setText("");
                        findViewById(R.id.createButtonId).setVisibility(View.VISIBLE);
                    }else{
                        findViewById(R.id.createButtonId).setVisibility(View.INVISIBLE);
                        ((TextView)findViewById(R.id.conditionTextId)).setText(errors.toString());
                    }
                }

                handler.postDelayed(this,200);
            }
        });
    }

    public void createAccountAction(View view){
        EditText userNameView = findViewById(R.id.createAccountUserNameId);
        String userNameFromView = userNameView.getText().toString();

        EditText userPasswordView = findViewById(R.id.createAccountPasswordId);
        String userPasswordFromView = userPasswordView.getText().toString();

        new DBConnection(CreateAccountActivity.this).createAccount(userNameFromView, userPasswordFromView);
        finish();
    }
}