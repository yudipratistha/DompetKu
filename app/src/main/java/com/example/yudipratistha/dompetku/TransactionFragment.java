package com.example.yudipratistha.dompetku;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.adapter.TransaksiAdapter;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionFragment extends Fragment {
    Calendar myCalendar;
    String month_string;
    public ImageButton monthPrev;
    public TextView monthText;
    public ImageButton monthNext;
    private static String month;
    private static boolean isMonth = false;
    private FloatingActionButton fab;
    private RecyclerView transaksi_list;
    private Toolbar toolbar;
    TextView text_account;
    TextView text_amount_account;
    TextView text_pengeluaran_total;
    TextView text_pendapatan_total;
    TextView text_jumlah_total;
    EditText input_value;
    EditText input_note;
    ImageView icon_kategori;
    Spinner kategori_input;
    EditText input_date;
    LihatTransaksiItem activity;
    public TransaksiAdapter adapter;
    public List<LihatTransaksiItem> lihatTransaksiItems;
    public TransactionFragment() {}

    public static TransactionFragment newInstance() {
        TransactionFragment fragment = new TransactionFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_transaksi, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy", Locale.US);
        try {
            String month = getArguments().getString("month");
            isMonth = true;
        }catch (Exception e){

        }
        if(isMonth){
            myCalendar = Util.stringToCalendar(month, "MMM yyyy");
            month_string = Util.calendarToString(myCalendar, "yyyy MM");
        }else{
            month = df.format(myCalendar.getTime());
            Calendar month_calendar = Util.stringToCalendar(month, "yyyy MM");
            month_string = Util.calendarToString(month_calendar, "yyyy MM");
        }

        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.app_bar_main, null);
        actionBar.setCustomView(customView);
        Toolbar parent =(Toolbar) customView.getParent();
        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.app_bar_main);
        View viewt = actionBar.getCustomView();

        TextView title = viewt.findViewById(R.id.text_month);
        ImageButton imageButton= viewt.findViewById(R.id.imageButton_prev);
        ImageButton imageButton2= viewt.findViewById(R.id.imageButton_next);
        text_account = getActivity().findViewById(R.id.text_account);
        text_amount_account = getActivity().findViewById(R.id.text_amount_account);
        text_pengeluaran_total = getActivity().findViewById(R.id.text_pengeluaran_total);
        text_pendapatan_total = getActivity().findViewById(R.id.text_pendapatan_total);
        text_jumlah_total = getActivity().findViewById(R.id.text_jumlah_total);



        title.setText(month);
        monthPrev = getActivity().findViewById(R.id.imageButton_prev);
        monthText = getActivity().findViewById(R.id.text_month);
        monthNext = getActivity().findViewById(R.id.imageButton_next);
        fab = getActivity().findViewById(R.id.fab_add_activity);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.MONTH, -1);
                month = Util.calendarToString(myCalendar, "MMM yyyy");
                TransactionFragment fragment = new TransactionFragment();
                Bundle month = new Bundle();
                month.putString("month", String.valueOf(month));
                fragment.setArguments(month);
                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.MONTH, +1);
                month = Util.calendarToString(myCalendar, "MMM yyyy");
                TransactionFragment fragment = new TransactionFragment();
                Bundle month = new Bundle();
                month.putString("month", String.valueOf(month));
                fragment.setArguments(month);
                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData(){
        int total_saldo = DompetkuSqLite.getInstance(getContext()).getTransaksiTotal(month_string);
        int total_peng = DompetkuSqLite.getInstance(getContext()).getTransaksiMonthPeng(month_string);
        int total_pem = DompetkuSqLite.getInstance(getContext()).getTransaksiMonthPem(month_string);
        int arus_kas = total_pem - total_peng;
        if (arus_kas > 0 ){
            text_jumlah_total.setTextColor(getResources().getColor(R.color.colorAccentBlue));
        }else if (arus_kas < 0){
            text_jumlah_total.setTextColor(getResources().getColor(R.color.colorAccentRed));
        }
        if (total_saldo > 0 ){
            text_amount_account.setTextColor(getResources().getColor(R.color.colorAccentBlue));
        }else if (total_saldo < 0){
            text_amount_account.setTextColor(getResources().getColor(R.color.colorAccentRed));
        }
        String saldo = "Saldo (" + month +")";
        String totalSaldo = "Rp. " + String.valueOf(total_saldo);
        String total_pengeluaran= "Rp. " + String.valueOf(total_peng);
        String total_pemasukan = "Rp. " + String.valueOf(total_pem);
        String kas_bulan = "Rp. " + String.valueOf(arus_kas);
        text_account.setText(saldo);
        text_amount_account.setText(totalSaldo);
        text_pengeluaran_total.setText(total_pengeluaran);
        text_pendapatan_total.setText(total_pemasukan);
        text_jumlah_total.setText(kas_bulan);

        lihatTransaksiItems = DompetkuSqLite.getInstance(getActivity()).getTransaksiMonth(month_string);
        transaksi_list = getActivity().findViewById(R.id.transaksi_list);
        adapter = new TransaksiAdapter(getActivity(), lihatTransaksiItems);
        transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transaksi_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("aaaaa", "aaaaaaaaaaaaaaaaa");
        initData();
    }
}

