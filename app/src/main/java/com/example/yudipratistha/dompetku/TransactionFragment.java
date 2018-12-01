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

import com.example.yudipratistha.dompetku.adapter.TransaksiAdapter;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TransactionFragment extends Fragment {
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

        transaksi_list = getActivity().findViewById(R.id.transaksi_list);
        fab = getActivity().findViewById(R.id.fab_add_activity);
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

