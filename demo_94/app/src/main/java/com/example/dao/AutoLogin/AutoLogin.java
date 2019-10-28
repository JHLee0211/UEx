package com.example.dao.AutoLogin;

import android.util.Log;

import com.example.Activity.MainActivity;
import com.example.dao.Connection.PHPConntection;

import java.io.BufferedReader;
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
            PHPConntection connection = new PHPConntection(conn);
            connection.output(postData);
            String result = connection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("autologin", e.getMessage());
            return null;
        }
    }
}
