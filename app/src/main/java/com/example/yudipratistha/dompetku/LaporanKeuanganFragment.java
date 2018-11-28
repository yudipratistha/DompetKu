package com.example.yudipratistha.dompetku;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yudipratistha.dompetku.model.DataPengguna;
import com.example.yudipratistha.dompetku.model.UserLogin;

import java.util.Calendar;


public class LaporanKeuanganFragment extends Fragment {
    public DataPengguna profile;
    private Calendar birthdayCalendar = Calendar.getInstance();
    private ImageView profile_pict;
    private EditText name_input;
    private EditText move_minute_input;
    private EditText move_distance_input;
    private EditText gender_input;
    private EditText birthday_input;
    private EditText weight_input;
    private EditText height_input;

    public LaporanKeuanganFragment() {}

    public static LaporanKeuanganFragment newInstance() {
        LaporanKeuanganFragment fragment = new LaporanKeuanganFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_transaksi, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getActivity().setTitle("Profile");
//
////        profile = TriathlogDbHelper.getInstance(getActivity()).getProfile(1);
//        profile = new DataPengguna();
//        profile.setId(1);
//        profile.setCreatedAt("2018-06-30");
//        profile.setUpdatedAt("2018-06-31");
//        profile.setName("Yudi");
//        profile.setEmail("yudipratistha@gmail.com");
//
//        name_input = getActivity().findViewById(R.id.name_input);
//        move_minute_input = getActivity().findViewById(R.id.move_minute_input);
////        move_distance_input = getActivity().findViewById(R.id.move_distance_input);
//        gender_input = getActivity().findViewById(R.id.gender_input);
//        birthday_input = getActivity().findViewById(R.id.birthday_input);
////        weight_input = getActivity().findViewById(R.id.weight_input);
////        height_input = getActivity().findViewById(R.id.height_input);
//        profile_pict = getActivity().findViewById(R.id.profile_pict);
//
//        //Glide.with(this).load(R.drawable.profile).into(profile_pict);
//
//        name_input.setOnFocusChangeListener(new FocusChangeListener());
////        move_minute_input.setOnFocusChangeListener(new FocusChangeListener());
////        move_distance_input.setOnFocusChangeListener(new FocusChangeListener());
//        gender_input.setOnFocusChangeListener(new FocusChangeListener());
//        birthday_input.setOnFocusChangeListener(new FocusChangeListener());
////        weight_input.setOnFocusChangeListener(new FocusChangeListener());
////        height_input.setOnFocusChangeListener(new FocusChangeListener());
//
//        populateData(profile);
//    }
//
//    private void populateData(DataPengguna profile){
//        if(profile.getId()!=0) {
//            name_input.setText(String.valueOf(profile.getId()));
//        }
////        if(profile.getName()!=null) {
////            move_minute_input.setText(profile.getName());
////        }
////        if(profile.getEmail()!=null) {
////            move_distance_input.setText(profile.getEmail());
////        }
////        if(profile.getCreatedAt()!=null) {
////            gender_input.setText(profile.getCreatedAt());
////        }
//    }
//
//    class FocusChangeListener implements View.OnFocusChangeListener{
//        @Override
//        public void onFocusChange(View view, boolean b) {
//            if(!b) {
//                boolean changed = false;
//                if(!name_input.getText().toString().equals("")) {
//                    if (Integer.parseInt(name_input.getText().toString()) != profile.getId()) {
//                        profile.setId(Integer.parseInt(name_input.getText().toString()));
//                        changed = true;
//                    }
//                }
//                if(!move_minute_input.getText().toString().equals(profile.getName())) {
//                    profile.setName(move_minute_input.getText().toString());
//                    changed=true;
//                }
//                if(!move_distance_input.getText().toString().equals(profile.getEmail())) {
//                    profile.setEmail(move_distance_input.getText().toString());
//                    changed=true;
//                }
//
//                if(!gender_input.getText().toString().equals(profile.getCreatedAt())) {
//                    profile.setCreatedAt(gender_input.getText().toString());
//                    changed=true;
//                }
//
//                if(changed) {
//                    //TriathlogDbHelper.getInstance(getActivity()).editProfile(profile);
//                }
//            }
//        }
//    }

}
