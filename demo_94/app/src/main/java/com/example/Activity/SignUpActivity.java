package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText signup_edit_id, signup_edit_password, signup_edit_name;
    private Spinner signup_spinner_sex, signup_spinner_year, signup_spinner_month, signup_spinner_day;
    private Button signup_btn_signup;
    private String curip = new getIP().getInstance();
    private String sexes[] = {"성별을 선택하세요.", "man", "woman"};
    private String years[], months[], days[];
    private TextView signup_text_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        signup_edit_id = (EditText)findViewById(R.id.signup_edit_id);
        signup_edit_password = (EditText)findViewById(R.id.signup_edit_password);
        signup_edit_name = (EditText)findViewById(R.id.signup_edit_name);
        signup_spinner_sex = (Spinner)findViewById(R.id.signup_spinner_sex);
        signup_spinner_year = (Spinner)findViewById(R.id.signup_spinner_year);
        signup_spinner_month = (Spinner)findViewById(R.id.signup_spinner_month);
        signup_spinner_day = (Spinner)findViewById(R.id.signup_spinner_day);
        signup_btn_signup = (Button)findViewById(R.id.signup_btn_signup);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        signup_spinner_day.setAdapter(dayAdapter);

        signup_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                signup_spinner_day.setSelection(1);
            }
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        signup_spinner_month.setAdapter(monthAdapter);

        signup_spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                signup_spinner_month.setSelection(1);
            }
        });

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        signup_spinner_year.setAdapter(yearAdapter);

        signup_spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                signup_spinner_year.setSelection(1);
            }
        });

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexes);
        sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        signup_spinner_sex.setAdapter(sexAdapter);

        signup_spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                signup_spinner_sex.setSelection(1);
            }
        });

        signup_btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    SignUpTest signuptest = new SignUpTest("http://"+curip+"/SSAFYProject/customerinformation_insert.php");
                    String curid = String.valueOf(signup_edit_id.getText());
                    String curpassword = String.valueOf(signup_edit_password.getText());
                    String curname = String.valueOf(signup_edit_name.getText());
                    String curbirth = signup_spinner_year.getSelectedItem().toString() + "-" + signup_spinner_month.getSelectedItem().toString() + "-" + signup_spinner_day.getSelectedItem().toString();
                    String cursex = signup_spinner_sex.getSelectedItem().toString();
                    String result = signuptest.signuptest(curid, curpassword, curname, curbirth, cursex);
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),getString(R.string.success_signin),Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle("Success");
                        alert.setMessage(getString(R.string.success_signin));
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
                        DuplicateCheckTest duplicatechecktest = new DuplicateCheckTest("http://"+curip+"/SSAFYProject/customerinformation_duplicatecheck.php");
                        String checkresult = duplicatechecktest.duplicatechecktest(curid);

                        JSONObject jsonobj = new JSONObject(checkresult);
                        String myid = jsonobj.getJSONArray("result").getJSONObject(0).getString("id");

                        if(curid.equals(myid)) {
                            Toast.makeText(getApplication(), getString(R.string.duplicate_id), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage(getString(R.string.duplicate_id));
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.show();
                        }
                        else {
                            Toast.makeText(getApplication(),getString(R.string.error_signin),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                            alert.setTitle("Error");
                            alert.setMessage(R.string.error_signin);
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
                    e.printStackTrace();
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage(getString(R.string.check_birth));
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