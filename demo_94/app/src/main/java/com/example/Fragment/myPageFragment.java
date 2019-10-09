package com.example.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.Activity.Logout;
import com.example.Activity.MainActivity;
import com.example.Activity.MainActivity2;
import com.example.Activity.SessionCheck;
import com.example.Activity.UpdateActivity;
import com.example.Activity.UpdateSearch;
import com.example.Activity.UpdateSearch_Sub;
import com.example.Activity.WithDraw;
import com.example.List.MP_List_Item;
import com.example.List.MP_List_Item_Adapter;
import com.example.calendar.ScheduleFragment;
import com.example.dao.Alert;
import com.example.demo_94.R;
import com.example.dto.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class myPageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        ListView listview = view.findViewById(R.id.mp_list);

        TextView mp_name2 = (TextView) view.findViewById(R.id.mp_name2);
        TextView mp_birth2 = (TextView) view.findViewById(R.id.mp_birth2);
        TextView mp_interest2 = (TextView) view.findViewById(R.id.mp_interest2);
        TextView mp_sex2 = (TextView) view.findViewById(R.id.mp_sex2);

        Customer customertemp = null;
        try {
            UpdateSearch updatesearch = new UpdateSearch("http://"+MainActivity.curip+"/SSAFYProject/customerinformation_update_search.php");
            String jsonString = updatesearch.updatesearch(MainActivity.cur_id);

            JSONObject jsonobj = new JSONObject(jsonString);
            JSONArray result = jsonobj.getJSONArray("result");

            JSONObject c = result.getJSONObject(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            customertemp = new Customer(c.getString("id"), c.getString("password"), c.getString("name"), format.parse(c.getString("birth")), c.getString("sex"));

            UpdateSearch_Sub updatesearch_sub = new UpdateSearch_Sub("http://"+MainActivity.curip+"/SSAFYProject/customerinformation_update_search_sub.php");
            jsonString = updatesearch_sub.updatesearch_sub(MainActivity.cur_id);

            jsonobj = new JSONObject(jsonString);
            result = jsonobj.getJSONArray("result");

            c = result.getJSONObject(0);
            String inter[] = new String[3];
            inter[0] = c.getString("inter1");
            inter[1] = c.getString("inter2");
            inter[2] = c.getString("inter3");
            customertemp.setInter(inter);
        } catch (MalformedURLException | JSONException | ParseException e) {
            e.printStackTrace();
        }

        final Customer customer = customertemp;
        mp_name2.setText(customer.getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        mp_birth2.setText(format.format(customer.getBirth()));
        mp_sex2.setText(customer.getSex());
        String inter[] = customer.getInter();
        mp_interest2.setText("");
        for(int i=0; i<inter.length; i++) {
            if(inter[i] == null || inter[i].length() == 0 || inter[i].equals("null"))
                break;
            if(i == 0) {
                mp_interest2.setText(inter[i]);
            } else {
                mp_interest2.setText(mp_interest2.getText() + "\n" + inter[i]);
            }
        }

        ArrayList<MP_List_Item> items = new ArrayList<>();
        items.add(new MP_List_Item(R.string.modify_my_info));
        items.add(new MP_List_Item(R.string.alarm_on_off));
        items.add(new MP_List_Item(R.string.developer_info));
        items.add(new MP_List_Item(R.string.user_logout));
        items.add(new MP_List_Item(R.string.user_withdraw));

        final MP_List_Item_Adapter listadapter = new MP_List_Item_Adapter(getContext(), R.layout.mp_list_item, items);
        listview.setAdapter(listadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MP_List_Item mplit = (MP_List_Item) listadapter.getItem(position);
                switch (getString(mplit.getNameId())) {
                    case "로그아웃":
                        new Logout().logout();
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case "정보 수정하기":
                        intent = new Intent(getActivity().getApplicationContext(), UpdateActivity.class);
                        startActivity(intent);
                        break;
                    case "회원탈퇴":
                        try {
                            WithDraw withdraw = new WithDraw("http://" + MainActivity.curip + "/SSAFYProject/customerinformation_withdraw.php");
                            if (withdraw.withdraw(MainActivity.cur_id).equals("1")) {
                                Toast.makeText(getActivity().getApplicationContext(),getString(R.string.success_withdraw),Toast.LENGTH_SHORT).show();
                                intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),getString(R.string.error_withdraw),Toast.LENGTH_SHORT).show();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        ((MainActivity)getActivity()).ChangeFragmentMain(mplit.getNameId());
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
        }
    }
}
