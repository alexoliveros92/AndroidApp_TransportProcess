package com.example.android.transportprocess2.deliver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.transportprocess2.MainActivity;
import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.example.android.transportprocess2.profiles.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DeliverActivity extends AppCompatActivity {

    private DeliverAdapter adapter;
    private ArrayList<Request> requests;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);
        db = FirebaseFirestore.getInstance();
        connect();
        requests = new ArrayList<>();
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton button = findViewById(R.id.btnGoListItems);
        button.setOnClickListener(e -> {
            onBackPressed();
            finish();
        });

//--------------------------------Add Divider Item Decoration----------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

//--------------------------------Read from the FireBaseFireStore-----------------------

        String currentDate = RecyclerViewAdapter.dateAndTime.getCurrentDate();
        String yesterday = getYesterdayDay();

            db.collection("Requests")
                    .whereGreaterThanOrEqualTo("date",yesterday).whereLessThanOrEqualTo("date",currentDate)
                    .addSnapshotListener((task, e) -> {
                        if (e != null) {
                            Log.w("here", "Listen failed.", e);
                            return;
                        }
                        requests.clear();
                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task)) {
                            Request request2 = new Request();
                            request2.setRequestTitle(Objects.requireNonNull(documentSnapshot.getData().get("title")).toString());
                            request2.setRequestLine(Objects.requireNonNull(documentSnapshot.getData().get("line")).toString());
                            request2.setRequestPlace(Objects.requireNonNull(documentSnapshot.getData().get("position")).toString());
                            request2.setRequestState(Objects.requireNonNull(documentSnapshot.getData().get("state")).toString());
                            request2.setReason(Objects.requireNonNull(documentSnapshot.getData().get("reason")).toString());

                            switch (request2.getRequestState()) {
                                case "Ordenado":
                                    request2.setRequestImageState(R.drawable.ic_ordered);
                                    break;
                                case "Preparando":
                                    request2.setRequestImageState(R.drawable.ic_preparing);
                                    break;
                                case "Incompleto":
                                    request2.setRequestImageState(R.drawable.ic_incomplete);
                                    break;
                                default:
                                    request2.setRequestImageState(R.drawable.ic_cancel_black_24);
                            }
                            request2.setTime(Objects.requireNonNull(documentSnapshot.getData().get("time")).toString());
                            request2.setRequestNoOrder(documentSnapshot.getId());
                            requests.add(request2);
                        }
                        adapter = new DeliverAdapter(requests, DeliverActivity.this);
                        recyclerView.setAdapter(adapter);
                    });
        }

    public void connect() {
        mainToolbar = findViewById(R.id.request_toolbar);
        recyclerView = findViewById(R.id.deliverRecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("SimpleDateFormat")
    public static String getYesterdayDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        Date date2 = c.getTime();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date2);
    }
}
