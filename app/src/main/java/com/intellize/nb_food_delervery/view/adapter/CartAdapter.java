package com.intellize.nb_food_delervery.view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.database.entity.Cart;
import com.intellize.nb_food_delervery.model.Food;
import com.intellize.nb_food_delervery.utility.Utils;

import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private static final String TAG = "MultipleStopAdapter";
    private List<Cart> cartList;
    private Activity activity;
    private LocalDatabase localDatabase;


    public CartAdapter(Activity activity) {
        this.activity = activity;
        cartList = new ArrayList<>();
        localDatabase = LocalDatabase.getInstance(activity);

    }

    public void addAll(List<Cart> cartList) {
        this.cartList.clear();
        this.cartList.addAll(cartList);
        notifyDataSetChanged();
    }

    public void clear() {
        cartList.clear();
        notifyDataSetChanged();
    }

    public List<Cart> getList() {

//        List<Food> foods = new ArrayList<>();
//        for (Cart cart : cartList){
//            foods.add(cart.getFood());
//        }
        return cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Cart cart = cartList.get(position);

        holder.lbl_name.setText(cart.getFood().getName());
        holder.lbl_price.setText("LKR. " + Utils.getDecimal(cart.getValue()));
        Glide.with(activity)
                .load(cart.getFood().getImage())
                .placeholder(R.drawable.food_placeholder)
                .centerCrop()
                .error(R.drawable.food_placeholder)
                .override(200, 100)
                .into(holder.img_cover);


        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localDatabase.cartDao().delete(cart.getPrimaryKey());
                cartList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.elegantQty.setNumber(cart.getQty() + "");

        holder.elegantQty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(position);
                Log.e("adap", "changed " + newValue);
                Log.e("id", "changed " + cart.getPrimaryKey());
                cart.setQty(newValue);
                cart.setValue(newValue * cart.getFood().getPrice());
                localDatabase.cartDao().update(cart);
                //         notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_cover, img_delete;
        private TextView lbl_name, lbl_price;
        private ElegantNumberButton elegantQty;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_cover = itemView.findViewById(R.id.img_cover);
            lbl_name = itemView.findViewById(R.id.lbl_name);
            lbl_price = itemView.findViewById(R.id.lbl_amount);
            img_delete = itemView.findViewById(R.id.img_delete);
            elegantQty = itemView.findViewById(R.id.changeQty);


        }
    }
}
