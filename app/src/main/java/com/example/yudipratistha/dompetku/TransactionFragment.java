package com.example.yudipratistha.dompetku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.adapter.TransaksiAdapter;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class TransactionFragment extends Fragment {
    Calendar myCalendar;
    public ImageButton monthPrev;
    public TextView monthText;
    public ImageButton monthNext;
    private static String month;
    private static boolean isMonth = false;
    private FloatingActionButton fab;
    private RecyclerView transaksi_list;
    private Toolbar toolbar;
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

        //isntance Calendar


        lihatTransaksiItems = new ArrayList<>();
        LihatTransaksiItem tempTaskItem = new LihatTransaksiItem();
        tempTaskItem.setId(1);
        tempTaskItem.setIdUser(1);
        tempTaskItem.setIdKategori(1);
        tempTaskItem.setIdPenyimpanan(1);
        tempTaskItem.setTanggal("2018-11-24");
        tempTaskItem.setCatatan("Beli Nasi Goreng");
        tempTaskItem.setJumlah(10000);
        tempTaskItem.setCreatedAt("2018-11-24");
        tempTaskItem.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem);

        LihatTransaksiItem tempTaskItem2 = new LihatTransaksiItem();
        tempTaskItem2.setId(2);
        tempTaskItem2.setIdUser(1);
        tempTaskItem2.setIdKategori(1);
        tempTaskItem2.setIdPenyimpanan(1);
        tempTaskItem2.setTanggal("2018-11-24");
        tempTaskItem2.setCatatan("Beli Nasi");
        tempTaskItem2.setJumlah(10000);
        tempTaskItem2.setCreatedAt("2018-11-24");
        tempTaskItem2.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem2);

        LihatTransaksiItem tempTaskItem3 = new LihatTransaksiItem();
        tempTaskItem3.setId(3);
        tempTaskItem3.setIdUser(1);
        tempTaskItem3.setIdKategori(1);
        tempTaskItem3.setIdPenyimpanan(1);
        tempTaskItem3.setTanggal("2018-11-30");
        tempTaskItem3.setCatatan("Beli Nasi Goreng");
        tempTaskItem3.setJumlah(10000);
        tempTaskItem3.setCreatedAt("2018-11-24");
        tempTaskItem3.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem3);

        LihatTransaksiItem tempTaskItem4 = new LihatTransaksiItem();
        tempTaskItem4.setId(3);
        tempTaskItem4.setIdUser(1);
        tempTaskItem4.setIdKategori(1);
        tempTaskItem4.setIdPenyimpanan(1);
        tempTaskItem4.setTanggal("2018-11-30");
        tempTaskItem4.setCatatan("Beli Nasi Goreng");
        tempTaskItem4.setJumlah(10000);
        tempTaskItem4.setCreatedAt("2018-11-24");
        tempTaskItem4.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem4);

        LihatTransaksiItem tempTaskItem5 = new LihatTransaksiItem();
        tempTaskItem5.setId(3);
        tempTaskItem5.setIdUser(1);
        tempTaskItem5.setIdKategori(1);
        tempTaskItem5.setIdPenyimpanan(1);
        tempTaskItem5.setTanggal("2018-11-30");
        tempTaskItem5.setCatatan("Beli Nasi Goreng");
        tempTaskItem5.setJumlah(10000);
        tempTaskItem5.setCreatedAt("2018-11-24");
        tempTaskItem5.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem5);

        LihatTransaksiItem tempTaskItem6 = new LihatTransaksiItem();
        tempTaskItem6.setId(3);
        tempTaskItem6.setIdUser(1);
        tempTaskItem6.setIdKategori(1);
        tempTaskItem6.setIdPenyimpanan(1);
        tempTaskItem6.setTanggal("2018-11-30");
        tempTaskItem6.setCatatan("Beli Nasi Goreng");
        tempTaskItem6.setJumlah(10000);
        tempTaskItem6.setCreatedAt("2018-11-24");
        tempTaskItem6.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem6);

        LihatTransaksiItem tempTaskItem7 = new LihatTransaksiItem();
        tempTaskItem7.setId(3);
        tempTaskItem7.setIdUser(1);
        tempTaskItem7.setIdKategori(1);
        tempTaskItem7.setIdPenyimpanan(1);
        tempTaskItem7.setTanggal("2018-12-30");
        tempTaskItem7.setCatatan("Beli Nasi Goreng");
        tempTaskItem7.setJumlah(10000);
        tempTaskItem7.setCreatedAt("2018-11-24");
        tempTaskItem7.setUpdatedAt("2018-11-24");
        lihatTransaksiItems.add(tempTaskItem7);

        LihatTransaksiItem tempTaskItem8 = new LihatTransaksiItem();
        tempTaskItem8.setId(3);
        tempTaskItem8.setIdUser(1);
        tempTaskItem8.setIdKategori(1);
        tempTaskItem8.setIdPenyimpanan(1);
        tempTaskItem8.setTanggal("2018-12-30");
        tempTaskItem8.setCatatan("Beli Nasi Goreng");
        tempTaskItem8.setJumlah(10000);
        tempTaskItem8.setCreatedAt("2018-12-24");
        tempTaskItem8.setUpdatedAt("2018-12-24");
        lihatTransaksiItems.add(tempTaskItem8);

//        monthPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

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
        }else{
            month = df.format(myCalendar.getTime());
        }
        monthPrev = getActivity().findViewById(R.id.imageButton_prev);
        monthText = getActivity().findViewById(R.id.text_month);
        monthNext = getActivity().findViewById(R.id.imageButton_next);
        monthText.setText(String.valueOf(month));
        fab = getActivity().findViewById(R.id.fab_add_activity);

        monthPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar.add(Calendar.MONTH, +1);
                month = Util.calendarToString(myCalendar, "MMM yyyy");
                TransactionFragment fragment = new TransactionFragment();
                Bundle month = new Bundle();
                month.putString("month", String.valueOf(month));
                fragment.setArguments(month);
                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
        });

//        fab = getActivity().findViewById(R.id.fab_add_activity);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AddActivity.class);
//                startActivity(intent);
//            }
//        });

        transaksi_list = getActivity().findViewById(R.id.transaksi_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        adapter = new TransaksiAdapter(getActivity(), lihatTransaksiItems);
        transaksi_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transaksi_list.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
//        activityList = TriathlogDbHelper.getInstance(getActivity()).getAllActivity();
//        adapter.setItems(activityList);
//        adapter.notifyDataSetChanged();
    }
}

