package com.example.testing;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
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
import java.time.LocalDate;
import java.util.Calendar;

public class activity_add extends AppCompatActivity {
    Button starthour,finishhour,addBtn;
    int mHour,mMin,fHour,fMin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        createNotificationChannel();
        starthour = findViewById(R.id.startHourButton);
        finishhour = findViewById(R.id.finishHourButton);
        addBtn = findViewById(R.id.button2);
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
                        mHour = hourOfDay;
                        mMin = minute;
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
                        fHour = hourOfDay;
                        fMin = minute;
                    }
                },fHour,fMin,true);
                timePickerDialog.show();
            }
        });

        addBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                if(radioGroup.getCheckedRadioButtonId() != 0){
                int selectedRadioBtnID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedRadioBtnID);
                CheckBox chBox = findViewById(R.id.checkBox);
                if((Calendar.HOUR_OF_DAY < fHour) || (Calendar.HOUR_OF_DAY == fHour && Calendar.MINUTE < fMin)) {
                    Activity activity = new Activity();
                    if(chBox.isChecked()){ //tworzy powiadomienie
                        Intent intent = new Intent(activity_add.this, ReminderBroadcast.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity_add.this, 0, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        long timeAtCreatingNotification = System.currentTimeMillis();
                        if (mMin > Calendar.MINUTE) {

                            long timeInMiliSec = (long) (mHour - Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + (long) (mMin - Calendar.MINUTE) * 60 * 1000;
                            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMiliSec + timeAtCreatingNotification, pendingIntent);
                        } else if (mMin != Calendar.MINUTE) {
                            long timeInMiliSec = (long) (mHour - Calendar.HOUR_OF_DAY - 1) * 60 * 60 * 1000 + (long) (60 - Calendar.MINUTE + mMin) * 60 * 1000;
                            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMiliSec + timeAtCreatingNotification, pendingIntent);
                        }
                    }
                    activity.name = ((EditText) findViewById(R.id.ActivityName)).getText().toString();
                    activity.description = ((EditText) findViewById(R.id.bonusInfo)).getText().toString();
                    activity.priority = (radioButton.getText()).toString();
                    activity.startHour = mHour;
                    activity.startMinute = mMin;
                    activity.finishHour = fHour;
                    activity.finishMinute = fMin;
                    activity.day = LocalDate.now().getDayOfYear();
                    activity.userId = DBConnection.currentUser.id;
                    new DBConnection(activity_add.this).addActivity(DBConnection.currentUser.id, activity);
                    Intent intent = new Intent(activity_add.this,DayScheduleActivity.class);
                    startActivity(intent);

                }}

            }}));
    }

    public void createNotificationChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for sending reminding notification of actvity";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}



