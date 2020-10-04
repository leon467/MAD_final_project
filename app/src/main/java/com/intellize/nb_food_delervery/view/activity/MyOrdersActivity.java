package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.utility.ContainerType;
import com.intellize.nb_food_delervery.view.adapter.OrderAdapter;
import com.intellize.nb_food_delervery.viewmodel.MyOrdersViewModel;

import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {


    private MyOrdersViewModel viewModel;
    private OrderAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout ll_empty;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        getSupportActionBar().setTitle("My Orders");

        viewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);

        recyclerView = findViewById(R.id.rv_order);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        ll_empty = findViewById(R.id.ll_empty);


        adapter = new OrderAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        changeContainer(ContainerType.LOADING);

        viewModel.orderList.observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if (orders == null || orders.isEmpty()) {
                    changeContainer(ContainerType.EMPTY);
                } else {
                    adapter.addAll(orders);
                    changeContainer(ContainerType.RV);
                }
            }
        });


        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                changeContainer(ContainerType.STOP_LOADING);
                changeContainer(ContainerType.EMPTY);
                new AlertDialog.Builder(MyOrdersActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        viewModel.loadOrders();


    }


    private void changeContainer(ContainerType type) {
        if (type == ContainerType.LOADING) {
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            ll_empty.setVisibility(View.GONE);
        } else if (type == ContainerType.RV) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        } else if (type == ContainerType.EMPTY) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
        } else if (type == ContainerType.STOP_LOADING) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadOrders();
    }
}