package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Activity.MainActivity;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Button login_btn_login;
    private EditText login_edit_id, login_edit_password;
    private TextView login_text_test;
    private String curip = new getIP().getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        login_btn_login = (Button)findViewById(R.id.login_btn_login);
        login_edit_id = (EditText)findViewById(R.id.login_edit_id);
        login_edit_password = (EditText)findViewById(R.id.login_edit_password);
        login_text_test = (TextView)findViewById(R.id.login_text_test);

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LoginTest logintest = new LoginTest("http://"+curip+"/SSAFYProject/customerinformation_login.php");
                    String jsonString = logintest.logintest(String.valueOf(login_edit_id.getText()), String.valueOf(login_edit_password.getText()));

                    JSONObject jsonobj = new JSONObject(jsonString);
                    JSONArray mypassword = jsonobj.getJSONArray("result");

                    if(mypassword.length() == 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage(getString(R.string.notexist_password));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alert.show();
                    }

                    else {
                        JSONObject c = mypassword.getJSONObject(0);
                        String pw = c.getString("password");

                        login_text_test.setText(pw.equals(login_edit_password.getText().toString())+"");

                        if(pw.equals(login_edit_password.getText().toString())) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage(getString(R.string.error_password));
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.show();
                        }
                    }
                }
                catch (MalformedURLException | JSONException e) {
                    Log.i("LoginActivity", e.getMessage());
                }
            }
        });
    }
}
