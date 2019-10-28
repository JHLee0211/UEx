package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.Activity.MainActivity;
import com.example.Activity.SearchActivity;
import com.example.dao.User.UpdateSearch_Sub;
import com.example.demo_94.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class basicFragment extends Fragment implements View.OnClickListener {
    private static ArrayList<Integer> listImage = new ArrayList<>();
    private static ViewPager viewPager;
    private static ImageFragment imageFragment;
    private static FragmentAdapter fragmentAdapter;
    public static TextView mainfrag_text_search;
    private TextView mainfrag_text_inter1, mainfrag_text_inter2, mainfrag_text_inter3;
    private ImageButton mainfrag_imgbtn_inter1, mainfrag_imgbtn_inter2, mainfrag_imgbtn_inter3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainfrag, container, false);
        mainfrag_text_inter1 = (TextView)view.findViewById(R.id.mainfrag_text_inter1);
        mainfrag_text_inter2 = (TextView)view.findViewById(R.id.mainfrag_text_inter2);
        mainfrag_text_inter3 = (TextView)view.findViewById(R.id.mainfrag_text_inter3);
        mainfrag_imgbtn_inter1 = (ImageButton)view.findViewById(R.id.mainfrag_imgbtn_inter1);
        mainfrag_imgbtn_inter2 = (ImageButton)view.findViewById(R.id.mainfrag_imgbtn_inter2);
        mainfrag_imgbtn_inter3 = (ImageButton)view.findViewById(R.id.mainfrag_imgbtn_inter3);

        mainfrag_imgbtn_inter1.setOnClickListener(this);
        mainfrag_imgbtn_inter2.setOnClickListener(this);
        mainfrag_imgbtn_inter3.setOnClickListener(this);
        view.findViewById(R.id.hottest_1).setOnClickListener(this);
        view.findViewById(R.id.hottest_2).setOnClickListener(this);
        view.findViewById(R.id.hottest_3).setOnClickListener(this);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        fragmentAdapter = MainActivity.getFragmentAdapter();
        if(listImage.size()==0) viewP(view);
        else viewPager.setAdapter(MainActivity.getFragmentAdapter());

        UpdateSearch_Sub updatesearch_sub = null;
        try {
            updatesearch_sub = new UpdateSearch_Sub("http://"+ MainActivity.curip+"/SSAFYProject/customerinformation_update_search_sub.php");
            String jsonString = updatesearch_sub.updatesearch_sub(MainActivity.cur_id);

            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            JSONObject c = result.getJSONObject(0);
            String inter[] = new String[3];
            inter[0] = c.getString("inter1");
            inter[1] = c.getString("inter2");
            inter[2] = c.getString("inter3");

            mainfrag_text_inter1.setVisibility(View.VISIBLE);
            mainfrag_imgbtn_inter1.setVisibility(View.VISIBLE);
            mainfrag_text_inter2.setVisibility(View.VISIBLE);
            mainfrag_imgbtn_inter2.setVisibility(View.VISIBLE);
            mainfrag_text_inter3.setVisibility(View.VISIBLE);
            mainfrag_imgbtn_inter3.setVisibility(View.VISIBLE);

            if(inter[0] != null && !inter[0].equals("null")) {
                mainfrag_text_inter1.setText(inter[0]);
                if(inter[1] != null && !inter[1].equals("null")) {
                    mainfrag_text_inter2.setText(inter[1]);
                    if(inter[2] != null && !inter[2].equals("null")) {
                        mainfrag_text_inter3.setText(inter[2]);
                    } else {
                        mainfrag_text_inter3.setVisibility(View.GONE);
                    }
                } else {
                    mainfrag_text_inter2.setVisibility(View.GONE);
                    mainfrag_text_inter3.setVisibility(View.GONE);
                }
            } else {
                mainfrag_text_inter1.setVisibility(View.GONE);
                mainfrag_text_inter2.setVisibility(View.GONE);
                mainfrag_text_inter3.setVisibility(View.GONE);
                mainfrag_imgbtn_inter1.setVisibility(View.GONE);
                mainfrag_imgbtn_inter2.setVisibility(View.GONE);
                mainfrag_imgbtn_inter3.setVisibility(View.GONE);
            }
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        mainfrag_text_search = (TextView)view.findViewById(R.id.mainfrag_text_search);
        mainfrag_text_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).ChangeFragmentMain(view.getId());
    }

    public void viewP(View view){
        // Fragment로 넘길 Image Resource
        listImage = new ArrayList<>();

        listImage.add(R.mipmap.national_exam);
        listImage.add(R.mipmap.toeic);
        listImage.add(R.mipmap.jungbo_gisa);
        listImage.add(R.mipmap.toeic_speaking);

        fragmentAdapter = new FragmentAdapter(getFragmentManager());
        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        MainActivity.setFragmentAdapter(fragmentAdapter);
        // ViewPager와  FragmentAdapter 연결
       viewPager.setAdapter(MainActivity.getFragmentAdapter());
    }
}
