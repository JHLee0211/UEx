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

import com.example.Util.NetworkUtil;
import com.example.dao.Alert;
import com.example.dao.Connection.PHPConntection;
import com.example.dao.Connection.getIP;
import com.example.dao.User.Logout;
import com.example.dao.User.SessionCheck;
import com.example.dao.User.WithDraw;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity { // 혹시 몰라서 냅둔거 안봐도 되는 파일
    private Button main_btn_output, main_btn_login, main_btn_signup, main_btn_search, main_btn_logout, main_btn_update, main_btn_withdraw, main_btn_test;
    private TextView main_text_hello;
    private String curip = new getIP().getInstance();
    public static boolean cur_session = false;
    public static String cur_cookies = "";
    public static String cur_id = "";
    public static String phone_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        NetworkUtil.setNetworkPolicy();

        main_btn_output = (Button) findViewById(R.id.main_btn_output);
        main_btn_login = (Button) findViewById(R.id.main_btn_login);
        main_btn_logout = (Button) findViewById(R.id.main_btn_logout);
        main_btn_signup = (Button) findViewById(R.id.main_btn_signup);
        main_btn_search = (Button) findViewById(R.id.main_btn_search);
        main_btn_update = (Button) findViewById(R.id.main_btn_update);
        main_btn_withdraw = (Button) findViewById(R.id.main_btn_withdraw);
        main_text_hello = (TextView) findViewById(R.id.main_text_hello);
        main_btn_test = (Button)findViewById(R.id.main_btn_test);

        main_btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
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
                    Alert alert = new Alert(MainActivity2.this, "Error", getString(R.string.error_loginservice));
                    alert.alert();
                }
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
                    Alert alert = new Alert(MainActivity2.this, "Error", getString(R.string.error_loginservice));
                    alert.alert();
                }
            }
        });

        main_btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    WithDraw withdraw = new WithDraw("http://" + curip + "/SSAFYProject/customerinformation_withdraw.php");
                    if (withdraw.withdraw(cur_id).equals("1")) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
                        alert.setTitle("Success");
                        alert.setMessage(getString(R.string.success_withdraw));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(intent);
                            }
                        });

                        alert.show();
                    } else {
                        Alert alert = new Alert(MainActivity2.this, "Error", getString(R.string.error_withdraw));
                        alert.alert();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        if(cur_session == false) {
            try {
                phone_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                URL url = new URL("http://" + curip + "/SSAFYProject/customerinformation_autologinsearch.php");
                String postData = "phone_id=" + phone_id + "&" + "cookies=" + cur_cookies + "&" + "id=" + cur_id;
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                PHPConntection connection = new PHPConntection(conn);
                connection.output(postData);
                String result = connection.input();
                conn.disconnect();
                if (!("-1").equals(result)) {
                    JSONObject jsonobj = new JSONObject(result);
                    JSONArray results = jsonobj.getJSONArray("result");
                    JSONObject c = results.getJSONObject(0);

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
            main_btn_withdraw.setVisibility(View.VISIBLE);
        } else {
            main_text_hello.setText("");
            main_btn_login.setVisibility(View.VISIBLE);
            main_btn_logout.setVisibility(View.GONE);
            main_btn_update.setVisibility(View.GONE);
            main_btn_withdraw.setVisibility(View.GONE);
        }
    }
}
