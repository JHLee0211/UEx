package com.example.dao.Exam;

import android.util.Log;

import com.example.dao.Connection.PHPConntection;

import java.io.BufferedReader;
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
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("searchtest1", e.getMessage());
            return null;
        }
    }

    public String searchtest2(final String jmcd) {
        try {
            String postData = "jmcd=" + jmcd;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("searchtest2", e.getMessage());
            return null;
        }
    }

    public String searchtest3(final String jmcd) {
        try {
            String postData = "jmcd=" + jmcd;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
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
