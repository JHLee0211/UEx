package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_94.R;

import java.net.MalformedURLException;

public class SignUpActivity extends AppCompatActivity {
    private EditText id, password;
    private Button btn_signup, btn_test;
    private String curip = "70.12.227.10";
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        id = (EditText)findViewById(R.id.signup_edit_id);
        password = (EditText)findViewById(R.id.signup_edit_password);
        btn_signup = (Button)findViewById(R.id.signup_button_signupButton);
        test = (TextView)findViewById(R.id.signup_text_textText);
        btn_test = (Button)findViewById(R.id.signup_button_testButton);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test.setText("성공");
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                test.setText("성공");
                try {
                    PHPRequest request = new PHPRequest("http://"+curip+"/SSAFYProject/customerinformation_insert.php");
                    String result = request.PhPtest(String.valueOf(id.getText()),String.valueOf(password.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
