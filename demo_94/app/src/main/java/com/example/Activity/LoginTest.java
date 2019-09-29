package com.example.Activity;

import android.util.Log;

import com.example.dao.PHPConntection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LoginTest {
    private URL url;
    private BufferedReader br;

    public LoginTest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String logintest(final String id, final String password) {
        try {
            String postData = "id=" + id + "&" + "password=" + password;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            PHPConntection conntection = new PHPConntection(conn);
            conntection.output(postData);

            Map<String, List<String>> header = conn.getHeaderFields();
            if (header.containsKey("Set-Cookie")) {
                List<String> cookie = header.get("Set-Cookie");
                for (int i = 0; i < cookie.size(); i++) {
                    MainActivity.cur_cookies = cookie.get(i);
                }
            }

            String result = conntection.input();
            conn.disconnect();
            return result;

            //CookieManager.getInstance().setCookie("http://70.12.227.10/SSAFYProject/test.php", m_cookies);
            //int responseCode = conn.getResponseCode();
        }
        catch (Exception e) {
            Log.i("logintest", e.getMessage());
            return null;
        }
    }
}
