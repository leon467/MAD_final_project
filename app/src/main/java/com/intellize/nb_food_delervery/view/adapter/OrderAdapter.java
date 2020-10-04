package com.intellize.nb_food_delervery.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.model.Order;
import com.intellize.nb_food_delervery.utility.Constant;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.view.activity.OrderRatingActivity;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private static final String TAG = "MultipleStopAdapter";
    private List<Order> orderList;
    private Activity activity;
    private LocalDatabase localDatabase;


    public OrderAdapter(Activity activity) {
        this.activity = activity;
        orderList = new ArrayList<>();
        localDatabase = LocalDatabase.getInstance(activity);

    }

    public void addAll(List<Order> cartList) {
        this.orderList.clear();
        this.orderList.addAll(cartList);
        notifyDataSetChanged();
    }

    public void clear() {
        orderList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Order order = orderList.get(position);

        holder.lbl_order_id.setText("Order ID : " + order.getOrderId());
        holder.lbl_price.setText("LKR. " + Utils.getDecimal(order.getTotalAmount()));
        holder.lbl_date.setText(order.getDate());
        holder.lbl_driver.setText(order.getDriver().getName());

        holder.ratings.setVisibility(order.isRated() ? View.VISIBLE : View.GONE);
        holder.lbl_rate.setText(order.isRated() ? "Show Review" : "Rate Order");


        if (order.isRated()) {
            holder.ratings.setRating((float) order.getRating());
        }

        holder.ll_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.isRated()) {
                    new AlertDialog.Builder(activity).setTitle("Review").setMessage(order.getReview()).setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                } else {
                    Intent intent = new Intent(activity, OrderRatingActivity.class);
                    intent.putExtra(Constant.BUNDLE_ORDER, new Gson().toJson(order));
                    activity.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lbl_order_id, lbl_price, lbl_date, lbl_driver, lbl_rate;
        private LinearLayout ll_rating;
        private com.willy.ratingbar.ScaleRatingBar ratings;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lbl_order_id = itemView.findViewById(R.id.lbl_order_id);
            lbl_price = itemView.findViewById(R.id.lbl_amount);
            lbl_date = itemView.findViewById(R.id.lbl_date);
            lbl_driver = itemView.findViewById(R.id.lbl_driver);
            ll_rating = itemView.findViewById(R.id.ll_rate);
            lbl_rate = itemView.findViewById(R.id.lbl_rate);
            ratings = itemView.findViewById(R.id.rating_bar);

        }
    }
}
