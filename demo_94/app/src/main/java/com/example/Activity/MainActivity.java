package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.util.Util;
import com.example.Fragment.DetailFragment;
import com.example.Fragment.FragmentAdapter;
import com.example.Fragment.myPageFragment;
import com.example.Fragment.basicFragment;
import com.example.calendar.ScheduleFragment;
import com.example.demo_94.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private Fragment mainfragment = null;
    private Stack<Fragment> MAfragment = new Stack<>();
    private Stack<Fragment> SCfragment = new Stack<>();
    private Stack<Fragment> TAfragment = new Stack<>();
    private Stack<Fragment> MPfragment = new Stack<>();
    private String maintitle = "";
    private int menu = -1;//1~4까지. 현재 선택 fragment 구별
    private static FragmentAdapter fragmentAdapter;

    public static FragmentAdapter getFragmentAdapter(){
        return fragmentAdapter;
    }
    public static void setFragmentAdapter(FragmentAdapter fm){
        fragmentAdapter = fm;
        fragmentAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MAfragment.push(new basicFragment());
        SCfragment.push(new ScheduleFragment());
        TAfragment.push(new TableActivity());
        MPfragment.push(new myPageFragment());

        mainfragment = MAfragment.peek();
        maintitle = getString(R.string.app_name);
        menu = 1;
        ChangeFragmentMain(0);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(this);
    }

    public void ChangeFragmentMain(Fragment newfragment) {
        ChangeFragmentMain(0, newfragment);
    }

    public void ChangeFragmentMain(int id) {
        ChangeFragmentMain(id, null);
    }

    public void ChangeFragmentMain(int id, Fragment newfragment) {
        if (id != 0) {
            Bundle args = new Bundle();
            args.putInt("id", id);
            switch (id) {
                case R.id.interest_1:
                case R.id.interest_2:
                case R.id.interest_3:
                case R.id.hottest_1:
                case R.id.hottest_2:
                case R.id.hottest_3:
                    Fragment inter = new DetailFragment();
                    args.putInt("id", id);
                    inter.setArguments(args);
                    MAfragment.push(inter);
                    mainfragment = MAfragment.peek();
                    break;
            }
        }
        if (newfragment != null) {
            if (newfragment instanceof ScheduleFragment) {
                SCfragment.push(newfragment);
                mainfragment = SCfragment.peek();
            }else if(newfragment instanceof basicFragment){
                MAfragment.push(newfragment);
                mainfragment=MAfragment.peek();
            }else if(newfragment instanceof TableActivity){
                TAfragment.push(newfragment);
                mainfragment=TAfragment.peek();
            }else if(newfragment instanceof myPageFragment){
                MPfragment.push(newfragment);
                mainfragment=MPfragment.peek();
            }
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mainfragment = MAfragment.peek();
                menu = 1;
                break;
            case 1:
                mainfragment = SCfragment.peek();
                menu = 2;
                break;
            case 2:
                mainfragment = TAfragment.peek();
                menu = 3;
                break;
            case 3:
                mainfragment = MPfragment.peek();
                menu = 4;
                break;
        }
        ChangeFragmentMain(0);
    }

    private void Logout(final Activity activity) {    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onBackPressed() {
        boolean realback  = false;
        switch (menu) {
            case 1:
                if (MAfragment.size() > 1) {
                    MAfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = MAfragment.peek();
                break;
            case 2:
                if (SCfragment.size() > 1) {
                    SCfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = SCfragment.peek();
                break;
            case 3:
                if (TAfragment.size() > 1) {
                    TAfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = TAfragment.peek();
                break;
            case 4:
                if (MPfragment.size() > 1) {
                    MPfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = MPfragment.peek();
                break;
        }
        if (realback) {
            ActivityCompat.finishAffinity(this);
        } else {
            ChangeFragmentMain(0);
        }
    }
}
