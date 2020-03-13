package com.auto.autoservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class AdapterAuto extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String ENTRY = "com.auto.auto.ENTRY";

    private Context context;
    private LayoutInflater inflater;
    List<Auto> data = Collections.emptyList();
    Auto current;
    int currentPos = 0;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterAuto(Context context, List<Auto> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        for (Auto auto : data) {
            Log.e("auto", auto.getMaintenanceType() + " " + auto.getPayment());
        }
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_auto, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Auto current = data.get(position);
        myHolder.textMakeModel.setText("Make model: " + current.getMakeModel());
        myHolder.textYear.setText("Year: " + current.getYear());
        myHolder.textMaintenanceType.setText("Maintenance type: " + current.getMaintenanceType());
        myHolder.textCity.setText("City: " + current.getCity());
        myHolder.textPayment.setText("Payment: " + current.getPayment());
        Log.e("auto", current.getMaintenanceType() + " " + current.getPayment());

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textMakeModel;
        TextView textYear;
        TextView textMaintenanceType;
        TextView textCity;
        TextView textPayment;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textMakeModel = (TextView) itemView.findViewById(R.id.textMakeModel);
            textYear = (TextView) itemView.findViewById(R.id.textYear);
            textMaintenanceType = (TextView) itemView.findViewById(R.id.textMaintenanceType);
            textCity = (TextView) itemView.findViewById(R.id.textCity);
            textPayment = (TextView) itemView.findViewById(R.id.textPayment);

            itemView.setOnClickListener(this);
        }

        // Click event for item
        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();

            Auto auto = data.get(itemPosition);

            Intent intent = new Intent(context, NewEntryActivity.class);
            intent.putExtra(ENTRY, auto);
            context.startActivity(intent);
        }

    }

}