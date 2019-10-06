package com.example.calendar.etc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.demo_94.R;
import java.util.ArrayList;


public class DateDialog extends AppCompatActivity {
    public static TextView morning, lunch, dinner;
    private static TextView routine;
    private static TextView record;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        Activity a = (Activity) context;
        a.setContentView(R.layout.item_date_dialog);
        Intent intent = getIntent();

        int position = intent.getExtras().getInt("position");
        ArrayList<Integer> setDate = intent.getExtras().getIntegerArrayList("array");

        morning = (TextView) findViewById(R.id.morning);
        morning.setText("08:00 IM Exam");
        lunch = (TextView) findViewById(R.id.lunch);
        lunch.setText("15:00 ~ 18:00 A형 시험");
        dinner = (TextView) findViewById(R.id.dinner);
        dinner.setText("19:00 ~ 23:00 B형 시험");
    }
    public String getMorning(){
        return morning.getText().toString();
    }

    public String getRoutine(){
        return routine.getText().toString();
    }

    public String getRecord(){
        return record.getText().toString();
    }

}
