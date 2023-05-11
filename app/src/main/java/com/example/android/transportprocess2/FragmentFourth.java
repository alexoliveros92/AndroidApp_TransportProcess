package com.example.android.transportprocess2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FragmentFourth extends Fragment {

    public FragmentFourth() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        ArrayList<core> listCore = new ArrayList<>();
        listCore.add(new core("MDM",getString(R.string.line_four),1));
        listCore.add(new core("Longaniza Butifarra","Tarima 1",1));
        listCore.add(new core("Longaniza Butifarra","Tarima 2",1));
        listCore.add(new core("Longaniza Butifarra (sin reúso)","Tarima 1",1));
        listCore.add(new core("Longaniza Butifarra (sin reúso)","Tarima 2",1));
        listCore.add(new core("Chorizo negro","Tarima 1",1));
        listCore.add(new core("Chorizo negro","Tarima 2",1));
        listCore.add(new core("Chorizo negro (sin reúso)","Tarima 1",1));
        listCore.add(new core("Chorizo negro (sin reúso)","Tarima 2",1));
        listCore.add(new core("Copetín - Extremeño","MADURADOS",1));
        listCore.add(new core("Copetín - Extremeño","COCIDOS",1));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCore, getString(R.string.hot_dog));
        RecyclerView recyclerView = view.findViewById(R.id.fourth_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        return view;
    }
}
