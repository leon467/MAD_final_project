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

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.viewmodel.LoginViewModel;
import com.intellize.nb_food_delervery.viewmodel.SignupViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private LoginViewModel viewModel;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loadingDialog = Utils.getLoadingDialog(this);


        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);

        findViewById(R.id.ll_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
            }
        });


        findViewById(R.id.ll_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

        viewModel.onSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(LoginActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }


    private void validateFields() {
        if (!Utils.validateField(username, "Enter username")) {
            return;
        } else if (!Utils.validateField(password, "Enter password")) {
            return;
        } else {
            //added fake lag for show loading bar
            viewModel.login(username.getText().toString(), password.getText().toString());

        }
    }
}