package com.example.android.transportprocess2.sausages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.example.android.transportprocess2.core;

import java.util.ArrayList;

public class FragmentOneSausage extends Fragment {

    private RecyclerView myRecyclerView;

    public FragmentOneSausage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();

        listCore.add(new core("MDM",getString(R.string.line_seydelmann),2));
        listCore.add(new core("Peil de pollo",getString(R.string.line_seydelmann),2));
        listCore.add(new core("Salchicha Hot Dog",getString(R.string.line_seydelmann),2));
        listCore.add(new core("Salchicha Hot Dog","Sin reúso",2));
        listCore.add(new core("Salchicha Franks",getString(R.string.line_seydelmann),2));
        listCore.add(new core("Salchicha del Rey (normal y picante)","Sin reúso",2));
        listCore.add(new core("Salchicha del Rey picante","Con reúso",2));
        listCore.add(new core("Salchicha Avicola PR","Pollo Rey",2));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), listCore,getString(R.string.line_seydelmann));
        myRecyclerView = view.findViewById(R.id.one_recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerView.setAdapter(adapter);

        //add the dividerLines between items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        myRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
