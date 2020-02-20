package com.atandi.smarttravel.EditFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.R;

public class ManageAccountFragment extends Fragment {

    ListView listView;
    String[] values = {"Change phone number","Change Password","Delete Account"};

    public ManageAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView  = view.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                if(str.equals("Change phone number")){
                    MyBuilderClass myBuilderClass = new MyBuilderClass();
                    myBuilderClass.MyBuilder(getContext(),"200");
                }
                else if(str.equals("Change Password")){

                    FragmentManager fragmentManager = getFragmentManager();
                    assert fragmentManager != null;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment,new ChangePasswordFragment());
                    fragmentTransaction.addToBackStack(null).commit();
                }
                else if(str.equals("Delete Account")){

                    FragmentManager fragmentManager = getFragmentManager();
                    assert fragmentManager != null;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment,new DeleteAccountFragment());
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });
    }
}
