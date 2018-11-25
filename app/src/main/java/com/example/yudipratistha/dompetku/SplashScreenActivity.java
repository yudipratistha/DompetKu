package com.example.yudipratistha.dompetku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("dataPengguna", MODE_PRIVATE);
                Boolean status_login = sharedPreferences.getBoolean("status_login", false);

                if (status_login == true){
                    Intent tampilanUtama = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(tampilanUtama);
                    finish();
                }else{
                    Intent tampilanLogin = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(tampilanLogin);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);


    }
}
