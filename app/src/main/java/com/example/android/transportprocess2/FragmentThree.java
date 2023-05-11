package com.example.android.transportprocess2;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FragmentThree extends Fragment {

    public FragmentThree() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_frragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();
        listCore.add(new core("MDM",getString(R.string.line_three),1));
        listCore.add(new core("Longaniza Campero","Guatemala y El Salvador",1));
        listCore.add(new core("Chorizo Argentino",getString(R.string.line_three),1));
        listCore.add(new core("Chorizo Salvadoreño","Con reúso",1));
        listCore.add(new core("Chorizo Salvadoreño","Sin reúso",1));
        listCore.add(new core("Longaniza Pollo PR","Pollo Rey",1));
        listCore.add(new core("Paté",getString(R.string.line_three),1));
        listCore.add(new core("Paté","Hígado para cutter",1));
        listCore.add(new core("Chorizo Argentino","Fresco",1));
        listCore.add(new core("Cantimpalo",getString(R.string.line_three),1));
        listCore.add(new core("Grasa emulsionada",getString(R.string.line_three),1));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCore, getString(R.string.hot_dog));
        RecyclerView recyclerView = view.findViewById(R.id.third_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        return view;
    }
}
