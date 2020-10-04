package com.intellize.nb_food_delervery.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.model.Food;
import com.intellize.nb_food_delervery.utility.Utils;

import java.util.ArrayList;
import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private static final String TAG = "MultipleStopAdapter";
    private List<Food> foodList;
    private Activity activity;
    private LocalDatabase localDatabase;


    public FoodAdapter(Activity activity) {
        this.activity = activity;
        foodList = new ArrayList<>();
        localDatabase = LocalDatabase.getInstance(activity);

    }

    public void addAll(List<Food> foodList) {
        this.foodList.clear();
        this.foodList.addAll(foodList);
        notifyDataSetChanged();
    }

    public void clear() {
        foodList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_food_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Food food = foodList.get(position);
        holder.lbl_name.setText(food.getName());
        holder.lbl_price.setText("LKR. " + Utils.getDecimal(food.getPrice()));
        Glide.with(activity)
                .load(food.getImage())
                .placeholder(R.drawable.food_placeholder)
                .centerCrop()
                .error(R.drawable.food_placeholder)
                .override(400, 300)
                .into(holder.img_cover);

        if (localDatabase.cartDao().getCartItem(food.getId()) != null) {
            holder.ll_add_to_cart.setEnabled(false);
            holder.ll_add_to_cart.setBackground(activity.getDrawable(R.drawable.btn_second_style));
        } else {
            holder.ll_add_to_cart.setEnabled(true);
            holder.ll_add_to_cart.setBackground(activity.getDrawable(R.drawable.btn_style));
        }


        holder.ll_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart(food, food.getId(), 1, food.getPrice());
                localDatabase.cartDao().save(cart);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_add_to_cart;
        private ImageView img_cover;
        private TextView lbl_name, lbl_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ll_add_to_cart = itemView.findViewById(R.id.ll_add_to_cart);
            img_cover = itemView.findViewById(R.id.img_cover);
            lbl_name = itemView.findViewById(R.id.lbl_name);
            lbl_price = itemView.findViewById(R.id.lbl_amount);


        }
    }
}
