package com.example.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.util.Util;
import com.example.Activity.MainActivity;
import com.example.List.MP_List_Item;
import com.example.List.MP_List_Item_Adapter;
import com.example.calendar.ScheduleFragment;
import com.example.demo_94.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
                if(getString(mplit.getNameId()).equals("회원 관리")) {
                    Intent intent1 = new Intent(getActivity(), ScheduleFragment.class);
                    startActivityForResult(intent1, 102);
                }
                else {
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
