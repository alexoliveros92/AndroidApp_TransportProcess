package com.example.android.transportprocess2.storehouse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.RecyclerViewAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class StorehouseAdapter extends RecyclerView.Adapter<StorehouseAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Storehouse>listStore;
    private Dialog dialog;
    private Button btnStateSelected;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private EditText edtIncomplete;
    private FirebaseFirestore db;
    //this is what the user will put inside the dialog textBox and save in the completed bd collection.
    private String incomplete;

    public StorehouseAdapter(Context context, ArrayList<Storehouse> list) {
        this.context = context;
        this.listStore = list;
        db=FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public StorehouseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_storehouse_recycler_view,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StorehouseAdapter.MyViewHolder holder, int position) {
        Storehouse current = listStore.get(position);

        holder.storeTitle.setText(current.getStoreTitle());
        holder.storeLine.setText(current.getStoreLine());
        holder.storePlace.setText(current.getStorePlace());
        holder.storeTime.setText(current.getStoreTime());
        holder.storeImageState.setImageResource(current.getStoreImageState());
        holder.storeNoOrder.setText(current.getStoreNoOrder());

        //--------------------------dialog message--------------------------------------------
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_state_storehouse);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        btnStateSelected = dialog.findViewById(R.id.btnSelectedState);
        radioButton1 = dialog.findViewById(R.id.radioStore1);
        radioButton2 = dialog.findViewById(R.id.radioStore2);
        radioButton3 = dialog.findViewById(R.id.radioStore3);
        radioButton4 = dialog.findViewById(R.id.radioStore4);
        edtIncomplete = dialog.findViewById(R.id.edtNoOrder);

        holder.layoutStorehouseView.setOnClickListener(e->{
            TextView titleStoreItemSelected = dialog.findViewById(R.id.titleStoreItemSelected);
            titleStoreItemSelected.setText(current.getStoreTitle());
            dialog.show();
            clickCheckBox();
            //----------------onClick responsible to control de alertDialog functions---------------
            btnStateSelected.setOnClickListener(o->{
                int state = checkState();
                switch (state){
                    case 0:
                        Toast.makeText(context,"please, select a state",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        db.collection("Requests")
                                .document(current.getStoreNoOrder())
                                .update("state","Ordenado");
                        upDateView(current);
                        break;
                    case 2:
                        db.collection("Requests")
                                .document(current.getStoreNoOrder())
                                .update("state","Preparando");
                        upDateView(current);
                        break;
                    case 3:
                        closeRequest(current);
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Completo")
                                .setIcon(R.drawable.ic_thumb_up_black_24dp)
                                .setMessage(current.getStoreTitle())
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                                .show();
                        break;
                    case 4:
                        incomplete = edtIncomplete.getText().toString();
                        if(!incomplete.equals("")){
                            db.collection("Requests")
                                    .document(current.getStoreNoOrder())
                                    .update("state","Incompleto");
                            upDateView(current);
                            db.collection("Requests")
                                    .document(current.getStoreNoOrder())
                                    .update("reason",incomplete);
                        }else {
                            Toast.makeText(context,"Please, insert a reason",Toast.LENGTH_LONG).show();
                            edtIncomplete.getText().clear();
                            edtIncomplete.requestFocus();
                        }
                        break;
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listStore.size();
    }

//----------------------method responsible to udDate de IU state------------------------------------

    public void upDateView(Storehouse storehouse){
        db.collection("Requests")
                .document(storehouse.getStoreNoOrder())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        storehouse.setStoreState(documentSnapshot.getData().get("state").toString());
                        notifyDataSetChanged();
                        dialog.cancel();
                        Toast.makeText(context,"state updated successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void closeRequest(Storehouse storehouse){
        String timeCompleted = RecyclerViewAdapter.dateAndTime.getCurrentTime();
        String currentDate =RecyclerViewAdapter.dateAndTime.getCurrentDate();

        Map<String, Object> FinalData = new HashMap<>();
        FinalData.put("title",storehouse.getStoreTitle());
        FinalData.put("line",storehouse.getStoreLine());
        FinalData.put("position",storehouse.getStorePlace());
        FinalData.put("order",storehouse.getStoreNoOrder());
        FinalData.put("time ordered", storehouse.getStoreTime());
        FinalData.put("time completed",timeCompleted);
        FinalData.put("date",currentDate);
        Timestamp timestamp=Timestamp.now();
        long tmsTp = timestamp.getSeconds();

        db.collection("Completed")
                .document(Long.toString(tmsTp))
                .set(FinalData)
                .addOnSuccessListener(task -> {
                })
                .addOnFailureListener(task -> {
                });
        dialog.cancel();
        notifyDataSetChanged();
        db.collection("Requests")
                .document(storehouse.getStoreNoOrder())
                .delete()
                .addOnSuccessListener(task -> {
                    listStore.remove(storehouse);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(task -> {
                    Toast.makeText(context,"It was not possible to update data",Toast.LENGTH_LONG).show();
                });

    }

    private void clickCheckBox(){
        radioButton3.setOnClickListener(v -> {
            edtIncomplete.setVisibility(View.GONE);
        });
        radioButton1.setOnClickListener(v -> {
            edtIncomplete.setVisibility(View.GONE);
        });
        radioButton2.setOnClickListener(v -> {
            edtIncomplete.setVisibility(View.GONE);
        });
        radioButton4.setOnClickListener(v -> {
            edtIncomplete.setVisibility(View.VISIBLE);
        });
    }

    private int checkState() {
        if (radioButton1.isChecked()) {
            return 1;
        } else if (radioButton2.isChecked()) {
            return 2;
        } else if (radioButton3.isChecked()) {
            return 3;
        } else if (radioButton4.isChecked()) {
            return 4;
        } else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView storeTitle;
        private TextView storeLine;
        private TextView storePlace;
        private LinearLayout layoutStorehouseView;
        private TextView storeTime;
        private TextView storeNoOrder;
        private ImageView storeImageState;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            storeTitle=itemView.findViewById(R.id.store_title);
            storeLine=itemView.findViewById(R.id.store_line);
            storePlace=itemView.findViewById(R.id.store_place);
            storeTime=itemView.findViewById(R.id.storehouse_time);
            storeNoOrder = itemView.findViewById(R.id.store_noOrder);
            storeImageState = itemView.findViewById(R.id.storehouseImageStateId);
            layoutStorehouseView=itemView.findViewById(R.id.storeHouseRootView);
        }
    }
}