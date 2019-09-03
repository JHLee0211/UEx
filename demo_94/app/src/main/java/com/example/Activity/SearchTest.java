package com.example.Activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchTest {
    private URL url;
    private BufferedReader br;
    public SearchTest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String searchtest1(final String name) {
        try {
            String postData = "name=" + name;
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

            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("searchtest1", e.getMessage());
            return null;
        }
    }

    public String searchtest2(final String jmNm) {
        try {
            String postData = "jmNm=" + jmNm;
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
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String jsonString;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }

            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("searchtest2", e.getMessage());
            return null;
        }
    }

    public String searchtest3(final String jmNm) {
        try {
            String postData = "jmNm=" + jmNm;
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

            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("searchtest3", e.getMessage());
            return null;
        }
    }

    public void setUrl(String url) throws  MalformedURLException {
        this.url = new URL(url);
    }
}
