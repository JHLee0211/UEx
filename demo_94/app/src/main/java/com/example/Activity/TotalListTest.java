package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

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
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output();
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("totallisttest", e.getMessage());
            return null;
        }
    }
}
