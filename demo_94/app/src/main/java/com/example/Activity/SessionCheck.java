package com.example.Activity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString = null;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }

            conn.disconnect();

            jsonString = sb.toString().trim();
            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            JSONObject c = result.getJSONObject(0);
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
