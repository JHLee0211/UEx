package com.example.dao.User;

import android.util.Log;

import com.example.Activity.MainActivity;
import com.example.dao.Connection.getIP;
import com.example.dao.Connection.PHPConntection;

import java.net.HttpURLConnection;
import java.net.URL;

public class Logout {
    private String curip = new getIP().getInstance();

    public String logout() {
        try {
            URL url = new URL("http://"+curip+"/SSAFYProject/customerinformation_logout.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if (MainActivity.cur_session) {
                conn.setRequestProperty("Cookie", MainActivity.cur_cookies);
            }

            PHPConntection conntection = new PHPConntection(conn);
            conntection.output();

            String postData = "phone_id=" + MainActivity.phone_id;
            url = new URL("http://"+curip+"/SSAFYProject/customerinformation_autologinsignout.php");
            conn = (HttpURLConnection)url.openConnection();
            conntection = new PHPConntection(conn);
            conntection.output(postData);
            // 이걸왜 해야되지..?
            String result = conntection.input();
            // 여기까지
            conn.disconnect();

            MainActivity.cur_session = false;
            MainActivity.cur_cookies = "";
            MainActivity.cur_id = "";

            return result;
        }
        catch (Exception e) {
            Log.i("logout", e.getMessage());
            return null;
        }
    }
}
