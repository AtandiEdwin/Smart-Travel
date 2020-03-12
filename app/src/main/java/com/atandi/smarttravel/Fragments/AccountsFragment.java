package com.atandi.smarttravel.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atandi.smarttravel.Constants.MyBuilderClass;
import com.atandi.smarttravel.EditFragments.ManageAccountFragment;
import com.atandi.smarttravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;

import static com.atandi.smarttravel.Constants.Links.FETCH_ACCOUNT_DETAILS;
import static com.atandi.smarttravel.Constants.PaperComons.USER_NAME;
import static com.atandi.smarttravel.Constants.PaperComons.USER_PHONE;

public class AccountsFragment extends Fragment {

    ListView listView;
    TextView userNameId,userPhoneId;
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
        userPhoneId = view.findViewById(R.id.userPhoneId);
        userNameId = view.findViewById(R.id.userNameId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);

        Paper.init(getContext());

        String userName = Paper.book().read(USER_NAME);
        final String Phone = Paper.book().read(USER_PHONE);
        userNameId.setText(userName);
        userPhoneId.setText(Phone);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
               if(str.equals("Check Account Balance")){
                   StringRequest acountrequest = new StringRequest(Request.Method.POST, FETCH_ACCOUNT_DETAILS, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONArray jsonArray = new JSONArray(response);
                               for(int i=0; i<jsonArray.length();i++){
                                   JSONObject jsonObject = jsonArray.getJSONObject(i);

                                   String balance = jsonObject.getString("balance");
                                   String deposit = jsonObject.getString("deposit");
                                   String spent = jsonObject.getString("spent");

                                  final String message = "Your balance is : Ksh "+balance+"\nTotal deposits : Ksh "+deposit+"\nTotal expenses : Ksh "
                                          +spent + "\n Thank you for trusting and joining us.";

                                   AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                   alert.setTitle("Smart Travel");
                                   alert.setMessage(message);
                                   alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           getActivity().onBackPressed();
                                       }
                                   });
                                   alert.create().show();
                               }



                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }
                   },
                           new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {

                               }
                           }){
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String,String> map = new HashMap<>();
                           map.put("phone","0726123366");
                           return map;
                       }
                   };
                   RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
                   requestQueue.add(acountrequest);
               }
               else if(str.equals("Account Settings")){

                   FragmentManager fragmentManager = getFragmentManager();
                   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   fragmentTransaction.replace(R.id.Framelayoutid,new ManageAccountFragment());
                   fragmentTransaction.addToBackStack(null).commit();
               }
            }
        });
    }
}
