package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.demo_94.R;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class TableActivity extends Fragment implements View.OnClickListener {
    private Button addBtn;
    private Button clearBtn;
    private Button saveBtn;
    private Button loadBtn;
    private TimetableView timetable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_table, container, false);

        addBtn = view.findViewById(R.id.add_btn);
        clearBtn = view.findViewById(R.id.clear_btn);
        saveBtn = view.findViewById(R.id.save_btn);
        loadBtn = view.findViewById(R.id.load_btn);

        timetable = view.findViewById(R.id.timetable);
        timetable.setHeaderHighlight(2);

        addBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
              //  Intent i = new Intent(this, TableEditActivity.class);
//                i.putExtra("mode", REQUEST_ADD);
//                startActivityForResult(i, REQUEST_ADD);
                break;
            case R.id.clear_btn:
                timetable.removeAll();
                break;
            case R.id.save_btn:
//                saveByPreference(timetable.createSaveData());
                break;
            case R.id.load_btn:
//                loadSavedData();
                break;
            default:
                Log.d("", "클릭이벤트 분류실패");
                break;
        }
    }
}