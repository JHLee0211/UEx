package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;
import com.example.dto.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateSearch_Sub {
    private URL url;
    private BufferedReader br;

    public UpdateSearch_Sub(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String updatesearch_sub(final String id) {
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
            Log.i("updatesearch_sub", e.getMessage());
            return null;
        }
    }
}
