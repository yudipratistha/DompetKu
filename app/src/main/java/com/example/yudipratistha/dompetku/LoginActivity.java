package com.example.yudipratistha.dompetku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;
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
    private SharedPreferences.Editor profile;
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

                                    profile = sharedPref.edit();
                                    profile.putString("email_user", etEmail.getText().toString());
                                    profile.putString("nama_user", etPassword.getText().toString());
                                    profile.putInt("id", response.body().getDataPengguna().getId());
                                    profile.putBoolean("status_login", response.body().isStatus());
                                    profile.apply();
                                    inserLogin();
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Gagal ada masalah pengisian", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserLogin> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Gagal" + t, Toast.LENGTH_LONG).show();
                            }
                        });


//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.link_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
                break;
        }


    }
    
    private void inserLogin(){
        DompetkuSqLite.getInstance(getApplicationContext()).emptyProfiles();
        DompetkuSqLite.getInstance(getApplicationContext()).emptyTransaksi();
        DompetkuSqLite.getInstance(getApplicationContext()).insertProfileId((User) profile);
        service.lihatTransaksi(((User) profile).getId())
                .enqueue(new Callback<LihatTransaksi>() {
                    @Override
                    public void onResponse(Call<LihatTransaksi> call, Response<LihatTransaksi> response) {
                        if (response.isSuccessful() && response.body().getLihatTransaksi().size() > 0) {
                            Toast.makeText(LoginActivity.this, "Sukses", Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).insertTransaksiLogin(response.body().getLihatTransaksi());
                            Intent tampilanUtama = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(tampilanUtama);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal insert task", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LihatTransaksi> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Gagal konek task" + t, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
