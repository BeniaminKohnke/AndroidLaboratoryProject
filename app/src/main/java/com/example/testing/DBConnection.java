package com.example.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBConnection extends SQLiteOpenHelper  {
    public static User currentUser = new User();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "androidProject";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ACTIVITIES = "activities";

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean createAccount(String userName, String password){
        if (checkUserName(userName)){
            return false;
        }else{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", userName);
            values.put("password", password);
            db.insert(TABLE_USERS, null, values);
            db.close();
            return true;
        }
    }

    public boolean checkUserName(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { "id", "name", "password" }, "name =?",
                new String[] { userName.toUpperCase() }, null, null, null, null);
        if(cursor != null){
            boolean exists = cursor.moveToFirst();
            cursor.close();
            db.close();
            return exists;
        }
        return false;
    }

    /** Returns user's id*/
    public int checkLoggingData(String userName, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { "id",
                        "name", "password" },  "name =?",
                new String[] { userName.toUpperCase() }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            if(cursor.getString(1).toUpperCase().equals(userName.toUpperCase())
                    && cursor.getString(2).toUpperCase().equals(password.toUpperCase())){
                int id = Integer.parseInt(cursor.getString(0));
                cursor.close();
                return id;
            }
        }
        db.close();
        return -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT);";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_ACTIVITIES_TABLE
                = "CREATE TABLE " + TABLE_ACTIVITIES
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, name TEXT, startHour INTEGER, startMinute INTEGER, finishHour INTEGER, finishMinute INTEGER, description TEXT, priority TEXT, FOREIGN KEY(userId) REFERENCES users(id));";
        db.execSQL(CREATE_ACTIVITIES_TABLE);
    }

    public void addActivity(int id, Activity activity){
        if(id != -1){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();

            values.put("userId", id);
            values.put("name", activity.name);
            values.put("description", activity.description);
            values.put("startDate", activity.startHour);
            values.put("finishDate", activity.finishHour);
            values.put("priority", activity.priority);

            db.insert(TABLE_ACTIVITIES, null, values);
            db.close();
        }
    }

    public ArrayList<Activity> getUserActivities(int id){
        ArrayList<Activity> activities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[] { "id", "userId", "name", "startHour", "startMinute", "finishHour", "finishMinute", "description", "priority" },
                "userId =?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            do{
                Activity activity = new Activity();
                activity.id = Integer.parseInt(cursor.getString(0));
                activity.userId = Integer.parseInt(cursor.getString(1));
                activity.name = cursor.getString(2);
                activity.startHour = Integer.parseInt(cursor.getString(3));
                activity.startMinute = Integer.parseInt(cursor.getString(4));
                activity.finishHour = Integer.parseInt(cursor.getString(5));
                activity.finishMinute = Integer.parseInt(cursor.getString(6));
                activity.description = cursor.getString(7);
                activity.priority = cursor.getString(8);
                activities.add(activity);
            }
            while(cursor.moveToNext());
            cursor.close();
        }
        return activities;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES + ";");
        onCreate(db);
    }
}