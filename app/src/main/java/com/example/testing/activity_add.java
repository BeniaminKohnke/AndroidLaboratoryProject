package com.example.testing;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;


import java.util.Calendar;

public class activity_add extends AppCompatActivity {

    Button starthour,finishhour;
    int mHour,mMin,fHour,fMin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        starthour = findViewById(R.id.startHourButton);
        finishhour = findViewById(R.id.finishHourButton);



        starthour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Calendar c = Calendar.getInstance();

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_add.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(activity_add.this, ""+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();

                    }
                },mHour,mMin,true);

                timePickerDialog.show();

            }
        });

        finishhour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Calendar c = Calendar.getInstance();

                fHour = c.get(Calendar.HOUR_OF_DAY);
                fMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(activity_add.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(activity_add.this, ""+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
                    }
                },fHour,fMin,true);

                timePickerDialog.show();
            }
        });
    }

    public void addActivityAction(){ // przycisk DODAJ
        //
         RadioGroup radioGroup = findViewById(R.id.radioGroup);
         int selectedRadioBtnID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButtin = findViewById(selectedRadioBtnID);

        CheckBox chBox = findViewById(R.id.checkBox);
        if(chBox.isChecked() && ((Calendar.HOUR_OF_DAY < mHour) || (Calendar.HOUR_OF_DAY == mHour && Calendar.MINUTE < mMin))){

            //tworzy powiadomienie
        }
        // trzeba zapisać godziny mHour,mMin godzina startu aktywności
        // fHour,fMin godziny zakończenia

        String nazwa = ((EditText)findViewById(R.id.ActivityName)).getText().toString();
        String info = ((EditText)findViewById(R.id.bonusInfo)).getText().toString();

        // dodatkowe info
        String priorytet  = (radioButtin.getText()).toString();

    }






}