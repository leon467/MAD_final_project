package com.intellize.nb_food_delervery.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.Driver;
import com.intellize.nb_food_delervery.model.Food;

import java.util.ArrayList;
import java.util.List;


public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private static final String TAG = "MultipleStopAdapter";
    private List<Driver> drivers;
    private Activity activity;
    private LocalDatabase localDatabase;


    public DriverAdapter(Activity activity) {
        this.activity = activity;
        drivers = new ArrayList<>();
        localDatabase = LocalDatabase.getInstance(activity);

    }

    public void addAll(List<Driver> drivers) {
        this.drivers.clear();
        this.drivers.addAll(drivers);
        notifyDataSetChanged();
    }

    public void clear() {
        drivers.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_driver_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Driver driver = drivers.get(position);
        holder.lbl_name.setText(driver.getName());
        holder.lbl_phone.setText(driver.getContactNo());
        holder.lbl_vehicle.setText(driver.getVehicleNo());


    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lbl_name, lbl_phone, lbl_vehicle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            lbl_name = itemView.findViewById(R.id.lbl_name);
            lbl_phone = itemView.findViewById(R.id.lbl_phone_number);
            lbl_vehicle = itemView.findViewById(R.id.lbl_vehicle);


        }
    }
}
