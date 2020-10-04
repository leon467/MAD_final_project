package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.utility.AlertType;
import com.intellize.nb_food_delervery.utility.Constant;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.viewmodel.OrderRateViewModel;

public class OrderRatingActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private com.willy.ratingbar.ScaleRatingBar ratings;
    private EditText review;
    private OrderRateViewModel viewModel;
    private Order order;
    private Dialog loadingDialog;
    private LinearLayout ll_rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rating);


        viewModel = new ViewModelProvider(this).get(OrderRateViewModel.class);
        animationView = findViewById(R.id.animation_view);
        ratings = findViewById(R.id.rating_bar);
        review = findViewById(R.id.txt_review);
        ll_rate = findViewById(R.id.ll_rate);
        loadingDialog = Utils.getLoadingDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (Utils.isNotEmptyString(extras.getString(Constant.BUNDLE_ORDER))) {
                order = new Gson().fromJson(extras.getString(Constant.BUNDLE_ORDER), Order.class);
            } else {
                Utils.showMessage(this, AlertType.TOAST, "Something went wrong !");
                finish();
            }
        }
        ll_rate.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_rate.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);

            }
        }, 2000);

        findViewById(R.id.ll_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order == null) {
                    Utils.showMessage(OrderRatingActivity.this, AlertType.TOAST, "Something went wrong !");
                    finish();
                } else {
                    validate();
                }
            }
        });

        viewModel.onSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                    Utils.showMessage(OrderRatingActivity.this,AlertType.TOAST,"Successful !");
                }else {
                    Utils.showMessage(OrderRatingActivity.this,AlertType.TOAST,"Failed !");
                }
            }
        });

        viewModel.onLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.e("log", "is loading " + aBoolean);
                    loadingDialog.show();
                } else {
                    Utils.dismissDialog(loadingDialog);
                }
            }
        });

        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(OrderRatingActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }


    private void validate() {
        if (ratings.getRating() == 0) {
            Utils.showMessage(this, AlertType.ERROR, "Select rating count !");
            return;
        } else if (!Utils.validateField(review, "Enter your review")) {
            return;
        } else {
            order.setRated(true);
            order.setRating(ratings.getRating());
            order.setReview(review.getText().toString());
            viewModel.submit(order);
        }
    }
}