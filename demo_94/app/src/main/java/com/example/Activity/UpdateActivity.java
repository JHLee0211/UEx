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
import com.example.dto.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    private EditText update_edit_password, update_edit_name;
    private Spinner update_spinner_sex, update_spinner_year, update_spinner_month, update_spinner_day;
    private TextView update_text_id;
    private Button update_btn_update;
    private String curip = new getIP().getInstance();
    private String sexes[] = {"성별을 선택하세요.", "man", "woman"};
    private String years[], months[], days[];
    private TextView update_text_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();

        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));

        update_text_id = (TextView)findViewById(R.id.update_text_id);
        update_edit_password = (EditText)findViewById(R.id.update_edit_password);
        update_edit_name = (EditText)findViewById(R.id.update_edit_name);
        update_spinner_sex = (Spinner)findViewById(R.id.update_spinner_sex);
        update_spinner_year = (Spinner)findViewById(R.id.update_spinner_year);
        update_spinner_month = (Spinner)findViewById(R.id.update_spinner_month);
        update_spinner_day = (Spinner)findViewById(R.id.update_spinner_day);
        update_btn_update = (Button)findViewById(R.id.update_btn_update);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        update_spinner_day.setAdapter(dayAdapter);

        update_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                update_spinner_day.setSelection(1);
            }
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        update_spinner_month.setAdapter(monthAdapter);

        update_spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                update_spinner_month.setSelection(1);
            }
        });

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        update_spinner_year.setAdapter(yearAdapter);

        update_spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                update_spinner_year.setSelection(1);
            }
        });

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexes);
        sexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        update_spinner_sex.setAdapter(sexAdapter);

        update_spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                update_spinner_sex.setSelection(1);
            }
        });

        update_text_id.setText(MainActivity.cur_id);

        update_btn_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    UpdateTest updatetest = new UpdateTest("http://"+curip+"/SSAFYProject/customerinformation_update.php");
                    String curid = String.valueOf(MainActivity.cur_id);
                    String curpassword = String.valueOf(update_edit_password.getText());
                    String curname = String.valueOf(update_edit_name.getText());
                    String date = update_spinner_year.getSelectedItem().toString() + "-" + update_spinner_month.getSelectedItem().toString() + "-" + update_spinner_day.getSelectedItem().toString();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date curbirth = format.parse(date);
                    String cursex = update_spinner_sex.getSelectedItem().toString();

                    Customer customer = new Customer(curid, curpassword, curname, curbirth, cursex);
                    String result = updatetest.updatetest(customer);
                    if(result.equals("1")) {
                        Toast.makeText(getApplication(), getString(R.string.success_update), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateActivity.this);
                        alert.setTitle("Success");
                        alert.setMessage(getString(R.string.success_update));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        alert.show();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage(getString(R.string.error_update));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        alert.show();
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateActivity.this);
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