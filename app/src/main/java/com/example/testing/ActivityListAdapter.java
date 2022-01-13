package com.example.testing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ActivityListAdapter extends ArrayAdapter<Activity> {

    private Context mContex;
    int mResource;

    public ActivityListAdapter(Context context, int resource, ArrayList<Activity> objects){
        super(context, resource, objects);
        mContex = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String name = getItem(position).getName();
        int startHour = getItem(position).getStartHour();
        int startMinute = getItem(position).getStartMinute();
        int finishHour = getItem(position).getFinishHour();
        int finishMinute = getItem(position).getFinishMinute();


        String priority = getItem(position).getPriority();
        String description = getItem(position).getDescription();

        Activity activity = new Activity(name,startHour,startMinute,finishHour,finishMinute,priority,description);

        LayoutInflater inflater = LayoutInflater.from(mContex);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvStartHour = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvFinishHour = (TextView) convertView.findViewById(R.id.textView7);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.textView4);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.textView5);

        tvName.setText(name);
        String placeHolder = String.valueOf(getItem(position).getStartHour()) +":"+ String.valueOf(getItem(position).getStartMinute());
        tvStartHour.setText(placeHolder);
        placeHolder =  String.valueOf(getItem(position).getFinishHour()) +":"+ String.valueOf(getItem(position).getFinishMinute());
        tvFinishHour.setText(placeHolder);
        tvPriority.setText(priority);
        tvDescription.setText(description);

        return convertView;
    }
}
