package com.example.android.transportprocess2.report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.transportprocess2.MainActivity;
import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.example.android.transportprocess2.deliver.DeliverActivity;
import com.example.android.transportprocess2.deliver.Request;
import com.example.android.transportprocess2.profiles.SignInActivity;
import com.example.android.transportprocess2.storehouse.StoreHouseActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {

    private Toolbar rToolbar;
    private ArrayList<Report> items;
    private FirebaseFirestore db;
    private Spinner spinner;
    private TextView textViewReport;
    private RecyclerView recyclerViewR;
    private ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        connect();
        setSupportActionBar(rToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        items = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerViewR.addItemDecoration(decoration);

        String today = RecyclerViewAdapter.dateAndTime.getCurrentDate();
        String yesterday = DeliverActivity.getYesterdayDay();

        db.collection("Completed")
                .whereGreaterThanOrEqualTo("date", yesterday).whereLessThanOrEqualTo("date", today)
                .addSnapshotListener((task, e) -> {
                    if (e != null) {
                        Log.w("here", "Listen failed.", e);
                        return;
                    }
                    items.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot1 : task) {
                        Report report = new Report();
                        report.setTitleR(Objects.requireNonNull(queryDocumentSnapshot1.getData().get("title")).toString());
                        items.add(report);
                    }
                    ArrayList<String> elements = new ArrayList<>();
                    for (int i = 0; i <= ((items.size()) - 1); i++) {
                        elements.add(items.get(i).getTitleR());
                    }

                    ArrayList<String> goodElements = removeDuplicates(elements);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, goodElements);
                    spinner.setAdapter(arrayAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String current = spinner.getAdapter().getItem(position).toString();
                            count(current, today, yesterday);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                });
    }

    public void count(String current, String today, String yesterday) {
        ArrayList<Report> currents = new ArrayList<>();
        currents.clear();
        db.collection("Completed")
                .whereGreaterThanOrEqualTo("date", yesterday).whereLessThanOrEqualTo("date", today)
                .whereEqualTo("title", current)
                .addSnapshotListener(((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w("here", "Listen failed.", e);
                        return;
                    }
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Report report2 = new Report();
                        report2.setTitleR(documentSnapshot.getData().get("title").toString());
                        report2.setLineR(documentSnapshot.getData().get("line").toString());
                        report2.setDateR(documentSnapshot.getData().get("date").toString());
                        report2.setTimeOR(documentSnapshot.getData().get("time ordered").toString());
                        report2.setTimeDR(documentSnapshot.getData().get("time completed").toString());
                        currents.add(report2);
                    }
                    textViewReport.setText(String.valueOf(currents.size()));
                    adapter = new ReportAdapter(this, currents);
                    recyclerViewR.setAdapter(adapter);
                }));
    }

    public void connect() {
        rToolbar = findViewById(R.id.report_toolbar);
        spinner = findViewById(R.id.resultSpinner);
        textViewReport = findViewById(R.id.txtShowReport);
        recyclerViewR = findViewById(R.id.ReportRecyclerView);
        recyclerViewR.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> inArray) {

        ArrayList<String> outArray = new ArrayList<>();
        boolean doAdd = true;

        for (int i = 0; i < inArray.size(); i++) {
            String testString = inArray.get(i);
            for (int j = 0; j < inArray.size(); j++) {
                if (i == j) {
                    break;
                } else if (inArray.get(j).equals(testString)) {
                    doAdd = false;
                    break;
                }
            }
            if (doAdd) {
                outArray.add(testString);
            } else {
                doAdd = true;
            }
        }
        return outArray;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, StoreHouseActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}