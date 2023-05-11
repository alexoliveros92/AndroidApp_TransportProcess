package com.example.android.transportprocess2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment {

    private RecyclerView myRecyclerView;

    public FragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment, container, false);

        ArrayList<core> listCore = new ArrayList<>();

        listCore.add(new core("MDM",getString(R.string.line_one),1));
        listCore.add(new core("Longaniza Campestre","Recorte de pechuga",1));
        listCore.add(new core("Longaniza Campestre","Recorte de cerdo",1));
        listCore.add(new core("Chorizo Pollo Indio",getString(R.string.line_one),1));
        listCore.add(new core("Chorizo Colorado",getString(R.string.line_one),1));
        listCore.add(new core("Chorizo Colorado","Sin reúso",1));
        listCore.add(new core("Longaniza Criolla",getString(R.string.line_one),1));
        listCore.add(new core("Longaniza Criolla","Sin reúso",1));
        listCore.add(new core("Recorte de pechuga",getString(R.string.line_one),1));
        listCore.add(new core("Recorte de cerdo",getString(R.string.line_one),1));
        listCore.add(new core("Chorizo Copetín Lata","Recorte de cerdo",1));
        listCore.add(new core("Chorizo Copetín Lata","Recorte de pechuga",1));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), listCore,getString(R.string.hot_dog));
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
