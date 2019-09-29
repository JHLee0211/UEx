package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button main_btn_output, main_btn_login, main_btn_signup, main_btn_search, main_btn_logout, main_btn_update, main_btn_test, main_btn_withdraw;
    private TextView main_text_hello;
    private String curip = new getIP().getInstance();
    public static boolean cur_session = false;
    public static String cur_cookies = "";
    public static String cur_id = "";
    public static String phone_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.setNetworkPolicy();

        main_btn_output = (Button) findViewById(R.id.main_btn_output);
        main_btn_login = (Button) findViewById(R.id.main_btn_login);
        main_btn_logout = (Button) findViewById(R.id.main_btn_logout);
        main_btn_signup = (Button) findViewById(R.id.main_btn_signup);
        main_btn_search = (Button) findViewById(R.id.main_btn_search);
        main_btn_update = (Button) findViewById(R.id.main_btn_update);
        main_text_hello = (TextView) findViewById(R.id.main_text_hello);
        main_btn_test = (Button)findViewById(R.id.main_btn_test);
        main_btn_withdraw=(Button)findViewById(R.id.main_btn_withdraw);

        main_btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_output.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TotalListActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Logout().logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });
        
        main_btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });

        main_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SessionCheck().sessioncheck()) {
                    Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                    intent.putExtra("message", getApplicationContext().toString());
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage(getString(R.string.error_loginservice));
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.show();
                }
            }
        });

        main_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SessionCheck().sessioncheck()) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("message", getApplicationContext().toString());
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Error");
                    alert.setMessage(getString(R.string.error_loginservice));
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.show();
                }
            }
        });

        if(cur_session == false) {
            try {
                phone_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                URL url = new URL("http://" + curip + "/SSAFYProject/customerinformation_autologinsearch.php");

                String postData = "phone_id=" + phone_id + "&" + "cookies=" + cur_cookies + "&" + "id=" + cur_id;

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String jsonString = null;
                while ((jsonString = br.readLine()) != null) {
                    sb.append(jsonString + "\n");
                }

                conn.disconnect();

                jsonString = sb.toString().trim();

                if (!jsonString.equals("-1")) {
                    JSONObject jsonobj = new JSONObject(jsonString);
                    JSONArray result = jsonobj.getJSONArray("result");

                    JSONObject c = result.getJSONObject(0);

                    cur_cookies = c.getString("cookies");
                    cur_id = c.getString("id");
                    cur_session = true;
                }
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(new SessionCheck().sessioncheck()) {
            main_text_hello.setText(cur_id + "님 반갑습니다!");
            main_btn_login.setVisibility(View.GONE);
            main_btn_logout.setVisibility(View.VISIBLE);
            main_btn_update.setVisibility(View.VISIBLE);
        } else {
            main_text_hello.setText("");
            main_btn_login.setVisibility(View.VISIBLE);
            main_btn_logout.setVisibility(View.GONE);
            main_btn_update.setVisibility(View.GONE);
        }
    }
}
