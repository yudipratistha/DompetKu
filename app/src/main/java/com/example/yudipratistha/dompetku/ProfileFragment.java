package com.example.yudipratistha.dompetku;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;
import com.example.yudipratistha.dompetku.model.LihatTransaksi;
import com.example.yudipratistha.dompetku.model.User;
import com.example.yudipratistha.dompetku.model.UserLogin;
import com.example.yudipratistha.dompetku.model.UserUpdate;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    public User profile;
    private EditText name_input;
    private EditText input_email;
    private Button logout_button;
    private Button aturkategori;
    private Button aturpengingat;
    APIService service;

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.app_bar_text);
        TextView text_judul = getActivity().findViewById(R.id.text_judul);
        text_judul.setText(R.string.profil);
        service = APIClient.getService();

        profile = DompetkuSqLite.getInstance(getActivity()).getProfile();
        name_input = getActivity().findViewById(R.id.name_input);
        input_email = getActivity().findViewById(R.id.input_email);

        name_input.setOnFocusChangeListener(new FocusChangeListener());

        populateData(profile);

        logout_button = getActivity().findViewById(R.id.btn_logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("dataPengguna", Context.MODE_PRIVATE);
                int id_user = preferences.getInt("id",0);
                service.lihatTransaksi(id_user)
                        .enqueue(new Callback<LihatTransaksi>() {
                            @Override
                            public void onResponse(Call<LihatTransaksi> call, Response<LihatTransaksi> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_LONG).show();
                                    SharedPreferences preferences = getActivity().getSharedPreferences("dataPengguna", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent loginactivity = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                                    startActivity(loginactivity);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Can't Connect to Server", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LihatTransaksi> call, Throwable t) {
                                Toast.makeText(getActivity(), "Can't Connect to Server" + t, Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

//        aturkategori = getActivity().findViewById(R.id.btn_aturkategori);
//        aturkategori.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), KategoriActivity.class);
//                startActivity(intent);
//            }
//        });
//        aturpengingat = getActivity().findViewById(R.id.btn_aturpengingat);
//        aturpengingat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), PengingatActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void populateData(User profile){
        if(profile.getName()!=null) {
            name_input.setText(profile.getName());
        }
        if(profile.getEmail()!=null) {
            input_email.setText(profile.getEmail());
        }
    }

    class FocusChangeListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View view, boolean b) {
            if(!b) {
                boolean changed = false;
                if(!name_input.getText().toString().equals(profile.getName())) {
                    profile.setName(name_input.getText().toString());
                    changed=true;
                }
                if(changed) {
                    Log.e("Update", String.valueOf(profile));
                    service.updateUser(profile.getId(), profile.getName())
                            .enqueue(new Callback<UserUpdate>() {
                                @Override
                                public void onResponse(Call<UserUpdate> call, Response<UserUpdate> response) {
                                    if (response.isSuccessful()) {
//                                        Toast.makeText(getContext(), "Sukses update profil", Toast.LENGTH_LONG).show();

                                    } else {
//                                        Toast.makeText(getContext(), "Gagal update profil", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserUpdate> call, Throwable t) {
                                    Toast.makeText(getContext(), "Koneksi gagal", Toast.LENGTH_LONG).show();
                                }
                            });
                    DompetkuSqLite.getInstance(getContext()).editProfile(profile);
                }
            }
        }
    }

}
