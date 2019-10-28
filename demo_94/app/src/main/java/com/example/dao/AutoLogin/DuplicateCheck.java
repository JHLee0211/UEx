package com.example.dao.AutoLogin;

import android.util.Log;

import com.example.dao.Connection.PHPConntection;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DuplicateCheck {
    private URL url;
    private BufferedReader br;
    public DuplicateCheck(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String duplicatecheck(final String id) {
        try {
            String postData = "id=" + id;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("duplicatecheck", e.getMessage());
            return null;
        }
    }
}
