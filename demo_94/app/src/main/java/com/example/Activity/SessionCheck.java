package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.PhantomReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class SessionCheck {
    private String curip = new getIP().getInstance();
    private BufferedReader br = null;

    public boolean sessioncheck() {
        try {
            URL url = new URL("http://"+curip+"/SSAFYProject/customerinformation_sessioncheck.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (MainActivity.cur_session) {
                conn.setRequestProperty("Cookie", MainActivity.cur_cookies);
            }

            PHPConntection conntection = new PHPConntection(conn);
            conntection.output();
            String result = conntection.input();
            conn.disconnect();

            JSONObject jsonobj = new JSONObject(result);
            JSONArray results = jsonobj.getJSONArray("result");

            JSONObject c = results.getJSONObject(0);
            String IsLogin = c.getString("IsLogin");

            if(IsLogin.equals("Y"))
                return true;
            else {
                MainActivity.cur_session = false;
                MainActivity.cur_id = "";
                MainActivity.cur_cookies = "";
                return false;
            }
        }
        catch (Exception e) {
            Log.i("sessioncheck", e.getMessage());
            return false;
        }
    }
}
