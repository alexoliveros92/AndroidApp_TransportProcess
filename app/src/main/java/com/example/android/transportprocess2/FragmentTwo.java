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

public class FragmentTwo extends Fragment {

    public FragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_fragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();
        listCore.add(new core("MDM",getString(R.string.line_two),1));
        listCore.add(new core("Copetín Franks",getString(R.string.line_two),1));
        listCore.add(new core("Extremeño Franks",getString(R.string.line_two),1));
        listCore.add(new core("Longaniza Franks",getString(R.string.line_two),1));
        listCore.add(new core("Parrillero Franks",getString(R.string.line_two),1));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listCore,getString(R.string.hot_dog));
        RecyclerView recyclerView = view.findViewById(R.id.two_recyclerView);
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
