package com.example.dao.Connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PHPConntection {
    private HttpURLConnection conn = null;
    private BufferedReader br = null;
    public PHPConntection(HttpURLConnection conn) {
        this.conn = conn;
    }

    public void output(String postData) {
        try {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        }  catch (Exception e) {
            Log.i("output", e.getMessage());
            return;
        }
    }

    public void output() {
        try {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }  catch (Exception e) {
            Log.i("output", e.getMessage());
            return;
        }
    }

    public String input() {
        try {
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }
            return sb.toString().trim();
        } catch (Exception e) {
            Log.i("input", e.getMessage());
            return null;
        }
    }
}