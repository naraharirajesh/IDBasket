package com.IDB.idbasket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean isUserLoggedIn = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this).getBoolean("isMobileLoggedIn",false);
                if(isUserLoggedIn){
                    finish();
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                }else{
                    finish();
                    startActivity(new Intent(SplashScreen.this,LoginActicity.class));
                }
            }
        },300);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
