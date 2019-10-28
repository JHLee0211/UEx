package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Fragment.basicFragment;
import com.example.dao.Connection.getIP;
import com.example.dao.Exam.SearchTest;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private Button search_btn_search;
    private static EditText search_edit_search;
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

        search_btn_search.setOnClickListener(new View.OnClickListener() { // 클릭했을 때 실행
            @Override
            public void onClick(View view) {
                try {
                    SearchTest searchtest = new SearchTest("http://"+MainActivity.curip+"/SSAFYProject/short_examination_search.php");
                    String jsonString = searchtest.searchtest1(search_edit_search.getText().toString());

                    JSONObject jsonobj = new JSONObject(jsonString);
                    JSONArray result = jsonobj.getJSONArray("result");

                    ArrayList<HashMap<String, String>> examList = new ArrayList<HashMap<String, String>>();

                    if(result.length() == 0) {
                        examList.clear();
                        HashMap<String, String> exams = new HashMap<String, String>();
                        exams.put("round", "미안해요..");
                        exams.put("data", "해당 자격증이 존재하지 않습니다.");
                        examList.add(exams);
                    }

                    else {
                        examList.clear();

                        for(int a=0; a<result.length(); a++) {
                            JSONObject c = result.getJSONObject(a);
                            String jmcd = c.getString("jmcd");

                            searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_search.php");
                            jsonString = searchtest.searchtest2(jmcd);

                            jsonobj = new JSONObject(jsonString);
                            JSONArray sub_result = jsonobj.getJSONArray("result");

                            for(int i=0; i<sub_result.length(); i++) {
                                c = sub_result.getJSONObject(i);
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

                            searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_sub_search.php");
                            jsonString = searchtest.searchtest3(jmcd);

                            jsonobj = new JSONObject(jsonString);
                            sub_result = jsonobj.getJSONArray("result");

                            c = sub_result.getJSONObject(0);
                            String title = "세부사항";
                            String data = "";
                            data = data + c.getString("caution") + "\n\n";
                            data = data + c.getString("price");

                            HashMap<String, String> exams = new HashMap<String, String>();
                            exams.put("round", title);
                            exams.put("data", data);

                            examList.add(exams);
                        }
                    }

                    ListAdapter adapter = new SimpleAdapter(SearchActivity.this, examList, R.layout.list_item2, new String[]{"round", "data"}, new int[]{R.id.item2_text_round, R.id.item2_text_data});
                    search_listview_search.setAdapter((adapter));
                }
                catch (MalformedURLException | JSONException e) {
                    e.printStackTrace();
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        search_edit_search.setOnKeyListener(new View.OnKeyListener() { // 엔터눌렀을 때 실행
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        SearchTest searchtest = new SearchTest("http://"+MainActivity.curip+"/SSAFYProject/short_examination_search.php");
                        String jsonString = searchtest.searchtest1(search_edit_search.getText().toString());

                        JSONObject jsonobj = new JSONObject(jsonString);
                        JSONArray result = jsonobj.getJSONArray("result");

                        ArrayList<HashMap<String, String>> examList = new ArrayList<HashMap<String, String>>();

                        if(result.length() == 0) {
                            examList.clear();
                            HashMap<String, String> exams = new HashMap<String, String>();
                            exams.put("round", "미안해요..");
                            exams.put("data", "해당 자격증이 존재하지 않습니다.");
                            examList.add(exams);
                        }

                        else {
                            examList.clear();

                            for(int a=0; a<result.length(); a++) {
                                JSONObject c = result.getJSONObject(a);
                                String jmcd = c.getString("jmcd");

                                searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_search.php");
                                jsonString = searchtest.searchtest2(jmcd);

                                jsonobj = new JSONObject(jsonString);
                                JSONArray sub_result = jsonobj.getJSONArray("result");

                                for(int i=0; i<sub_result.length(); i++) {
                                    c = sub_result.getJSONObject(i);
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

                                searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_sub_search.php");
                                jsonString = searchtest.searchtest3(jmcd);

                                jsonobj = new JSONObject(jsonString);
                                sub_result = jsonobj.getJSONArray("result");

                                c = sub_result.getJSONObject(0);
                                String title = "세부사항";
                                String data = "";
                                data = data + c.getString("caution") + "\n\n";
                                data = data + c.getString("price");

                                HashMap<String, String> exams = new HashMap<String, String>();
                                exams.put("round", title);
                                exams.put("data", data);

                                examList.add(exams);
                            }
                        }

                        ListAdapter adapter = new SimpleAdapter(SearchActivity.this, examList, R.layout.list_item2, new String[]{"round", "data"}, new int[]{R.id.item2_text_round, R.id.item2_text_data});
                        search_listview_search.setAdapter((adapter));
                    }
                    catch (MalformedURLException | JSONException e) {
                        e.printStackTrace();
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        search_edit_search.setText(basicFragment.mainfrag_text_search.getText().toString()); // 처음 접근했을 때 실행
        try {
            SearchTest searchtest = new SearchTest("http://"+MainActivity.curip+"/SSAFYProject/short_examination_search.php");
            String jsonString = searchtest.searchtest1(search_edit_search.getText().toString());

            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            ArrayList<HashMap<String, String>> examList = new ArrayList<HashMap<String, String>>();

            if(result.length() == 0) {
                examList.clear();
                HashMap<String, String> exams = new HashMap<String, String>();
                exams.put("round", "미안해요..");
                exams.put("data", "해당 자격증이 존재하지 않습니다.");
                examList.add(exams);
            }

            else {
                examList.clear();

                for(int a=0; a<result.length(); a++) {
                    JSONObject c = result.getJSONObject(a);
                    String jmcd = c.getString("jmcd");

                    searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_search.php");
                    jsonString = searchtest.searchtest2(jmcd);

                    jsonobj = new JSONObject(jsonString);
                    JSONArray sub_result = jsonobj.getJSONArray("result");

                    for(int i=0; i<sub_result.length(); i++) {
                        c = sub_result.getJSONObject(i);
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

                    searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_sub_search.php");
                    jsonString = searchtest.searchtest3(jmcd);

                    jsonobj = new JSONObject(jsonString);
                    sub_result = jsonobj.getJSONArray("result");

                    c = sub_result.getJSONObject(0);
                    String title = "세부사항";
                    String data = "";
                    data = data + c.getString("caution") + "\n\n";
                    data = data + c.getString("price");

                    HashMap<String, String> exams = new HashMap<String, String>();
                    exams.put("round", title);
                    exams.put("data", data);

                    examList.add(exams);
                }
            }

            ListAdapter adapter = new SimpleAdapter(SearchActivity.this, examList, R.layout.list_item2, new String[]{"round", "data"}, new int[]{R.id.item2_text_round, R.id.item2_text_data});
            search_listview_search.setAdapter((adapter));
        }
        catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
