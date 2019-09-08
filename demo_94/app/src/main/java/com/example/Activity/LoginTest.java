package com.example.Activity;

import android.util.Log;
import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.IOException;
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

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            Map<String, List<String>> header = conn.getHeaderFields();
            if (header.containsKey("Set-Cookie")) {
                List<String> cookie = header.get("Set-Cookie");
                for (int i = 0; i < cookie.size(); i++) {
                    MainActivity.cur_cookies = cookie.get(i);
                }
            }

            //CookieManager.getInstance().setCookie("http://70.12.227.10/SSAFYProject/test.php", m_cookies);

            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String jsonString;
            while((jsonString = br.readLine()) != null) {
                sb.append(jsonString + "\n");
            }

            //int responseCode = conn.getResponseCode();
            conn.disconnect();

            return sb.toString().trim();
        }
        catch (Exception e) {
            Log.i("logintest", e.getMessage());
            return null;
        }
    }
}
