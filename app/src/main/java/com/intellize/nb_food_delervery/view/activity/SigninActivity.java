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
import android.view.View;
import android.widget.EditText;

import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.LocalDatabase;
import com.intellize.nb_food_delervery.model.User;
import com.intellize.nb_food_delervery.utility.Utils;
import com.intellize.nb_food_delervery.viewmodel.SignupViewModel;

public class SigninActivity extends AppCompatActivity {

    private EditText txt_fName, txt_lName, txt_phoneNo, txt_address, txt_email, txt_password;
    private SignupViewModel viewModel;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().hide();
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        loadingDialog = Utils.getLoadingDialog(this);

        txt_fName = findViewById(R.id.txt_fname);
        txt_lName = findViewById(R.id.txt_lname);
        txt_phoneNo = findViewById(R.id.txt_phone_no);
        txt_address = findViewById(R.id.txt_address);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);


        findViewById(R.id.ll_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });


        viewModel.onSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    HomeActivity.setCurrentUser(LocalDatabase.getInstance(SigninActivity.this).currentUserDao().getUser());
                    finish();
                    startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                }
            }
        });

        viewModel.onLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    loadingDialog.show();
                } else {
                    Utils.dismissDialog(loadingDialog);
                }
            }
        });

        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(SigninActivity.this).setTitle("Error").setMessage(s).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        Utils.dismissDialog(loadingDialog);
        super.onDestroy();
    }

    private void validateFields() {

        if (!Utils.validateField(txt_fName, "Enter first name")) {
            return;
        } else if (!Utils.validateField(txt_lName, "Enter last name")) {
            return;
        } else if (!Utils.validateField(txt_phoneNo, "Enter phone number")) {
            return;
        } else if (!Utils.validateField(txt_address, "Enter address")) {
            return;
        } else if (!Utils.validateField(txt_email, "Enter email address")) {
            return;
        } else if (!Utils.validateField(txt_password, "Enter password")) {
            return;
        } else {
            final User user = new User(txt_fName.getText().toString(),
                    txt_lName.getText().toString(),
                    txt_phoneNo.getText().toString(),
                    txt_address.getText().toString(),
                    txt_email.getText().toString()
                    , txt_password.getText().toString());
            viewModel.signup(user);
        }
    }
}