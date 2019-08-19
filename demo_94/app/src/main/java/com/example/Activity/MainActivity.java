package com.example.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.view.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.Fragment.HomeTrainingFragment;
import com.example.demo_94.R;
import com.google.android.material.tabs.TabLayout;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import java.util.ArrayList;
import java.util.Stack;


        public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

            private Fragment mainfragment = null;
            private Stack<Fragment> HTfragment = new Stack<>();
            private Stack<Fragment> TMfragment = new Stack<>();
            private Stack<Fragment> DAfragment = new Stack<>();
            private Stack<Fragment> MPfragment = new Stack<>();
            private String maintitle = "";
            private int menu = -1;//1~4까지. 현재 선택 fragment 구별
            AutoScrollViewPager autoViewPager;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HTfragment.push(new HomeTrainingFragment());
        HTfragment.push(new HomeTrainingFragment());
        HTfragment.push(new HomeTrainingFragment());
        HTfragment.push(new HomeTrainingFragment());
//        TMfragment.push(new TrainerMatchFragment());
//        DAfragment.push(new DataAnalysisFragment());
//        MPfragment.push(new MyPageFragment());

        mainfragment = HTfragment.peek();
        maintitle = getString(R.string.app_name);
        menu = 1;
        ChangeFragmentMain(0);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(this);

        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("http://www.historyexam.go.kr/images/main/main.jpg");
        data.add("http://knnws.com/data/cheditor4/1904/3731752159_LYT3nfRx_ddwq.jpg");
        data.add("data:image/png;base64,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");
        data.add("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAbMAAAB0CAMAAAA4qSwNAAAAzFBMVEX///8AS42KJSkASYwANIMkWZUAP4cAN4QAOoUAPIYAQokARYoAR4sAQYgAPocARIqquc/N1eHa4eo0YpqCmbrw8vaAAABRdaTY3+n2+PoAMoKzwNSVp8Nwi7F4kbWLob/Ay9vm6/GHGh/G0N+EDRREbJ9dfamCAAloha6TpsPKqaqtu9EvX5h9lbigscry6uqveXvStrelZWcAKX4AIXyTPD+YSkzZwsOECBC8kpPo2tqqb3CaTlC1hIXAmJncx8iQMzaRNzsAF3mgWlyGmLH7AAATJElEQVR4nO1d6XabyBIWBrEIhPbNWKsNkhNLcewkTpxxkpm8/ztdJLaqphuqtUzmnsP3Y85Eht6qa69uarUKFSpUqFChQoUKFSpUqFChQoUKFSpUqFChQoUK/4/48OE1xIfJnx5HhXJMXt+9PX2+G6ZYfXn88fLhTw+rggCv775fDdd3q9UVwuru63D1/WNFt/8cXh5XwzuGWpBwX4d/vfuDknK5uN/0HNPQNMNV/Nmu8+eGAjDotOcbv67YetNWesF2t1j+a32/PA2/iumV8tvwUYbZ1PoRGPFaWkxtw2rYqnKAqjot3dT83aCw+xmxR+U2fSWAv5dNr7sLWoalN2zHCYekRsNyDTdo/wt0e31DBFtlyJNt+ESm2thQ5dHc5tq53WhuS8nD0bVREbfVbVqXWrbEVvarzd082eT6qqHbnFEpajiu3nvqIh2Hly/DVaq31sP1r9/fH9++ffv29vj05W64ZtlvNXwjNvxe502pBLrHtlI3uUtzgG30xsL+NVqPqp6+sTSynxvzgqnt6kZLLWrTKuXSE/Dx13oVq6vV07eXV0ZjTV4/vn1mxObd6pXU9JbHHGUwMQ08xS1am5DZtKmg+7FJ6xEw1MLNfrYWonlN5qZePKgSip+Gl6t1pKeu3grM+cnH78OvkGrDH5TGe2UT48GALXR7ZnkbDbXL7Z7K5mB575tgICKldG80ygclfPtUvH4e7tlm+PtduY76+HkImG39RGjeKJ1ZHs4DaOBecyjvqNesPD1gRmRzwFBB1p9q8ie1sEt5bP/2A//tk/EYUmE1/EIg2AE//xoC+fhX6fNdomxCsGfp+5OeW/58hOs2p38qmwOWAG84Pm9Ok5FBalXf0daUiEksSV5CX+zr+k3Gdv95dZeZIqVE88hLDiebrn63SWKyCDxOo7J5Jo0HwGpp9TlTutWJvKsVeyFyWE4fIpp9H67WV+9kX3/LWO3ue8mz/ZNMkFuNs6FV227ZDm+nX+fMRyqbA4a6tbKfXc4umF8TZ1HiJ0hhPDIWh//5cHW3/vvliBZ+rjOdVmKI+BJ8ksKI7dbFNUsZtWEZ9dFmuhkphpmzAtQGG6KhsnkjY6gdsFrMvA8x4rXoNFzTMEzz8B89dvt5BD8Ond4/sYn0MlxfHUOx2p7aqSkyLDb5ny0zB7TUnL+bSvTuLbujbbN+n61iZ65aDNVa7NaeN2BXYlxn7u8GOIIG01xtUs/JDbVlGf7cG0eCcLDsePORazYdkf0ijbH/qRdr2x/DobRUTDH5lRLt78Lnbjs5jKFd4Ob/Hj4RjZURjKrhswEPT2niBYwlSIoRYHONNrV61qvDGn4DhRUbqm5OF/lGujf1a5HLKIdlcH2d2DKP66dTYr2TlGZracoDB9kRRwoGjB/dBBHBDFtsZKgK/rNVQAABoAnCLPtEYYMxrsIzVg/oiGMzEuhr5kNi0n4fHikWE3xIDZE7yTdhbIhdFYAHvD7GjP+Yh4NTLlpD1JWgAQYwcMIa63WGyxqNC0cUQz9QS0OvT19OTqh8TIj2VZLRYGxI7MJsUQBD1YQKHRMNMxrqSsgRCDBwYmJZ7DO6TKPtgqMR+oF2JuwfqRHeIvxOxOMvuffuG8JVyXCL2ccoCN33EXVRhPAGqDuLlmuD8VENbewtthhViyerzwjPsltK5urTwrslSKVjienIAsSGUqs+B+R/qVqhbqjDZ50AdgXkq0aTLMBAYngW7yJbOafHzMHGUHVuGOYkfE+yN3JcC0jGmgwp+sge1BaFDd4ixxnSRgUEIOZFgAa0IfknFuxEaV0qlhijqzQUNyh/ThavCaOtZN6CsSFbMKol2tNuWUrjATKalWm+CSTAhjQ6GDhp3oA/jJBJZF+YZJ7mKNZF1OWvRDjKxCthbAitCgBaIKdX1iSKdgBTtFNgAxKaggprgXwKoXw4E/rhnrXyufpz4EccLf76UeIlGBtyF9xHxojNtNIsFOQnuJ6wK6LFAAMnBtBYWL+al6312MfHEi7rtPMIJQnn1wi7veof74K6aZhuPbhnLa/X9REKbQpjQ/y5IzZrEpK96IUsnD5t8X4tbgkkz9zs5xtkm5bo11PRC5W5nojyjaGzMILa5Dn36wHPQTjNtmI07b0qV1WnaZlMbiJWaCtK8jMBsPJUi/tEF7FZg9DmDposGUdBg9KhjQ4EX2BcHsVIG+eJSomwD2naqcVYzycwQjHf4ecrtNAHHTOFGKwc/xwrtC8SQwIEcfjZCsgeND3UgUZdFgoBItOh2WCC+h3MZi1SU8eibkMjd8KpQQqd2h23ZMIIheZ7NhfCLuBj4lbThwRjQw1eShFrJ0XnPcJiAF9JW4VdNe9Jo4OBE5BLQZ6HdbYcCw8HkmX6kleDFHozU14V2t76WuSye6wLnBghEsY+jA3xM0xtuIeIaw2TMqlV/77c2mFxw63fQXZpQVj7DPD3xADZiXgxnGYGPeRzRW/sEStf9fCPxvWgNomFuOMa2r7WupX3cd7dSUevYGzI5NZKoRIOoumANFcicWFXxBKogJs868HYsLh87gwI9iSC+nJ7GJCzuQF4HzoD8wOmh4GpvfvDv8Lt3Y/MXrvu7ee7XPTtZ9Zw/PiVWBaSwQery81oIX+aqIZQOlzt5X9USSJWUL+DRnRR1+xmLwlVFfwSbWBLELuLuBAo3kggQOspJ8peYpqVVYUAAM2jcp1lpF6JIg0Z+6kCB6pAYO2wgBq/lfq0UGCeu5gKIUrMG9CTjJZLFCuNrLWM87vR43z5FSOm2R2pOvWAJVhImxucQRUkueS+ADDunNCMXMOdAQVO0twYSpsRQ83HYHLgEhTPi0JpQt6OVEJmscQmVKHLH9Ns/ZM8Lq8so4WMW2KQkE8z2JUT9EWAG4dbvzNAI7pA3DZBJCxQ2Ceag3AZNGZjx1PmM0OMWJ8N6XsPxoa4Ga0FcrWoZjWUjUkRAarfcVoC6NAMRIGT5EdkNZ6vmCoH78BTOJ0ezUEUlo0cAaBiEjlRFF6O7EYZE2RUltFCK21Qc1RQoCbGw0h8kgYArVGdV6mCXPyz1pliuJwOojm4gnStdxALmeLNhFRDEe6tbwef+k6iuEAvy2j5lPRaDsjWj8UXrRoWFZBwK1UU0DY1C3cEtofNyojBZtR3I6vqM8AIooJfqGLSQj/VElUXRVlPCdE4KM1oQb+/oMKHAfSpY84Z0Mq+oYDm1u8gBUssBDoCsdbExRacOcARRPsbOgLgBdXlVxj9LWvpo+QZz2qGxh617IYJd8U24i1OLIsABTS3fgfFMskjkkZ0gIeRLJw5wBFwHIEODDeaPoebhrLlIDAAz81owYCfuMKHBYrKxUb6DVOuygcSdtz6HXSCjTwiWcRsxvgknDmAcvRofzNkHjfBJGwnp34P+TOpRAwqrOapc+RRk00QZNrFe2FDMkFsKH59Xv3OcUaRLGLqMAcE4jmgc98Z6yy4jsBko2Uzt3P692A2ShUWQH3OTWnArS5Ir3GA13WZ60oMFNaA/n7qhyHaE+vH5RGNlp1xPAcQIHYBV0V0zquYbpBRzWXdhKfQBLmTyVGjohpuOInnHJcDGZvRvHmJJw6gsIO6NHOJUFyGmDmVxtjgrUk8B9FhjWiteCpmOUvPOrAp46FszRXU5/xwEgzq0w9wmfnYLsrk6oYIn4CWhhI2C3TW/w1TPxYUzJp0WJ8ZIxoYX1wv6zGrGTj2uD+GNqSHrWo4NcbPaUCJZlNNfWSCxClP2JXuTQYigGZQ/U4aQYJunnD5TkW8VZkoS5v1mREiu15tCpqMo6QMGz6uru4epYY2K81owQs3RIPNAZWDxBwylU+egcAJWAlYXXExmsWCnDFLZzmfGSLiQqEwioOADM2G0qX6D0A38KU0co65pQccIJUTB39gV8TsAGAzkLuBPHwpmiWCgkmiRHPgHDY9YMcmz7ht4rdDq1HKZqzhohp+LTp0Iqk0Q+ZGEifUSrtiMeDnbhDNLqTPEk3KCIRoQKIzDZEkgeIUsVQs6XFQ95ekMsOF1QLBh2hGlI3IO4tXG6o4Iu0Fh99R5dmFSq4SrxTTLE6eifZJzIUZa3rP/iL9x8Lk7NeXNe0iHgCUPOMft2tC2Ui0QVAAP5YFHr+Aqggw6ABWAl1pdSH/LLF+MM2iOaj1EUSWv4u5MHv+wXFcM9gtOt1O20+k7QK2+GstZ3/U8MUTAimtyNv6KCeZeFCErlgIDr+jotALFX3HpTfMSOM5qDZAWl+ccCFInu2JqDpN1zJNPdbmNmKzd0OZmFUEv9wu6Ml7QygolygiX/7wO9gu0NZABg61PkUSCZ/h5nn3dGRRjziFnYX5OflCx4babDL8LT80tyx5hovViPYeypNpsVCD1g7t3BG/fodJeV7oprjEWcFxKN5lepnpzibPus+5h3V8rvG3TLl3DMrhdxQ5LKwfSoALRnv0rhjw63cYNr6QsZ/MAcV7l7wEYBb1iJNnqUe39DWc5W1o2IZ79/mIkVEOvyMKCOwUDBQLTuqyYVUJsbpNdP8Oyg5dqLYgjenBoIb3nL/txshET/RXGHsbb1uG22w5odpr6YbTx7r39RiSkU6kowuqKGVXbbikaTnnnHLOHoNbv1Njt/tlyhtTuQzHOujmMc4kzzj6ATe09O6nwWi06b8XiajubLORqENCh99F+xWGeynXD6GsYFrcUVoqlAcInGBlW3gy6ExIjFN6NYUk+nGewtvbLAv67WlQigmTGsj6KfesplD/qWmr4FfqKsNj3mjlkBHCODznQuqaCC9yOAmTh9h2Wk5rve1oOaZGBaFhJq7DR6diSm8axdeIpKeMUKkQrYq0y6vfOQBVXF6I0VIDiH+860QsPiWGwXxQe6gtgxq1shYaZqIqS9ZDLtnVA2QfZBSmnLNn4ME0EdaA+FZU/RKrmp3XLj89Lo3Np3Q+oQCxb0bdGjljQjsOhoRjya7GF1BlN7/A5AzRDe6Lz15j4ahol7h/Jz1JWn5LgyQWJvDStpNavbbb1ag19cSM1gKdbCw8u9xDnr+ebZ5N+Tl7FqgSGf8Jn+8OLSOK2yiJSWp7ld6GIoXB6BOUg+P7vWzcTqimDmCKQoMQF98YQuN6Ukckg+cvYRhKlMdlUHT22sdRIZUYwJTCfSqFxNexyWN+/Ywzpv29ZhuQ72SiZrTauBBTExCt08ArCRQBLBUinjzj1+/EGLP1QKIxnYJsn53tKot2S6uzUsYLphvybTH0jBZT5WZwGXnGrCPcneWlQjlA4zCvAQO29t/0xfLxSCbsZBO6Psu1kJ5iXp9oL5Uffk/QYcjRUHOP79wGfgbdXNKWP/zOr99JMMl9ScMRfFnodqpR9TuLfmYEa6fbpm3FMuunyvDyw+8pNgxBVEu5AW/cTg32egwXLRQsFVIfegVIdSCs3+GUwnr5eK1jtmYeoO5k7PV9zWoRKxk4GGXT1nsnxTUHc9dtiC0BMmQyWvmrmpum5c/mu9182gvXhV0/C1sNsH5HYT+aBZFdDwecA+6iT3mXqNiuqSl+EAQj/3C9VHT3w/F1q71sYo5Bcyt5uA20hq1tzhDNlsloLTkf91CdVqPZbPC+XmUyGo/8jZr0YBCq3+EKJr/Bb0J1bMexwUWRYEO2pxu5vd4DnejiY39FGPdbpuMYBfqWDrnrnHNX6xdA1Zh7X+jfqEkzhmUfr6ghJihGqg79UJ10fantPgLjEB/7E6LTV8yWYucvtT8Okhmt/CcsRHDYO/UlvlGTBqkE9TsIPvGzXEmjQW2+T3vIqbc5nLeqmzPy6i/bgWmFUqilnYli8hmtDu3bR4qV19b0b9SkaZrCj1ckmNKGFPPpcl4bDWp67b3cCnYclBe3Q0tnURrr7+yCpqE7akhlbXq+KI10RmupEPZ1i+fdkr9Rk+XJBPU7DDyDcqYtliK3i1pwc9+vjWXNt+017sV2tfrsfYe7ZpPu4mZT18zDR0VV21B258zlQPObmM7Yln2tTmAcsV+REb+feAjojFWBsh1QvnwWFwCN27VRp+PXbqVNiSU4PxavmK1bht4Ltjc7b7GH197NZ8GDrpluM/qikNoyXbogJaH88DsH455RQLWGtuGKAW7tCxepXoWBk+Ij051eGa/ZRhwt82v+slavBUfs/O5Ga+Y2R+iatJq67oawXP3wdeXkGadh6tOzpxuOyGgdXvM17nczw11lzwUB+4W8CYLOWJUEDzqBxv+88WFYrjZK+Kpz2Jrz4yK+gxuF8sXQuM+H+SXOed8/ayk+LSReXN7XNbcFz3HaDddQtuJB9kFXxfgnkawb8Mo/pXwxaY8MU29Bn2zPA7qlKVMPvN0NZrPR8bt/PA9nbheph1Bkmlqvv7jw1xmOwdLb+i3NiGrFjPpo7l2mAlsGnd2014zGFA5Ks+qj7e727Gu39Gb1vcpivqvohEJy/5G83qx9gazQGTHojrvd5X9rSw2W+4q1Cw9q7N3MRg/geLHTG03vvc5/aykqcLE/YDy53FWEFSpUqFChQoUKFSpUqFChQoUKFSpUqFChQoUKFSpUqFChQoUKFY7H/wDj+FqvnfNIXQAAAABJRU5ErkJggg==");

        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작
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
                case R.id.ht_f1:
                case R.id.ht_f2:
                case R.id.ht_f3:
                case R.id.ht_f4:
                case R.id.ht_f5:
                case R.id.ht_f6:
                    Fragment ht2 = new HomeTrainingFragment();
                    args.putInt("id", id);
                    ht2.setArguments(args);
                    HTfragment.push(ht2);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.fitness_1_1:
                case R.string.fitness_1_2:
                case R.string.fitness_1_3:
                    Fragment ht3 = new HomeTrainingFragment();
                    args.putInt("id", id);
                    ht3.setArguments(args);
                    HTfragment.push(ht3);
                    mainfragment = HTfragment.peek();
                    break;
                case R.string.my_info:
                    MPfragment.push(new HomeTrainingFragment());
                    mainfragment = MPfragment.peek();
                    break;
                case R.string.my_trainee:
                    MPfragment.push(new HomeTrainingFragment());
                    mainfragment = MPfragment.peek();
                    break;
                case R.string.my_calender:
                    MPfragment.push(new HomeTrainingFragment());
                    mainfragment = MPfragment.peek();
                    break;
                    /*
                case R.string.logout:
                    Logout(this);
                    break;
                case R.string.app_info:
                    showSimpleDialog();
                    break;
                case R.string.separate:
                    Util.DeleteUser(kakaoid + "");
                    Logout(this);
                    break;*/
            }
        }/*
        if (newfragment != null) {
            if (newfragment instanceof TrainerMatchFragment || newfragment instanceof TrainerMatch3Fragment) {
                TMfragment.push(newfragment);
                mainfragment = TMfragment.peek();
            } else if (newfragment instanceof Camera2BasicFragment) {
                HTfragment.push(newfragment);
                mainfragment = HTfragment.peek();
            }
        }*/
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainfragment, mainfragment);
        ft.commit();
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mainfragment = HTfragment.peek();
                menu = 1;
                break;
            case 1:
                mainfragment = TMfragment.peek();
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

    public class AutoScrollAdapter extends PagerAdapter {

        Context context;
        ArrayList<String> data;

        public AutoScrollAdapter(Context context, ArrayList<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //뷰페이지 슬라이딩 할 레이아웃 인플레이션
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.auto_viewpager,null);
            ImageView image_container = (ImageView) v.findViewById(R.id.image_container);
            Glide.with(context).load(data.get(position)).into(image_container);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View)object);

        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
