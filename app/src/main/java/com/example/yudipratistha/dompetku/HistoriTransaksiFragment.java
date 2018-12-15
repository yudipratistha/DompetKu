package com.example.yudipratistha.dompetku;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.adapter.HistoriTransaksiAdapter;
import com.example.yudipratistha.dompetku.adapter.TransaksiAdapter;
import com.example.yudipratistha.dompetku.model.DataPengguna;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoriTransaksiFragment extends Fragment {
    private DataPengguna profile;
    private RecyclerView transaksi_list;
    Calendar myCalendar;
    Calendar myCalendar2;
    private Button filterstart;
    private Button filterend;
    private String filterStartStr;
    private String filterEndStr;
    private String tipeTrans;
    private HistoriTransaksiAdapter adapter;
    private List<LihatTransaksiItem> lihatTransaksiItems;


    public HistoriTransaksiFragment() {}

    public static HistoriTransaksiFragment newInstance() {
        HistoriTransaksiFragment fragment = new HistoriTransaksiFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_histori_transaksi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.app_bar_text);
        TextView text_judul = getActivity().findViewById(R.id.text_judul);
        text_judul.setText(R.string.historiTransaksi);

        TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
        transaksi_list = getActivity().findViewById(R.id.transaksi_list);
        transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transaksi_list.setAdapter(adapter);
        myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DATE, -7);
        myCalendar2 = Calendar.getInstance();
        filterstart = getActivity().findViewById(R.id.btn_date_start);
        filterend = getActivity().findViewById(R.id.btn_date_end);
        filterstart.setText(Util.calendarToString(myCalendar, "MMMM dd, yyyy"));
        filterStartStr = Util.calendarToString(myCalendar, "yyyy-MM-dd");
        filterend.setText(Util.calendarToString(myCalendar2, "MMMM dd, yyyy"));
        filterEndStr = Util.calendarToString(myCalendar2, "yyyy-MM-dd");
        historiTransaksi(filterStartStr, filterEndStr, "Pengeluaran");
        tipeTrans= "Pengeluaran";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        myCalendar = Calendar.getInstance();
                        myCalendar.add(Calendar.DATE, -7);
                        myCalendar2 = Calendar.getInstance();
                        filterstart = getActivity().findViewById(R.id.btn_date_start);
                        filterend = getActivity().findViewById(R.id.btn_date_end);
                        filterstart.setText(Util.calendarToString(myCalendar, "MMMM dd, yyyy"));
                        filterStartStr = Util.calendarToString(myCalendar, "yyyy-MM-dd");
                        filterend.setText(Util.calendarToString(myCalendar2, "MMMM dd, yyyy"));
                        filterEndStr = Util.calendarToString(myCalendar2, "yyyy-MM-dd");
                        tipeTrans= "Pengeluaran";
                        historiTransaksi(filterStartStr, filterEndStr, "Pengeluaran");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                    case 1:
                        myCalendar = Calendar.getInstance();
                        myCalendar.add(Calendar.DATE, -7);
                        myCalendar2 = Calendar.getInstance();
                        filterstart = getActivity().findViewById(R.id.btn_date_start);
                        filterend = getActivity().findViewById(R.id.btn_date_end);
                        filterstart.setText(Util.calendarToString(myCalendar, "MMMM dd, yyyy"));
                        filterStartStr = Util.calendarToString(myCalendar, "yyyy-MM-dd");
                        filterend.setText(Util.calendarToString(myCalendar2, "MMMM dd, yyyy"));
                        filterEndStr = Util.calendarToString(myCalendar2, "yyyy-MM-dd");
                        tipeTrans= "Pemasukan";
                        historiTransaksi(filterStartStr, filterEndStr, "Pemasukan");
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });
    }

    private void historiTransaksi(String date, final String date2, final String tipe){
        lihatTransaksiItems = DompetkuSqLite.getInstance(getActivity()).getHistoriTransaksi(date, date2, tipe);
        adapter = new HistoriTransaksiAdapter(getActivity(), lihatTransaksiItems);
        transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transaksi_list.setAdapter(adapter);
        //display datepicker
        filterstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                filterstart.setText(Util.calendarToString(myCalendar, "MMMM dd, yyyy"));
                                filterStartStr = Util.calendarToString(myCalendar, "yyyy-MM-dd");
                                lihatTransaksiItems = DompetkuSqLite.getInstance(getActivity()).getHistoriTransaksi(filterStartStr, filterEndStr, tipe);
                                adapter = new HistoriTransaksiAdapter(getActivity(), lihatTransaksiItems);
                                transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                transaksi_list.setAdapter(adapter);
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        filterend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myCalendar2.set(Calendar.YEAR, year);
                                myCalendar2.set(Calendar.MONTH, monthOfYear);
                                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                filterend.setText(Util.calendarToString(myCalendar2, "MMMM dd, yyyy"));
                                filterEndStr = Util.calendarToString(myCalendar2, "yyyy-MM-dd");
                                Log.d("Date", String.valueOf(filterStartStr));
                                Log.d("Date", String.valueOf(filterEndStr));
                                lihatTransaksiItems = DompetkuSqLite.getInstance(getActivity()).getHistoriTransaksi(filterStartStr, filterEndStr, tipe);
                                adapter = new HistoriTransaksiAdapter(getActivity(), lihatTransaksiItems);
                                transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                transaksi_list.setAdapter(adapter);
                            }
                        },
                        myCalendar2.get(Calendar.YEAR),
                        myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

//    private void initData(){
//        lihatTransaksiItems = DompetkuSqLite.getInstance(getActivity()).getHistoriTransaksi(historiTransaksi;);
//        transaksi_list = getActivity().findViewById(R.id.transaksi_list);
//        adapter = new HistoriTransaksiAdapter(getActivity(), lihatTransaksiItems);
//        transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        transaksi_list.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }



}

