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

public class FragmentThreeSausage extends Fragment {

    public FragmentThreeSausage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_frragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();
        listCore.add(new core("MDM",getString(R.string.line_cutter),3));
        listCore.add(new core("Peil de pollo",getString(R.string.line_cutter),3));
        listCore.add(new core("Salchicha Hot Dog",getString(R.string.line_cutter),3));
        listCore.add(new core("Salchicha Hot Dog","Sin reúso",3));
        listCore.add(new core("Salchicha Franks",getString(R.string.line_cutter),3));
        listCore.add(new core("Salchicha del Rey (normal y picante)","Sin reúso",3));
        listCore.add(new core("Salchicha del Rey picante","Con reúso",3));
        listCore.add(new core("Salchicha Avicola PR","Pollo Rey",3));
        listCore.add(new core("Salchicha Americana CON queso","C/Q",3));
        listCore.add(new core("Salchicha Americana CON y SIN queso","Sin reúso",3));
        listCore.add(new core("Salchicha Pavo Especial",getString(R.string.line_cutter),3));
        listCore.add(new core("Frankfurt - Cocktail",getString(R.string.line_cutter),3));
        listCore.add(new core("Salchicha de Pechuga",getString(R.string.line_cutter),3));
        listCore.add(new core("Salchicha Ahumada Especial",getString(R.string.line_cutter),3));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCore, getString(R.string.line_cutter));
        RecyclerView recyclerView = view.findViewById(R.id.third_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        return view;
    }
}
