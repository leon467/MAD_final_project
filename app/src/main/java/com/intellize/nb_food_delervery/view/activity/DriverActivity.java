package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.entity.Driver;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.view.adapter.DriverAdapter;
import com.intellize.nb_food_delervery.viewmodel.DriverViewModel;

import java.util.List;

public class DriverActivity extends AppCompatActivity {

    private DriverViewModel viewModel;
    private EditText txt_name, txt_contact, txt_vehicle;
    private RecyclerView recyclerView;
    private Dialog loadingDialog;
    private DriverAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        getSupportActionBar().setTitle("Drivers");

        viewModel = new ViewModelProvider(this).get(DriverViewModel.class);

        recyclerView = findViewById(R.id.rv_drivers);
        adapter = new DriverAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        loadingDialog = Utils.getLoadingDialog(this);

        txt_name = findViewById(R.id.txt_name);
        txt_contact = findViewById(R.id.txt_phone_no);
        txt_vehicle = findViewById(R.id.txt_vehicle);

        findViewById(R.id.ll_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(DriverActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        viewModel.driverList.observe(this, new Observer<List<Driver>>() {
            @Override
            public void onChanged(List<Driver> drivers) {
                adapter.addAll(drivers);
            }
        });


        viewModel.onSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    txt_name.setText("");
                    txt_contact.setText("");
                    txt_vehicle.setText("");
                    viewModel.getDriver();
                }
            }
        });

        viewModel.onLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.e("log","is loading "+aBoolean);
                    loadingDialog.show();
                } else {
                    Utils.dismissDialog(loadingDialog);
                }
            }
        });

        viewModel.getDriver();

    }


    private void validate() {
        if (!Utils.validateField(txt_name, "Enter name")) {
            return;
        } else if (!Utils.validateField(txt_contact, "Enter phone number}")) {
            return;
        } else if (!Utils.validateField(txt_vehicle, "Enter vehicle number")) {
            return;
        } else {
            Driver driver = new Driver();
            driver.setName(txt_name.getText().toString());
            driver.setContactNo(txt_contact.getText().toString());
            driver.setVehicleNo(txt_vehicle.getText().toString());
            viewModel.saveDriver(driver);
        }
    }
}