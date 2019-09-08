package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo_94.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button main_btn_output, main_btn_login, main_btn_signup, main_btn_search, main_btn_logout;
    private TextView main_text_hello;
    public static boolean cur_session = false;
    public static String cur_cookies = "";
    public static String cur_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.setNetworkPolicy();

        main_btn_output = (Button)findViewById(R.id.main_btn_output);
        main_btn_login = (Button)findViewById(R.id.main_btn_login);
        main_btn_logout = (Button)findViewById(R.id.main_btn_logout);
        main_btn_signup = (Button)findViewById(R.id.main_btn_signup);
        main_btn_search = (Button)findViewById(R.id.main_btn_search);
        main_text_hello = (TextView)findViewById(R.id.main_text_hello);

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

        main_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Logout().logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SessionCheck().sessioncheck()) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("message", getApplicationContext().toString());
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage(getString(R.string.error_loginservice));
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.show();
                }
            }
        });

        if(new SessionCheck().sessioncheck()) {
            main_text_hello.setText(cur_id + "님 반갑습니다!");
            main_btn_login.setVisibility(View.GONE);
            main_btn_logout.setVisibility(View.VISIBLE);
        } else {
            main_text_hello.setText("");
            main_btn_login.setVisibility(View.VISIBLE);
            main_btn_logout.setVisibility(View.GONE);
        }
    }
}
