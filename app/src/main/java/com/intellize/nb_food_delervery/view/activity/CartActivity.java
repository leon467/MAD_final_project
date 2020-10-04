package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.database.entity.Driver;
import com.intellize.nb_food_delervery.model.CartUI;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.utility.AlertType;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.view.adapter.CartAdapter;
import com.intellize.nb_food_delervery.view.adapter.FoodAdapter;
import com.intellize.nb_food_delervery.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout ll_empty, ll_main;
    private CartViewModel viewModel;
    private RadioGroup paymentMethod;
    private TextView lbl_qty, lbl_amount;
    private double totalValue;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        viewModel = new ViewModelProvider(this).get(CartViewModel.class);


        paymentMethod = findViewById(R.id.radio_group);
        ll_empty = findViewById(R.id.ll_empty);
        ll_main = findViewById(R.id.ll_main);
        recyclerView = findViewById(R.id.rv_cart_items);
        lbl_qty = findViewById(R.id.lbl_total_qty);
        lbl_amount = findViewById(R.id.lbl_total_amount);

        loadingDialog = Utils.getLoadingDialog(this);


        adapter = new CartAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.cart.observe(this, new Observer<CartUI>() {
            @Override
            public void onChanged(CartUI cartUI) {
                if (cartUI.getCartList() == null || cartUI.getCartList().isEmpty()) {
                    ll_empty.setVisibility(View.VISIBLE);
                    ll_main.setVisibility(View.GONE);


                } else {
                    ll_empty.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);
                    adapter.addAll(cartUI.getCartList());
                    lbl_qty.setText(cartUI.getQty() + "");
                    lbl_amount.setText("LKR. " + Utils.getDecimal(cartUI.getAmount()));
                    totalValue = cartUI.getAmount();
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


        findViewById(R.id.ll_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
            }
        });


        viewModel.driver.observe(this, new Observer<Driver>() {
            @Override
            public void onChanged(Driver driver) {
                if (driver != null) {

                    Order order = new Order();
                    order.setDriver(driver);
                    order.setFoodList(adapter.getList());
                    order.setTotalAmount(totalValue);
                    order.setUser(HomeActivity.getCurrentUser().getUser());
                    order.setPaymentMethod(paymentMethod.getCheckedRadioButtonId() == 0 ? "Cash on delivery" : "Card payment");
                    order.setRated(false);
                    viewModel.checkout(order);

                }
            }
        });


        viewModel.onSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.clearCart();
                    new AlertDialog.Builder(CartActivity.this).setTitle("Success !").
                            setMessage("Your order successfully placed !").setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                            startActivity(new Intent(CartActivity.this, MyOrdersActivity.class));

                        }
                    }).setNegativeButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            finish();
                            startActivity(new Intent(CartActivity.this, HomeActivity.class));
                        }
                    }).show();
                }
            }
        });


        viewModel.loadItems(this);

    }


    private void checkout() {
        if (paymentMethod.getCheckedRadioButtonId() == -1) {
            Utils.showMessage(this, AlertType.ERROR, "Select a payment method !");
            return;
        } else {

            viewModel.getDriver(this);

        }


    }
}