package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_94.R;

import java.net.MalformedURLException;

public class SignUpActivity extends AppCompatActivity {
    private EditText id, password, name;
    private TextView sex;
    private Spinner sexspinner;
    private Button btn_signup;
    private String curip = new getIP().getInstance();
    private String sexes[] = {"남자", "여자"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        id = (EditText)findViewById(R.id.signup_edit_id);
        password = (EditText)findViewById(R.id.signup_edit_password);
        name = (EditText)findViewById(R.id.signup_edit_name);
        sex = (TextView)findViewById(R.id.signup_text_sex);
        sexspinner = (Spinner)findViewById(R.id.signup_spinner_sex);
        btn_signup = (Button)findViewById(R.id.signup_button_signupButton);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexes);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexspinner.setAdapter(arrayAdapter);

        sexspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex.setText(sexes[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sex.setText("성별을 선택하세요.");
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://"+curip+"/SSAFYProject/customerinformation_insert.php");
                    String result = request.PhPtest(String.valueOf(id.getText()),String.valueOf(password.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage("회원가입에 성공했습니다.");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        alert.show();
                    }
                    else{
                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage("회원가입에 실패했습니다.");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alert.show();
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
