package com.example.Activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AutoLogin {
    private URL url;
    private BufferedReader br;

    public AutoLogin(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String autologinsignup() {
        try {
            String postData = "phone_id=" + MainActivity.phone_id + "&" + "cookies=" + MainActivity.cur_cookies + "&" + "id=" + MainActivity.cur_id;

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

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
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }

            //int responseCode = conn.getResponseCode();
            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("autologin", e.getMessage());
            return null;
        }
    }
}
