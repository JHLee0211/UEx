package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TotalListActivity extends AppCompatActivity {
    private Button totallist_btn_result;
    private String curip = new getIP().getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_list);

        totallist_btn_result = (Button)findViewById(R.id.totallist_btn_result);

        totallist_btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView list = (ListView)findViewById(R.id.totallist_listview_outputView);
                ArrayList<HashMap<String, String>> personList = new ArrayList<HashMap<String, String>>();

                try {
                    TotalListTest totallisttest = new TotalListTest("http://"+curip+"/SSAFYProject/customerinformation_search.php");
                    String jsonString = totallisttest.totallisttest();

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray peoples = jsonObj.getJSONArray("result");

                    for(int i=0; i<peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String id = c.getString("id");
                        String password = c.getString("password");

                        HashMap<String, String> persons = new HashMap<String, String>();

                        persons.put("id", id);
                        persons.put("password", password);

                        personList.add(persons);
                    }

                    ListAdapter adapter = new SimpleAdapter(TotalListActivity.this, personList, R.layout.list_item, new String[]{"id", "password"}, new int[]{R.id.item_text_id, R.id.item_text_password});
                    list.setAdapter((adapter));
                }

                catch (MalformedURLException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}