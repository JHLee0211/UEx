package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.LoginActivity;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText id, password;
    private Button btn_send, btn_result, btn_login;
    private String curip = "192.168.100.24";

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_PASSWORD = "password";

    String myJSON;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkUtil.setNetworkPolicy();

        id = (EditText)findViewById(R.id.main_edit_id);
        password = (EditText)findViewById(R.id.main_edit_password);
        btn_send = (Button)findViewById(R.id.main_button_saveButton);
        btn_result = (Button)findViewById(R.id.main_button_outputButton);
        btn_login = (Button)findViewById(R.id.main_button_loginButton);

        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    PHPRequest request = new PHPRequest("http://"+curip+"/SSAFYProject/customerinformation_insert.php");
                    String result = request.PhPtest(String.valueOf(id.getText()),String.valueOf(password.getText()));
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_result.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list = (ListView)findViewById(R.id.main_listview_outputView);
                personList = new ArrayList<HashMap<String, String>>();
                getData("http://"+curip+"/SSAFYProject/customerinformation_search.php");
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("message", getApplicationContext().toString());
                startActivity(intent);
            }
        });
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String password = c.getString(TAG_PASSWORD);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);
                persons.put(TAG_PASSWORD, password);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, personList, R.layout.list_item, new String[]{TAG_ID, TAG_PASSWORD}, new int[]{R.id.item_text_id, R.id.item_text_password});
            list.setAdapter((adapter));
        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String uri = strings[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while((json = bufferedReader.readLine()) != null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();
                } catch(Exception e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                myJSON = s;
                showList();
            }
        }

        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
