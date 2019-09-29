package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WithDraw {
    private URL url;

    public WithDraw(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String withdraw(String id) {
        try {
            String postData = "id=" + id;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection connect = new PHPConntection(conn);
            connect.output(postData);
            String result = connect.input();
            conn.disconnect();

            MainActivity.cur_cookies = "";
            MainActivity.cur_id = "";
            MainActivity.cur_session = false;

            return result;
        }
        catch (Exception e) {
            Log.i("withdraw", e.getMessage());
            return null;
        }
    }
}
