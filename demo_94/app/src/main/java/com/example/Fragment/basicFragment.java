package com.example.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.Activity.MainActivity;
import com.example.demo_94.R;

import java.util.ArrayList;

public class basicFragment extends Fragment implements View.OnClickListener {

    ArrayList<Integer> listImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainfrag, container, false);

        view.findViewById(R.id.ht_f4).setOnClickListener(this);
        view.findViewById(R.id.ht_f5).setOnClickListener(this);
        view.findViewById(R.id.ht_f6).setOnClickListener(this);
        view.findViewById(R.id.ht_y1).setOnClickListener(this);
        view.findViewById(R.id.ht_y2).setOnClickListener(this);
        view.findViewById(R.id.ht_y3).setOnClickListener(this);
        viewP(view);
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

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);


        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();
    }




}
