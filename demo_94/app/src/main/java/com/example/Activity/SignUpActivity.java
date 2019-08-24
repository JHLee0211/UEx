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
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText id, password, name;
    private Spinner sexspinner, yearspinner, monthspinner, dayspinner;
    private Button btn_signup;
    private String curip = new getIP().getInstance();
    private String sexes[] = {"성별을 선택하세요.", "남자", "여자"};
    private String years[], months[], days[];
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        id = (EditText)findViewById(R.id.signup_edit_id);
        password = (EditText)findViewById(R.id.signup_edit_password);
        name = (EditText)findViewById(R.id.signup_edit_name);
        sexspinner = (Spinner)findViewById(R.id.signup_spinner_sex);
        yearspinner = (Spinner)findViewById(R.id.signup_spinner_year);
        monthspinner = (Spinner)findViewById(R.id.signup_spinner_month);
        dayspinner = (Spinner)findViewById(R.id.signup_spinner_day);
        btn_signup = (Button)findViewById(R.id.signup_button_signupButton);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dayspinner.setAdapter(dayAdapter);

        dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dayspinner.setSelection(1);
            }
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthspinner.setAdapter(monthAdapter);

        monthspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                monthspinner.setSelection(1);
            }
        });

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        yearspinner.setAdapter(yearAdapter);

        yearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                yearspinner.setSelection(1);
            }
        });

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexes);
        sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexspinner.setAdapter(sexAdapter);

        sexspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sexspinner.setSelection(1);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://"+curip+"/SSAFYProject/customerinformation_insert.php");
                    String curid = String.valueOf(id.getText());
                    String curpassword = String.valueOf(password.getText());
                    String curname = String.valueOf(name.getText());
                    String curbirth = yearspinner.getSelectedItem().toString() + "-" + monthspinner.getSelectedItem().toString() + "-" + dayspinner.getSelectedItem().toString();
                    String cursex = sexspinner.getSelectedItem().toString();
                    String result = request.PhPtest(curid, curpassword, curname, curbirth, cursex);
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle("Error");
                        //alert.setMessage("회원가입에 성공했습니다.");
                        alert.setMessage(curid + " " + curpassword + " " + curname + " " + curbirth + " " + cursex);
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
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage("생년월일을 확인해 주세요");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.show();
                }
            }
        });
    }

    private void init() {
        years = new String[101];
        years[0] = "년";
        int curyear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=1; i<years.length; i++) {
            years[i] = curyear--+"";
        }

        months = new String[13];
        months[0] = "월";
        for(int i=1; i<months.length; i++) {
            String temp = i+"";
            if(temp.length() == 1)
                temp = "0" + temp;
            months[i] = temp;
        }

        days = new String[32];
        days[0] = "일";
        for(int i=1; i<days.length; i++) {
            String temp = i+"";
            if(temp.length() == 1)
                temp = "0"+temp;
            days[i] = temp;
        }
    }
}
