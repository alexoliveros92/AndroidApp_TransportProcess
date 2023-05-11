package com.example.android.transportprocess2.deliver;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.FeatureGroupInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.storehouse.StorehouseAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.MyViewHolder> {

    private ArrayList<Request>requests;
    private Context context;
    private Dialog dialog;
    private Button btnCancel;
    private Button btnNoCancel;
    private FirebaseFirestore db;

    public DeliverAdapter(ArrayList<Request> requests, Context context) {
        this.requests = requests;
        this.context = context;
        db=FirebaseFirestore.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request_recycler_view,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Request current = requests.get(position);

        holder.requestTitle.setText(current.getRequestTitle());
        holder.requestLine.setText(current.getRequestLine());
        holder.requestPlace.setText(current.getRequestPlace());
        holder.requestTime.setText(current.getTime());
        holder.requestImageState.setImageResource(current.getRequestImageState());
        holder.requestReason.setText(current.getReason());

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_cancel_order);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        btnCancel=dialog.findViewById(R.id.btnYesCancel);
        btnNoCancel=dialog.findViewById(R.id.btnNoCancel);

        holder.layoutRequestView.setOnLongClickListener(v -> {
            db.collection("Requests")
                    .document(current.getRequestNoOrder())
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()){
                                String state=documentSnapshot.getData().get("state").toString();
                                cancelRequest(state,current);
                            }
                        }
                    });
            return true;
        });
    }
    private void cancelRequest(String state, Request current){
        if (!state.equals("Preparando")) {
            TextView txtCancelOrder = dialog.findViewById(R.id.txtCancel_order);
            txtCancelOrder.setText(current.getRequestTitle());
            dialog.show();
//-------------------------Delete request-----------------------------------------
            btnCancel.setOnClickListener(e -> {
                db.collection("Requests")
                        .document(current.getRequestNoOrder())
                        .delete()
                        .addOnSuccessListener(task -> {
                            Toast.makeText(context, current.getRequestTitle() + " eliminada", Toast.LENGTH_SHORT).show();
                            requests.remove(current);
                            notifyDataSetChanged();
                            dialog.cancel();
                        })
                        .addOnFailureListener(task -> {
                            Toast.makeText(context, "cancel failed", Toast.LENGTH_SHORT).show();
                        });
            });
            btnNoCancel.setOnClickListener(e -> {
                dialog.cancel();
            });
        } else {
            Toast.makeText(context,"It is not possible to cancel this order",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView requestTitle;
        private TextView requestLine;
        private TextView requestPlace;
        private TextView requestTime;
        private ImageView requestImageState;
        private TextView requestReason;
        private LinearLayout layoutRequestView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requestTitle=itemView.findViewById(R.id.request_title);
            requestLine=itemView.findViewById(R.id.request_line);
            requestTime=itemView.findViewById(R.id.request_time);
            requestPlace=itemView.findViewById(R.id.request_place);
            requestImageState = itemView.findViewById(R.id.imageStateId);
            requestReason = itemView.findViewById(R.id.reasonTxt);
            layoutRequestView=itemView.findViewById(R.id.requestRootView);
        }
    }
}
