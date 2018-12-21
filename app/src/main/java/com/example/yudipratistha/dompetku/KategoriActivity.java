package com.example.yudipratistha.dompetku;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.adapter.HistoriTransaksiAdapter;
import com.example.yudipratistha.dompetku.adapter.KategoriAdapter;
import com.example.yudipratistha.dompetku.model.LihatKategoriItem;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {
    RecyclerView kategori_list;
    Calendar myCalendar;
    Calendar myCalendar2;
    private Button filterstart;
    private Button filterend;
    private String filterStartStr;
    private String filterEndStr;
    public KategoriAdapter adapter;
    public List<LihatKategoriItem> lihatKategoriItem;
    EditText input_value;
    EditText input_note;
    ImageView icon_kategori;
    Spinner kategori_input;
    EditText input_date;
    MenuItem delete;
    LihatTransaksiItem activity;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        setupToolBar();

        TabLayout tabLayout = findViewById(R.id.tabs);
        kategori_list = findViewById(R.id.kategori_list);
        kategori_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        kategori_list.setAdapter(adapter);

        kategoriItem("Pengeluaran");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        kategoriItem("Pengeluaran");
                        break;
                    case 1:
                        kategoriItem("Pemasukan");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        kategoriItem("Pengeluaran");
                        break;
                    case 1:
                        kategoriItem("Pemasukan");
                        break;
                }
            }

        });

        fab = findViewById(R.id.fab_add_kategori_activity);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(KategoriActivity.this, AddKategoriActivity.class);
//                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void kategoriItem(String tipe){
        lihatKategoriItem = DompetkuSqLite.getInstance(this).getKategori(tipe);
        adapter = new KategoriAdapter(this, lihatKategoriItem);
        kategori_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        kategori_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        kategoriItem("Pengeluaran");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        kategoriItem("Pengeluaran");
                        break;
                    case 1:
                        kategoriItem("Pemasukan");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        kategoriItem("Pengeluaran");
                        break;
                    case 1:
                        kategoriItem("Pemasukan");
                        break;
                }
            }

        });
    }

    private void setupToolBar(){
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_text);
        TextView text_judul = findViewById(R.id.text_judul);
        text_judul.setText(R.string.kategori);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
