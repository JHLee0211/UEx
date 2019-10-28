package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.Alert;
import com.example.dao.AutoLogin.DuplicateCheck;
import com.example.dao.Connection.getIP;
import com.example.dao.User.SignUpTest;
import com.example.demo_94.R;

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

        signup_edit_id = (EditText)findViewById(R.id.signup_edit_id);
        signup_edit_password = (EditText)findViewById(R.id.signup_edit_password);
        signup_edit_name = (EditText)findViewById(R.id.signup_edit_name);
        signup_spinner_sex = (Spinner)findViewById(R.id.signup_spinner_sex);
        signup_spinner_year = (Spinner)findViewById(R.id.signup_spinner_year);
        signup_spinner_month = (Spinner)findViewById(R.id.signup_spinner_month);
        signup_spinner_day = (Spinner)findViewById(R.id.signup_spinner_day);
        signup_btn_signup = (Button)findViewById(R.id.signup_btn_signup);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        signup_spinner_year.setAdapter(yearAdapter);

        signup_spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0) {
                    months = new String[13];
                    months[0] = "월";
                    for(int i=1; i<months.length; i++) {
                        String temp = i+"";
                        if(temp.length() == 1)
                            temp = "0" + temp;
                        months[i] = temp;
                    }

                    final String curyear = (String)adapterView.getItemAtPosition(position);
                    ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, months);
                    monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    signup_spinner_month.setAdapter(monthAdapter);

                    signup_spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if(position != 0) {
                                String selectday = (String)adapterView.getItemAtPosition(position);
                                Calendar cal = Calendar.getInstance();
                                cal.set(Integer.valueOf(curyear), Integer.valueOf(selectday)-1, 1);
                                int maxday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                                days = new String[maxday+1];
                                days[0] = "일";
                                for(int i=1; i<days.length; i++) {
                                    String temp = i+"";
                                    if(temp.length() == 1)
                                        temp = "0"+temp;
                                    days[i] = temp;
                                }

                                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, days);
                                dayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                signup_spinner_day.setAdapter(dayAdapter);

                                signup_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
                        DuplicateCheck duplicatecheck = new DuplicateCheck("http://"+curip+"/SSAFYProject/customerinformation_duplicatecheck.php");
                        String checkresult = duplicatecheck.duplicatecheck(curid);

                        JSONObject jsonobj = new JSONObject(checkresult);
                        String myid = jsonobj.getJSONArray("result").getJSONObject(0).getString("id");

                        if(curid.equals(myid)) {
                            Toast.makeText(getApplication(), getString(R.string.duplicate_id), Toast.LENGTH_SHORT).show();
                            Alert alert = new Alert(SignUpActivity.this, "Error", getString(R.string.duplicate_id));
                            alert.alert();
                        }
                        else {
                            Toast.makeText(getApplication(),getString(R.string.error_signin),Toast.LENGTH_SHORT).show();
                            Alert alert = new Alert(SignUpActivity.this, "Error", getString(R.string.error_signin));
                            alert.alert();
                        }
                    }
                }
                catch (MalformedURLException | JSONException e) {
                    e.printStackTrace();
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(SignUpActivity.this, "Error", getString(R.string.check_birth));
                    alert.alert();
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
    }
}