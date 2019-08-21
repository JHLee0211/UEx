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
    private Button btn_login;
    private EditText id, password;
    private TextView testview;
    private String curip = "70.12.227.10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        btn_login = (Button)findViewById(R.id.login_button_loginbutton);
        id = (EditText)findViewById(R.id.login_edit_id);
        password = (EditText)findViewById(R.id.login_edit_password);
        testview = (TextView)findViewById(R.id.login_text_test);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LoginTest logintest = new LoginTest("http://"+curip+"/SSAFYProject/customerinformation_login.php");
                    String jsonString = logintest.logintest(String.valueOf(id.getText()), String.valueOf(password.getText()));

                    JSONObject jsonobj = new JSONObject(jsonString);
                    JSONArray mypassword = jsonobj.getJSONArray("result");

                    if(mypassword.length() == 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage("비밀번호가 존재하지 않습니다.");
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

                        testview.setText(pw.equals(password.getText().toString())+"");

                        if(pw.equals(password.getText().toString())) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage("비밀번호가 틀렸습니다.");
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
