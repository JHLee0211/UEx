package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

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
                    JSONArray result = jsonobj.getJSONArray("result");

                    JSONObject c = result.getJSONObject(0);
                    String IsLogin = c.getString("IsLogin");

                    if(IsLogin.equals("Y")) {
                        Toast.makeText(getApplication(),getString(R.string.success_login),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        MainActivity.cur_id = String.valueOf(login_edit_id.getText());
                        MainActivity.cur_session = true;
                        jsonString = new AutoLogin("http://"+curip+"/SSAFYProject/customerinformation_autologinsignup.php").autologinsignup();
                        if(jsonString.equals("-1")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage("autologin error");
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.show();
                        }
                        startActivity(intent);
                    }
                    else {
                        MainActivity.cur_session = false;
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage(getString(R.string.error_login));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alert.show();
                    }
                }
                catch (MalformedURLException | JSONException e) {
                    Log.i("LoginActivity", e.getMessage());
                }
            }
        });
    }
}
