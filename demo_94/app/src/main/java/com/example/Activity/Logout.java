package com.example.Activity;

import android.util.Log;

import com.example.Activity.MainActivity;
import com.example.Activity.getIP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logout {
    private String curip = new getIP().getInstance();

    public void logout() {
        try {
            URL url = new URL("http://"+curip+"/SSAFYProject/customerinformation_logout.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (MainActivity.cur_session) {
                conn.setRequestProperty("Cookie", MainActivity.cur_cookies);
            }

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.disconnect();

            MainActivity.cur_session = false;
            MainActivity.cur_cookies = "";
            MainActivity.cur_id = "";
        }
        catch (Exception e) {
            Log.i("logout", e.getMessage());
            return;
        }
    }
}
