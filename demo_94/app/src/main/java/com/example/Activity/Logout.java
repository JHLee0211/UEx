package com.example.Activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

            String postData = "phone_id=" + MainActivity.phone_id;

            url = new URL("http://"+curip+"/SSAFYProject/customerinformation_autologinsignout.php");
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            // 이걸왜 해야되지..?
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString = null;
            while ((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }
            // 여기까지

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
