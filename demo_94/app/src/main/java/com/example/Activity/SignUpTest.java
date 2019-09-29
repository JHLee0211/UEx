package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpTest {
    private URL url;

    public SignUpTest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String signuptest(final String id, final String password, final String name, final String birth, final String sex) {
        try {
            String postData = "id=" + id + "&" + "password=" + password + "&" + "name=" + name + "&" + "birth=" + birth + "&" + "sex=" + sex;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection connect = new PHPConntection(conn);
            connect.output(postData);
            String result = connect.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("signuptest", e.getMessage());
            return null;
        }
    }
}