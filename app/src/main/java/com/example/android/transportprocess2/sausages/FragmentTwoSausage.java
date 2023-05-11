package com.example.android.transportprocess2.sausages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.example.android.transportprocess2.core;

import java.util.ArrayList;

public class FragmentTwoSausage extends Fragment {

    private RecyclerView recyclerView;

    public FragmentTwoSausage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();
        listCore.add(new core("MDM",getString(R.string.line_weiler),2));
        listCore.add(new core("Peil de pollo",getString(R.string.line_weiler),2));
        listCore.add(new core("Salchicha Hot Dog",getString(R.string.line_weiler),2));
        listCore.add(new core("Salchicha Hot Dog","Sin reúso",2));
        listCore.add(new core("Salchicha Franks",getString(R.string.line_weiler),2));
        listCore.add(new core("Salchicha del Rey (normal y picante)","Sin reúso",2));
        listCore.add(new core("Salchicha del Rey picante","Con reúso",2));
        listCore.add(new core("Salchicha Avicola PR","Pollo Rey",2));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCore,getString(R.string.line_weiler));
        recyclerView = view.findViewById(R.id.two_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        //Add divider lines
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
