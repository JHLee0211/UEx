package com.example.Activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TotalListTest {
    private URL url;
    private BufferedReader br;
    public TotalListTest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String totallisttest() {
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }

            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("totallisttest", e.getMessage());
            return null;
        }
    }
}
