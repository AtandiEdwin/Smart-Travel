package com.atandi.smarttravel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.EditFragments.ManageAccountFragment;
import com.atandi.smarttravel.R;

public class AccountsFragment extends Fragment {

    ListView listView;
    String[] values = {"Check Account Balance","Account Settings"};
    public AccountsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_layout,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView  = view.findViewById(R.id.myListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
               if(str.equals("Check Account Balance")){
                   MyBuilderClass myBuilderClass = new MyBuilderClass();
                   myBuilderClass.MyBuilder(getContext(),"200");
               }
               else if(str.equals("Account Settings")){

                   FragmentManager fragmentManager = getFragmentManager();
                   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   fragmentTransaction.replace(R.id.nav_host_fragment,new ManageAccountFragment());
                   fragmentTransaction.addToBackStack(null).commit();
               }
            }
        });
    }
}
