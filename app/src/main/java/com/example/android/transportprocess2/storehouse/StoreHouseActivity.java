package com.example.android.transportprocess2.storehouse;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.example.android.transportprocess2.deliver.DeliverActivity;
import com.example.android.transportprocess2.profiles.SignInActivity;
import com.example.android.transportprocess2.report.ReportActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class StoreHouseActivity extends AppCompatActivity {

    private StorehouseAdapter adapter;
    private ArrayList<Storehouse> list;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private Toolbar sToolbar;
    private TextView showPreparing;
    private TextView showOrdered;
    private TextView showIncomplete;
    private int countIncomplete;
    private int countOrdered;
    private int countPreparing;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_house);
        connect();
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        setSupportActionBar(sToolbar);
        button.setOnClickListener(v -> {
            startActivity(new Intent(this, ReportActivity.class));
            finish();
        });

//-----------------------------------------Add Divider Item Decoration----------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
//----------------------------------------Read from the FireBaseFireStore-------------------------

        String currentDate = RecyclerViewAdapter.dateAndTime.getCurrentDate();
        String yesterday = DeliverActivity.getYesterdayDay();

        db.collection("Requests")
                .whereGreaterThanOrEqualTo("date", yesterday).whereLessThanOrEqualTo("date", currentDate)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w("here", "Listen failed", e);
                        return;
                    }
                    list.clear();
                    countIncomplete = 0;
                    countOrdered = 0;
                    countPreparing = 0;
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(queryDocumentSnapshots)) {
                        Storehouse storehouse = new Storehouse();
                        storehouse.setStoreTitle(Objects.requireNonNull(documentSnapshot.getData().get("title")).toString());
                        storehouse.setStoreLine(Objects.requireNonNull(documentSnapshot.getData().get("line")).toString());
                        storehouse.setStorePlace(Objects.requireNonNull(documentSnapshot.getData().get("position")).toString());
                        storehouse.setStoreState(Objects.requireNonNull(documentSnapshot.getData().get("state")).toString());
                        storehouse.setStoreNoOrder(documentSnapshot.getId());
                        storehouse.setStoreTime(Objects.requireNonNull(documentSnapshot.getData().get("time")).toString());
                        //-----------process to count the documents in determine state----------------
                        String counter = storehouse.getStoreState();
                        if (counter.equals("Ordenado")) {
                            countOrdered++;
                        } else if (counter.equals("Incompleto")) {
                            countIncomplete++;
                        } else if (counter.equals("Preparando")) {
                            countPreparing++;
                        }

                        switch (storehouse.getStoreState()) {
                            case "Ordenado":
                                storehouse.setStoreImageState(R.drawable.ic_ordered);
                                break;
                            case "Preparando":
                                storehouse.setStoreImageState(R.drawable.ic_preparing);
                                break;
                            case "Incompleto":
                                storehouse.setStoreImageState(R.drawable.ic_incomplete);
                                break;
                            default:
                                storehouse.setStoreImageState(R.drawable.ic_cancel_black_24);
                        }

                        list.add(storehouse);
                    }
                    adapter = new StorehouseAdapter(this, list);
                    recyclerView.setAdapter(adapter);
                    showOrdered.setText(String.valueOf(countOrdered));
                    showIncomplete.setText(String.valueOf(countIncomplete));
                    showPreparing.setText(String.valueOf(countPreparing));
                });
    }

    private void connect() {
        recyclerView = findViewById(R.id.storeHouseRecyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sToolbar = findViewById(R.id.storehouse_toolbar);
        showOrdered = findViewById(R.id.txtShowOrdered);
        showIncomplete = findViewById(R.id.txtShowIncomplete);
        showPreparing = findViewById(R.id.txtShowDelivered);
        button = findViewById(R.id.btnGoReport);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SignOut:
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class dateAndTime {

        public static String getCurrentTime() {
            String format = "HH:mm:ss";
            return dateAndTime.getDateWithFormat(format);
        }

        public static String getCurrentDate() {
            String format = "yyyy-MM-dd";
            return dateAndTime.getDateWithFormat(format);
        }

        @SuppressLint("SimpleDateFormat")
        public static String getDateWithFormat(String format) {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYesterdayDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date date2 = c.getTime();
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date2);
    }
}