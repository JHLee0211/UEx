package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button main_btn_output, main_btn_login, main_btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.setNetworkPolicy();

        main_btn_output = (Button)findViewById(R.id.main_btn_output);
        main_btn_login = (Button)findViewById(R.id.main_btn_login);
        main_btn_signup = (Button)findViewById(R.id.main_btn_signup);

        main_btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_output.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TotalListActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });
    }
}
