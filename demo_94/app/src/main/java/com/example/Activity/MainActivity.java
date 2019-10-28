package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.example.Fragment.DetailFragment;
import com.example.Fragment.FragmentAdapter;
import com.example.Fragment.myPageFragment;
import com.example.Fragment.basicFragment;
import com.example.Util.NetworkUtil;
import com.example.calendar.ScheduleFragment;
import com.example.dao.Connection.PHPConntection;
import com.example.dao.Connection.getIP;
import com.example.dao.User.UpdateSearch_Sub;
import com.example.demo_94.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static String curip = new getIP().getInstance();
    public static boolean cur_session = false;
    public static String cur_cookies = "";
    public static String cur_id = "";
    public static String phone_id = "";
    public static String cur_name = "";

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
        NetworkUtil.setNetworkPolicy();

        if(cur_session == false) {
            try {
                phone_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                URL url = new URL("http://" + curip + "/SSAFYProject/customerinformation_autologinsearch.php");
                String postData = "phone_id=" + phone_id + "&" + "cookies=" + cur_cookies + "&" + "id=" + cur_id;
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                PHPConntection connection = new PHPConntection(conn);
                connection.output(postData);
                String result = connection.input();
                conn.disconnect();
                System.out.println(result +"찾기");
                if (!("-1").equals(result) && result != null) {
                    JSONObject jsonobj = new JSONObject(result);
                    JSONArray results = jsonobj.getJSONArray("result");
                    JSONObject c = results.getJSONObject(0);

                    cur_cookies = c.getString("cookies");
                    cur_id = c.getString("id");
                    cur_session = true;
                }
            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!cur_session) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
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
            String interStr[] = null;
            try {
                UpdateSearch_Sub updatesearch_sub = new UpdateSearch_Sub("http://"+ MainActivity.curip+"/SSAFYProject/customerinformation_update_search_sub.php");
                String jsonString = updatesearch_sub.updatesearch_sub(MainActivity.cur_id);

                JSONObject jsonobj = new JSONObject(jsonString);
                JSONArray result = jsonobj.getJSONArray("result");

                JSONObject c = result.getJSONObject(0);
                interStr = new String[3];
                interStr[0] = c.getString("inter1");
                interStr[1] = c.getString("inter2");
                interStr[2] = c.getString("inter3");
            } catch (MalformedURLException | JSONException e) {
                e.printStackTrace();
            }

            switch (id) {
                case R.id.mainfrag_imgbtn_inter1:
                    cur_name = interStr[0];
                    break;
                case R.id.mainfrag_imgbtn_inter2:
                    cur_name = interStr[1];
                    break;
                case R.id.mainfrag_imgbtn_inter3:
                    cur_name = interStr[2];
                    break;
                case R.id.hottest_1:
                    break;
                case R.id.hottest_2:
                    break;
                case R.id.hottest_3:
                    break;
            }

            Fragment inter = new DetailFragment();
            args.putInt("id", id);
            inter.setArguments(args);
            MAfragment.push(inter);
            mainfragment = MAfragment.peek();
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
