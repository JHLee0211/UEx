package com.example.Fragment;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Activity.MainActivity;
import com.example.dao.Exam.SearchTest;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class DetailFragment extends Fragment {
    public static TextView regist_start, regist_end, test_day, result_day, test_desc;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container, false);

        TextView regist_start = view.findViewById(R.id.regist_start);
        TextView regist_end = view.findViewById(R.id.regist_end);
        TextView test_day = view.findViewById(R.id.test_day);
        TextView result_day = view.findViewById(R.id.result_day);
        TextView test_desc = view.findViewById(R.id.test_desc);

        try {
            SearchTest searchtest = new SearchTest("http://"+MainActivity.curip+"/SSAFYProject/short_examination_search_accuracy.php");
            String jsonString = searchtest.searchtest1(MainActivity.cur_name);

            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            for(int a=0; a<result.length(); a++) {
                JSONObject c = result.getJSONObject(a);
                String jmcd = c.getString("jmcd");

                searchtest.setUrl("http://"+MainActivity.curip+"/SSAFYProject/examinformation_search.php");
                jsonString = searchtest.searchtest2(jmcd);

                jsonobj = new JSONObject(jsonString);
                JSONArray sub_result = jsonobj.getJSONArray("result");

                for(int i=0; i<sub_result.length(); i++) {
                    c = sub_result.getJSONObject(i);
                    regist_start.setText(c.getString("w_recept_start"));
                    regist_end.setText(c.getString("w_recept_end"));
                    String examstart = c.getString("w_exam_start");
                    String examend = c.getString("w_exam_end");
                    if(examstart.equals(examend)) {
                        test_day.setText(examstart);
                    } else {
                        test_day.setText(examstart + " ~ " + examend);
                    }
                    result_day.setText(c.getString("w_presentation"));
                    test_desc.setText(c.getString("etc"));
                }

                searchtest.setUrl("http://"+ MainActivity.curip+"/SSAFYProject/examinformation_sub_search.php");
                jsonString = searchtest.searchtest3(jmcd);

                jsonobj = new JSONObject(jsonString);
                sub_result = jsonobj.getJSONArray("result");

                c = sub_result.getJSONObject(0);
                test_desc.setText(test_desc.getText() + "\n" + c.getString("caution"));
                test_desc.setText(test_desc.getText() + "\n" + c.getString("price"));
            }
        }
        catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Drawable alpha = (view.findViewById(R.id.textView)).getBackground();
        alpha.setAlpha(170);
        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
