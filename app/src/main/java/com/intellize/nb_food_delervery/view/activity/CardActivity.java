package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.entity.Card;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.view.adapter.CardAdapter;
import com.intellize.nb_food_delervery.viewmodel.CardViewModel;

import java.util.List;

public class CardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private EditText txt_name, txt_cardNumber, txt_bank;
    private CardViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setTitle("Debit Cards");
        viewModel = new ViewModelProvider(this).get(CardViewModel.class);

        recyclerView = findViewById(R.id.rv_cards);
        txt_name = findViewById(R.id.txt_name);
        txt_cardNumber = findViewById(R.id.txt_card_no);
        txt_bank = findViewById(R.id.txt_bank);


        adapter = new CardAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.ll_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        viewModel.cardList.observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {

                txt_bank.setText("");
                txt_cardNumber.setText("");
                txt_name.setText("");

                if (cards != null) {
                    adapter.addAll(cards);
                }

            }
        });

        viewModel.loadCards(this);


    }


    private void validate() {

        if (!Utils.validateField(txt_name, "Enter card name")) {
            return;
        } else if (!Utils.validateField(txt_cardNumber, "Enter card number")) {
            return;
        } else if (!Utils.validateField(txt_bank, "Enter bank name")) {
            return;
        } else {
            Card card = new Card(txt_name.getText().toString(), txt_cardNumber.getText().toString(), txt_bank.getText().toString());
            viewModel.saveCard(card);
        }
    }
}