package com.atandi.smarttravel.EditFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atandi.smarttravel.R;

public class DeleteAccountFragment extends Fragment {
    public DeleteAccountFragment() {
        // Required empty public constructor
    }

    Button btnDeleteAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDeleteAccount  =view.findViewById(R.id.btnDeleteAccount);
    }
}
