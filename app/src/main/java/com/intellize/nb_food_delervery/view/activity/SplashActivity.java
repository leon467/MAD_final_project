package com.intellize.nb_food_delervery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.stetho.Stetho;
import com.intellize.nb_food_delervery.R;
import com.intellize.nb_food_delervery.database.entity.CurrentUser;
import com.intellize.nb_food_delervery.viewmodel.BaseViewModel;
import com.intellize.nb_food_delervery.viewmodel.SignupViewModel;

public class SplashActivity extends AppCompatActivity {


    private BaseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Stetho.initializeWithDefaults(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(BaseViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        }, 3000);


    }


    private void next() {
        CurrentUser currentUser = viewModel.getCurrentUser();
        if (currentUser == null) {
            finish();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } else {
            HomeActivity.setCurrentUser(currentUser);
            finish();
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }
    }
}