package com.example.yudipratistha.dompetku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;
import com.example.yudipratistha.dompetku.model.LihatKategori;
import com.example.yudipratistha.dompetku.model.LihatTransaksi;
import com.example.yudipratistha.dompetku.model.User;
import com.example.yudipratistha.dompetku.model.UserLogin;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText etEmail, etName, etPassword;
    protected Button btn_login;
    protected TextView btn_daftar;
    protected static int isSuccess = 0;
    protected static int id_user;
    protected static String email_user;
    protected static String name_user;
    protected static String created_at_user;
    protected static String updated_at_user;
    private User profile;

    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = APIClient.getService();

        etEmail = findViewById(R.id.input_email);
        etName = findViewById(R.id.input_name);
        etPassword = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        btn_daftar = findViewById(R.id.link_signup);

        SharedPreferences sharedPref = getSharedPreferences("dataPengguna", Context.MODE_PRIVATE);

        if (sharedPref.contains("token")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btn_login.setOnClickListener(this);
        btn_daftar.setOnClickListener(this);
        service = APIClient.getService();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                service.loginPost(etEmail.getText().toString(), etPassword.getText().toString())
                        .enqueue(new Callback<UserLogin>() {
                            @Override
                            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Sukses", Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPref = getSharedPreferences("dataPengguna", Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("email_user", etEmail.getText().toString());
                                    editor.putString("nama_user", etPassword.getText().toString());
                                    editor.putInt("id", response.body().getDataPengguna().getId());
                                    editor.putBoolean("status_login", response.body().isStatus());
                                    editor.apply();
                                    id_user = response.body().getDataPengguna().getId();
                                    email_user = response.body().getDataPengguna().getEmail();
                                    name_user = response.body().getDataPengguna().getName();
                                    created_at_user = response.body().getDataPengguna().getCreatedAt();
                                    updated_at_user = response.body().getDataPengguna().getUpdatedAt();
                                    profile = new User();
                                    profile.setId(id_user);
                                    profile.setCreatedAt(created_at_user);
                                    profile.setUpdatedAt(updated_at_user);
                                    profile.setName(name_user);
                                    profile.setEmail(email_user);
//
                                    insertLogin();
                                    insertKategori();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Gagal ada masalah pengisian", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserLogin> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            case R.id.link_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
        }


    }
    
    private void insertLogin(){
        DompetkuSqLite.getInstance(getApplicationContext()).emptyProfiles();
        DompetkuSqLite.getInstance(getApplicationContext()).emptyTransaksi();
        DompetkuSqLite.getInstance(getApplicationContext()).emptyKategori();
        DompetkuSqLite.getInstance(getApplicationContext()).insertProfileId(profile);
        service.lihatTransaksi(id_user)
                .enqueue(new Callback<LihatTransaksi>() {
                    @Override
                    public void onResponse(Call<LihatTransaksi> call, Response<LihatTransaksi> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sukses insert", Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).insertTransaksiLogin(response.body().getLihatTransaksi());


                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal insert data", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LihatTransaksi> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Koneksi gagal", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void insertKategori(){
        service.lihatKategori()
                .enqueue(new Callback<LihatKategori>() {
                    @Override
                    public void onResponse(Call<LihatKategori> call, Response<LihatKategori> response) {
                        if (response.isSuccessful()) {
//                            Log.d("kat", String.valueOf(response.body().getLihatKategori()));
                            Toast.makeText(LoginActivity.this, "Sukses kategori", Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).insertKategoriLogin(response.body().getLihatKategori());
                            Intent tampilanUtama = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(tampilanUtama);
                            finish();


                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal insert data", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LihatKategori> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Koneksi gagal", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
