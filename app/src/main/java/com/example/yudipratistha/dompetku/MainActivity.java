package com.example.yudipratistha.dompetku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;



public class MainActivity extends AppCompatActivity {
    protected TextView nama_pengguna, email_pengguna;
    BottomNavigationView bottomNavigation;

    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = APIClient.getService();

//        nama_pengguna = findViewById(R.id.nama_pengguna);
//        email_pengguna =  findViewById(R.id.email_pengguna);

//        SharedPreferences sharedPref = getSharedPreferences("dataPengguna", Context.MODE_PRIVATE);
//        Boolean status_login = sharedPref.getBoolean("status_login",false);
//        String nama_user = sharedPref.getString("nama_user","");
//        String email_user = sharedPref.getString("email_user", "");
//
//        nama_pengguna.setText(nama_user);
//        email_pengguna.setText(email_user);

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

//        bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.transaksi:
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_container, TransactionFragment.newInstance(), TransactionFragment.class.getSimpleName())
//                                .commit();
//
//                        break;
//                    case R.id.historitransaksi:
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_container, HistoriTransaksiFragment.newInstance(), HistoriTransaksiFragment.class.getSimpleName())
//                                .commit();
//                        break;
//                    case R.id.profiluser:
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_container, ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName())
//                                .commit();
//                        break;
//                }
//                return true;
//            }
//
//        });
    }
//
//    @Override
//    protected void onResumeFragments() {
//        super.onResumeFragments();
//
//    }
}
