package com.example.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private Button search_btn_search;
    private EditText search_edit_search;
    private ListView search_listview_search;
    private TextView search_text_test;
    private String curip = new getIP().getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_btn_search = (Button)findViewById(R.id.search_btn_search);
        search_edit_search = (EditText)findViewById(R.id.search_edit_search);
        search_listview_search = (ListView)findViewById(R.id.search_listview_search);
        search_text_test = (TextView)findViewById(R.id.search_text_test);

        search_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SearchTest searchtest = new SearchTest("http://"+curip+"/SSAFYProject/short_examination_search.php");
                    String jsonString = searchtest.searchtest1(search_edit_search.getText().toString());

                    JSONObject jsonobj = new JSONObject(jsonString);
                    JSONArray result = jsonobj.getJSONArray("result");

                    if(result.length() == 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SearchActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage(getString(R.string.notexist_jmnm));
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alert.show();
                    }

                    else {
                        JSONObject c = result.getJSONObject(0);
                        String jmNm = c.getString("jmcd");

                        ArrayList<HashMap<String, String>> examList = new ArrayList<HashMap<String, String>>();
                        searchtest.setUrl("http://"+curip+"/SSAFYProject/examinformation_search.php");
                        jsonString = searchtest.searchtest2(jmNm);

                        jsonobj = new JSONObject(jsonString);
                        result = jsonobj.getJSONArray("result");

                        for(int i=0; i<result.length(); i++) {
                            c = result.getJSONObject(i);
                            String round = c.getString("round");
                            String data = "";
                            data = data + "필기시험접수 : " + c.getString("w_recept_start") + " ~ " + c.getString("w_recept_end") + "\n";
                            data = data + "필기시험 : " + c.getString("w_exam_start") + " ~ " + c.getString("w_exam_end") + "\n";
                            data = data + "필기발표 : " + c.getString("w_presentation") + "\n";
                            data = data + "실기시험접수 : " + c.getString("p_recept_start") + " ~ " + c.getString("p_recept_end") + "\n";
                            data = data + "실기시험 : " + c.getString("p_exam_start") + " ~ " + c.getString("p_exam_end") + "\n";
                            data = data + "실기발표 : " + c.getString("p_presentation") + "\n";
                            data = data + "비고 : " + c.getString("etc");

                            HashMap<String, String> exams = new HashMap<String, String>();
                            exams.put("round", round);
                            exams.put("data", data);

                            examList.add(exams);
                        }

                        ListAdapter adapter = new SimpleAdapter(SearchActivity.this, examList, R.layout.list_item2, new String[]{"round", "data"}, new int[]{R.id.item2_text_round, R.id.item2_text_data});
                        search_listview_search.setAdapter((adapter));
                    }
                }
                catch (MalformedURLException | JSONException e) {
                    e.printStackTrace();
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
