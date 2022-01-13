package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DayScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);

        ListView mlistView = (ListView) findViewById(R.id.listview);

        DBConnection db = new DBConnection(DayScheduleActivity.this);

         ArrayList<Activity> yourActivitis = db.getUserActivities(DBConnection.currentUser.id);

        {
            ArrayList<Activity> copy = new ArrayList<>();
            for(Activity act : yourActivitis){
                if((Calendar.HOUR_OF_DAY < act.finishHour || (Calendar.HOUR_OF_DAY == act.finishHour && Calendar.MINUTE < act.finishMinute)) && (LocalDate.now().getDayOfYear() == act.day)){
                    copy.add(act);
                }
                else{
                    db.deleteActivity(act.id);

                }
            }
            yourActivitis = copy;
        }




        ActivityListAdapter adapter = new ActivityListAdapter(this, R.layout.adapterviewlayout, yourActivitis);
        mlistView.setAdapter(adapter);



    }

    public void goToCreateActivity(View view){
        Intent intent = new Intent(this,activity_add.class);
        startActivity(intent);

    }
}