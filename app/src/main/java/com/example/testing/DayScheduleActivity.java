package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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
        ArrayList<Activity> userActivities = db.getUserActivities(DBConnection.currentUser.id);
        {
            ArrayList<Activity> userActivitiesCopy = new ArrayList<>();
            for(Activity act : userActivities){
                if((Calendar.HOUR_OF_DAY < act.finishHour
                        || (Calendar.HOUR_OF_DAY == act.finishHour
                            && Calendar.MINUTE < act.finishMinute))
                        && (LocalDate.now().getDayOfYear() == act.day)){
                    userActivitiesCopy.add(act);
                }else{
                    db.deleteActivity(act.id);
                }
            }
            userActivities = userActivitiesCopy;
        }
        ActivityListAdapter adapter = new ActivityListAdapter(this, R.layout.adapterviewlayout, userActivities);
        mlistView.setAdapter(adapter);
    }

    public void goToCreateActivity(View view){
        Intent intent = new Intent(this,activity_add.class);
        startActivity(intent);
    }
}