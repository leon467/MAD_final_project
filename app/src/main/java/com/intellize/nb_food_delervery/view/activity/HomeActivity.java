package com.intellize.nb_food_delervery.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;
import com.intellize.nb_food_delervery.model.Food;
import com.intellize.nb_food_delervery.model.User;
import com.intellize.nb_food_delervery.utility.ContainerType;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.view.adapter.FoodAdapter;
import com.intellize.nb_food_delervery.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FoodAdapter adapter;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout ll_empty;
    private HomeViewModel viewModel;
    private TextView lbl_total;
    private CardView viewCart;
    private static CurrentUser currentUser;
    private com.michaldrabik.tapbarmenulib.TapBarMenu menu;

    public static CurrentUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        HomeActivity.currentUser = currentUser;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewCart = findViewById(R.id.view_cart);
        lbl_total = findViewById(R.id.lbl_total);
        recyclerView = findViewById(R.id.rv_food);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        ll_empty = findViewById(R.id.ll_empty);
        menu = findViewById(R.id.tapBarMenu);

        adapter = new FoodAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        changeContainer(ContainerType.LOADING);

        menu.findViewById(R.id.img_driver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close();
                startActivity(new Intent(HomeActivity.this, DriverActivity.class));
            }
        });

        menu.findViewById(R.id.img_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close();
                startActivity(new Intent(HomeActivity.this, CardActivity.class));
            }
        });

        menu.findViewById(R.id.img_past_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close();
                startActivity(new Intent(HomeActivity.this, MyOrdersActivity.class));
            }
        });

        menu.findViewById(R.id.img_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                menu.close();
                new AlertDialog.Builder(HomeActivity.this).setTitle("Logout")
                        .setMessage("Are you sure logout ?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.logout();
                        finish();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    }
                }).show();
            }
        });

        viewModel.getCartTotal(this);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });


        //add fake lag to show the loading animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.loadFoods();
            }
        }, 500);

        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(HomeActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        viewModel.foodList.observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                if (foodList.isEmpty()) {
                    changeContainer(ContainerType.EMPTY);
                } else {
                    adapter.addAll(foodList);
                    changeContainer(ContainerType.RV);
                }
            }
        });


        viewModel.onLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                changeContainer(aBoolean ? ContainerType.LOADING : ContainerType.STOP_LOADING);
            }
        });

        viewModel.cartTotal.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                lbl_total.setText(aDouble == 0 ? "" : ("LKR " + Utils.getDecimal(aDouble)));
                viewCart.setVisibility(aDouble == 0 ? View.GONE : View.VISIBLE);
            }
        });


        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });


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
        viewModel.loadFoods();
    }
}