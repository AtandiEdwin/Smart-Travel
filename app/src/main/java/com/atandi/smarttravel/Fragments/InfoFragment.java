package com.atandi.smarttravel.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atandi.smarttravel.R;

public class InfoFragment extends Fragment {
    ProgressDialog progressDialog;

    public InfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_layout,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button SuggestBtn = view.findViewById(R.id.BtnSuggest);

        SuggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"smartTravel@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Suggestion about smart travel system application");
                intent.putExtra(Intent.EXTRA_TEXT,"");
//                intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
                progressDialog.dismiss();

            }
        });
    }
}
