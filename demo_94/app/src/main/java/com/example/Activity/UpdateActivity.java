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

import com.example.dao.Alert;
import com.example.demo_94.R;
import com.example.dto.Customer;

import org.json.JSONArray;
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

        update_text_id = (TextView)findViewById(R.id.update_text_id);
        update_edit_password = (EditText)findViewById(R.id.update_edit_password);
        update_edit_name = (EditText)findViewById(R.id.update_edit_name);
        update_spinner_sex = (Spinner)findViewById(R.id.update_spinner_sex);
        update_spinner_year = (Spinner)findViewById(R.id.update_spinner_year);
        update_spinner_month = (Spinner)findViewById(R.id.update_spinner_month);
        update_spinner_day = (Spinner)findViewById(R.id.update_spinner_day);
        update_btn_update = (Button)findViewById(R.id.update_btn_update);

        Customer customertemp = null;
        try {
            UpdateSearch updatesearch = new UpdateSearch("http://"+curip+"/SSAFYProject/customerinformation_update_search.php");
            String jsonString = updatesearch.updatesearch(MainActivity.cur_id);

            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            JSONObject c = result.getJSONObject(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            customertemp = new Customer(c.getString("id"), c.getString("password"), c.getString("name"), format.parse(c.getString("birth")), c.getString("sex"));
        } catch (MalformedURLException | JSONException | ParseException e) {
            e.printStackTrace();
        }

        final Customer customer = customertemp;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String str[] = format.format(customer.getBirth()).split("-");

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        update_spinner_year.setAdapter(yearAdapter);

        for(int i=0; i<years.length; i++) {
            if(years[i].equals(str[0])) {
                update_spinner_year.setSelection(i);
                break;
            }
        }

        update_spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    update_spinner_month.setAdapter(monthAdapter);

                    for(int i=0; i<months.length; i++) {
                        if(months[i].equals(str[1])) {
                            update_spinner_month.setSelection(i);
                            break;
                        }
                    }

                    update_spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                update_spinner_day.setAdapter(dayAdapter);

                                for(int i=0; i<days.length; i++) {
                                    if(days[i].equals(str[2])) {
                                        update_spinner_day.setSelection(i);
                                        break;
                                    }
                                }

                                update_spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        update_spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        update_text_id.setText(customer.getId());
        update_edit_password.setText(customer.getPassword());
        update_edit_name.setText(customer.getName());

        update_btn_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    UpdateTest updatetest = new UpdateTest("http://"+curip+"/SSAFYProject/customerinformation_update.php");
                    String curid = String.valueOf(update_text_id.getText());
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
                    Alert alert = new Alert(UpdateActivity.this, "Error", getString(R.string.check_birth));
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