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

public class UpdateTest {
    private URL url;

    public UpdateTest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
    public String updatetest(final Customer customer) {
        try {
            String postData = "id=" + customer.getId() + "&" + "password=" + customer.getPassword() + "&" + "name=" + customer.getName() + "&" + "birth=" + customer.getBirth() + "&" + "sex=" + customer.getSex();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);
            String result = conntection.input();
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            Log.i("updatetest", e.getMessage());
            return null;
        }
    }
}
