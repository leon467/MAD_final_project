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
import com.intellize.nb_food_delervery.database.entity.Card;
import com.intellize.nb_food_delervery.database.entity.Driver;

import java.util.ArrayList;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private static final String TAG = "MultipleStopAdapter";
    private List<Card> cardList;
    private Activity activity;
    private LocalDatabase localDatabase;


    public CardAdapter(Activity activity) {
        this.activity = activity;
        cardList = new ArrayList<>();
        localDatabase = LocalDatabase.getInstance(activity);

    }

    public void addAll(List<Card> drivers) {
        this.cardList.clear();
        this.cardList.addAll(drivers);
        notifyDataSetChanged();
    }

    public void clear() {
        cardList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Card card = cardList.get(position);
        holder.lbl_name.setText(card.getName());
        holder.lbl_card_number.setText(card.getCardNumber());
        holder.lbl_bank.setText(card.getBank());


    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lbl_name, lbl_card_number, lbl_bank;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            lbl_name = itemView.findViewById(R.id.lbl_name);
            lbl_card_number = itemView.findViewById(R.id.lbl_card_number);
            lbl_bank = itemView.findViewById(R.id.lbl_bank);


        }
    }
}
