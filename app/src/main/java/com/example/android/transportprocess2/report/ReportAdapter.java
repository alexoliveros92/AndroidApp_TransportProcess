package com.example.android.transportprocess2.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.transportprocess2.R;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Report> reports;

    public ReportAdapter(Context context, ArrayList<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_recycler_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.MyViewHolder holder, int position) {
        Report current = reports.get(position);
        holder.titleR.setText(current.getTitleR());
        holder.lineR.setText(current.getLineR());
        holder.timeRO.setText(current.getTimeOR());
        holder.timeRD.setText(current.getTimeDR());
        holder.dateR.setText(current.getDateR());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleR;
        private TextView timeRO;
        private TextView timeRD;
        private TextView lineR;
        private TextView dateR;
        private RelativeLayout layoutReport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleR = itemView.findViewById(R.id.report_title);
            timeRO = itemView.findViewById(R.id.report_timeO);
            timeRD = itemView.findViewById(R.id.report_timeD);
            lineR = itemView.findViewById(R.id.report_line);
            dateR = itemView.findViewById(R.id.report_date);
            layoutReport = itemView.findViewById(R.id.report_container);

        }
    }
}
