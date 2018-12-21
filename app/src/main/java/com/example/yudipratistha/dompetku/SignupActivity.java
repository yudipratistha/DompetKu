package com.example.yudipratistha.dompetku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;
import com.example.yudipratistha.dompetku.model.BuatTransaksi;
import com.example.yudipratistha.dompetku.model.Signup;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText etName, etEmail, etPassword, etC_Password;
    protected Button btn_signup;
    protected TextView btn_login;

    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        service = APIClient.getService();

        etName = findViewById(R.id.input_name);
        etEmail = findViewById(R.id.input_email);
        etPassword = findViewById(R.id.input_password);
        etC_Password = findViewById(R.id.c_password);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.link_login);
        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password= etPassword.getText().toString();
                String c_password = etC_Password.getText().toString();
                //);
                service.saveRegisterPost(name, email, password, c_password)
                        .enqueue(new Callback<Signup>() {
                            @Override
                            public void onResponse(Call<Signup> call, Response<Signup> response) {
                                if (response.isSuccessful()){
                                    DompetkuSqLite.getInstance(getApplicationContext()).insertKategoris(response.body().getId());
                                    Toast.makeText(SignupActivity.this,"Sukses",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignupActivity.this,"Terjadi Kesalahan Input",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Signup> call, Throwable t) {
                                Toast.makeText(SignupActivity.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            case R.id.link_login:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}