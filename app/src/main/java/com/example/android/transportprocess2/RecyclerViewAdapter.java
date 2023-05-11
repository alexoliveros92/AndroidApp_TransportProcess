package com.example.android.transportprocess2;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<core> list;
    private Dialog dialog;
    private Dialog dialogKey2;
    private Dialog dialogCutter;
    private String lineTitle;
    private Button buttonSelected;
    private Button buttonSelectedKey2;
    private Button buttonSelectedCutter;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton1Key2;
    private RadioButton radioButton2Key2;
    private RadioButton radioButton3Key2;
    private RadioButton radioButton4Key2;
    private RadioButton radioButton5Key2;
    private RadioButton radioButton6Key2;
    private RadioButton radioButton1Cutter;
    private RadioButton radioButton2Cutter;
    private RadioButton radioButton3Cutter;
    private RadioButton radioButton4Cutter;
    private RadioButton radioButton5Cutter;

    private FirebaseFirestore db;

    public RecyclerViewAdapter(Context context, ArrayList<core> list, String lineTitle) {
        this.lineTitle = lineTitle;
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        core current = list.get(position);

        holder.title.setText(current.getItemTitle());
        holder.description.setText(current.getDescription());
        /*storageReference.child(current.getImagePath())
                .getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Picasso.get()
                            .load(uri.toString())
                            .into(holder.coreImage);
                }).addOnFailureListener(e -> {
                    Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
        });*/


//--------------------------dialog message--------------------------------------------
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_position);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        radioButton1 = dialog.findViewById(R.id.radio1);
        radioButton2 = dialog.findViewById(R.id.radio2);
        radioButton3 = dialog.findViewById(R.id.radio3);
        radioButton4 = dialog.findViewById(R.id.radio4);

        dialogKey2 = new Dialog(context);
        dialogKey2.setContentView(R.layout.dialog_position2);
        Objects.requireNonNull(dialogKey2.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        radioButton1Key2 = dialogKey2.findViewById(R.id.radio1_key2);
        radioButton2Key2 = dialogKey2.findViewById(R.id.radio2_key2);
        radioButton3Key2 = dialogKey2.findViewById(R.id.radio3_key2);
        radioButton4Key2 = dialogKey2.findViewById(R.id.radio4_key2);
        radioButton5Key2 = dialogKey2.findViewById(R.id.radio5_key2);
        radioButton6Key2 = dialogKey2.findViewById(R.id.radio6_key2);

        dialogCutter = new Dialog(context);
        dialogCutter.setContentView(R.layout.dialog_position_cutter);
        Objects.requireNonNull(dialogCutter.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        radioButton1Cutter = dialogCutter.findViewById(R.id.radio1_keyCutter);
        radioButton2Cutter = dialogCutter.findViewById(R.id.radio2_keyCutter);
        radioButton3Cutter = dialogCutter.findViewById(R.id.radio3_keyCutter);
        radioButton4Cutter = dialogCutter.findViewById(R.id.radio4_keyCutter);
        radioButton5Cutter = dialogCutter.findViewById(R.id.radio5_keyCutter);

        String time = dateAndTime.getCurrentTime();
        String date = dateAndTime.getCurrentDate();

        //coreItemView=recyclerView
        holder.coreItemView.setOnClickListener(e -> {

            //SAVE DATA FROM RECYCLERVIEW
            Map<String, Object> data = new HashMap<>();
            data.put("title", current.getItemTitle());
            data.put("state", "Ordenado");
            data.put("reason", "ok");
            data.put("line", lineTitle);
            data.put("time", time);
            data.put("date", date);

//-------------------------show dialog----------------------------------------------------

            switch (current.getKeyPosition()) {
                case 1:
                    buttonSelected = dialog.findViewById(R.id.btnSelectedPosition);
                    TextView titleItemSelected = dialog.findViewById(R.id.titleItemSelected);
                    titleItemSelected.setText(current.getItemTitle());
                    dialog.show();
                    buttonSelected.setOnClickListener(v -> {
                        String check = checkPlace();
                        if (!check.equals("")) {
                            data.put("position", check);
                            saveData(data);
                            dialog.cancel();
                        } else
                            Toast.makeText(context, "Select a place to receive", Toast.LENGTH_LONG).show();
                    });
                    break;

                case 2:
                    buttonSelectedKey2 = dialogKey2.findViewById(R.id.btnSelectedPosition_key2);
                    TextView titleItemSelected2 = dialogKey2.findViewById(R.id.titleItemSelected_key2);
                    titleItemSelected2.setText(current.getItemTitle());
                    dialogKey2.show();
                    buttonSelectedKey2.setOnClickListener(v -> {
                        String check2 = checkPlace();
                        if (!check2.equals("")) {
                            data.put("position", check2);
                            saveData(data);
                            dialogKey2.cancel();
                        } else
                            Toast.makeText(context, "Select a place to receive", Toast.LENGTH_LONG).show();
                    });
                    break;

                case 3:
                    buttonSelectedCutter = dialogCutter.findViewById(R.id.btnSelectedPosition_keyCutter);
                    TextView titleItemSelected3 = dialogCutter.findViewById(R.id.titleItemSelected_keyCutter);
                    titleItemSelected3.setText(current.getItemTitle());
                    dialogCutter.show();
                    buttonSelectedCutter.setOnClickListener(v -> {
                        String check3 = checkPlace();
                        if (!check3.equals("")) {
                            data.put("position", check3);
                            saveData(data);
                            dialogCutter.cancel();
                        } else
                            Toast.makeText(context, "Select a place to receive", Toast.LENGTH_LONG).show();
                    });
                    break;
                default:
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void saveData(Map<String, Object> data) {
        Timestamp timestamp = Timestamp.now();
        long tmsTp = timestamp.getSeconds();
        db.collection("Requests")
                .document(Long.toString(tmsTp))
                .set(data)
                .addOnSuccessListener(task -> {
                    Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(task -> {
                    Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show();
                });
    }

    private String checkPlace() {
        if (radioButton1.isChecked()) {
            return "1";
        } else if (radioButton2.isChecked()) {
            return "2";
        } else if (radioButton3.isChecked()) {
            return "3";
        } else if (radioButton4.isChecked()) {
            return "complementos";
        } else if (radioButton1Key2.isChecked()) {
            return "1";
        } else if (radioButton2Key2.isChecked()) {
            return "2";
        } else if (radioButton3Key2.isChecked()) {
            return "3";
        } else if (radioButton4Key2.isChecked()) {
            return "complementos 1";
        } else if (radioButton5Key2.isChecked()) {
            return "complementos 2";
        } else if (radioButton6Key2.isChecked()) {
            return "complementos 3";
        } else if (radioButton1Cutter.isChecked()) {
            return "1";
        } else if (radioButton2Cutter.isChecked()) {
            return "2";
        } else if (radioButton3Cutter.isChecked()) {
            return "3";
        } else if (radioButton4Cutter.isChecked()) {
            return "4";
        } else if (radioButton5Cutter.isChecked()) {
            return "5";
        } else
            return "";
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout coreItemView;
        private TextView title;
        private TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.core_title);
            description = itemView.findViewById(R.id.core_description);
            coreItemView = itemView.findViewById(R.id.coreItemViewId);


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
}
