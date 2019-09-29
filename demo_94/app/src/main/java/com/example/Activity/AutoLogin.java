package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

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
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("autologin", e.getMessage());
            return null;
        }
    }
}
