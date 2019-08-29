package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.example.Fragment.basicFragment;
import com.example.calendar.ScheduleFragment;
import com.example.demo_94.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Stack;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private Fragment mainfragment = null;
    private Stack<Fragment> MAfragment = new Stack<>();
    private Stack<Fragment> SCfragment = new Stack<>();
    private Stack<Fragment> DAfragment = new Stack<>();
    private Stack<Fragment> MPfragment = new Stack<>();
    private String maintitle = "";
    private int menu = -1;//1~4까지. 현재 선택 fragment 구별

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MAfragment.push(new basicFragment());
        SCfragment.push(new ScheduleFragment());

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
     /*   if (id != 0) {
            Bundle args = new Bundle();
            args.putInt("id", id);
            switch (id) {
                case R.id.ht_f4:
                case R.id.ht_f5:
                case R.id.ht_f6:
                    Fragment ht2 = new basicFragment();
                    args.putInt("id", id);
                    ht2.setArguments(args);
                    HTfragment.push(ht2);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.fitness_1_1:
                case R.string.fitness_1_2:
                case R.string.fitness_1_3:
                    Fragment ht3 = new basicFragment();
                    args.putInt("id", id);
                    ht3.setArguments(args);
                    HTfragment.push(ht3);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.my_info:
                    MPfragment.push(new basicFragment());
                    mainfragment = MPfragment.peek();
                    break;
                case R.string.my_trainee:
                    MPfragment.push(new basicFragment());
                    mainfragment = MPfragment.peek();
                    break;
                case R.string.my_calender:
                    MPfragment.push(new basicFragment());
                    mainfragment = MPfragment.peek();
                    break;

                case R.string.logout:
                    Logout(this);
                    break;
                case R.string.app_info:
                    showSimpleDialog();
                    break;
                case R.string.separate:
                    Util.DeleteUser(kakaoid + "");
                    Logout(this);
                    break;
            }
        }*/
        if (newfragment != null) {
            if (newfragment instanceof ScheduleFragment) {
                SCfragment.push(newfragment);
                mainfragment = SCfragment.peek();
            }else if(newfragment instanceof basicFragment){
                MAfragment.push(newfragment);
                mainfragment=MAfragment.peek();
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
                mainfragment = DAfragment.peek();
                menu = 3;
                break;
            case 3:
                mainfragment = MPfragment.peek();
                menu = 4;
                break;
        }
        ChangeFragmentMain(0);
    }

    private void Logout(final Activity activity) {
        /*
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Util.startLoginActivity(activity);
            }
        });*/
    }

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
                if (DAfragment.size() > 1) {
                    DAfragment.pop();
                } else {
                    realback = true;
                }
                mainfragment = DAfragment.peek();
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
