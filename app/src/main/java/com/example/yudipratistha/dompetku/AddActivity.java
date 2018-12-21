package com.example.yudipratistha.dompetku;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yudipratistha.dompetku.API.APIClient;
import com.example.yudipratistha.dompetku.API.APIService;
import com.example.yudipratistha.dompetku.model.BuatTransaksi;
import com.example.yudipratistha.dompetku.model.DeleteTransaksi;
import com.example.yudipratistha.dompetku.model.LihatKategoriItem;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.model.UpdateTransaksi;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    EditText input_value;
    EditText input_note;
    ImageView icon_kategori;
    Spinner kategori_input;
    EditText input_date;
    Calendar myCalendar;
    MenuItem save;
    MenuItem delete;
    TextView biaya;
    LihatTransaksiItem transaksi;
    TabLayout tabs;

    APIService service;
    boolean isAdd = true;
    boolean isInsert = true;
    boolean isUpdate = true;

    DompetkuSqLite db;

    private void populateData(LihatTransaksiItem transaksi){
        TabLayout tabLayout =  findViewById(R.id.tabs);
        Calendar calendar = Util.stringToCalendar(transaksi.getTanggal(), "yyyy-MM-dd");
        String pattern = "dd MMMM";
        if(calendar.get(Calendar.YEAR) != Calendar.getInstance().get(Calendar.YEAR))
            pattern+=" yyyy";
        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getAllType();
        LihatKategoriItem type = db.getInstance(this).getType(transaksi.getIdKategori());
        if (type.getTipe().equals("Pengeluaran")){
            biaya.setText("-");
            tabLayout.setScrollPosition(0, 0.0f, true);
        }else if (type.getTipe().equals("Pemasukan")){
            biaya.setText("+");
            tabLayout.setScrollPosition(1, 0.0f, true);
        }

        input_value.setText(String.valueOf(transaksi.getJumlah()));
        input_note.setText(transaksi.getCatatan());
        ArrayAdapter<LihatKategoriItem> adapter = new ArrayAdapter<LihatKategoriItem>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int type_position = adapter.getPosition(type);
        this.kategori_input.setSelection(type_position);
        Log.d("kategori", type.getTipe());
        icon_kategori.setImageResource(type.getIcon());
        input_date.setText(Util.calendarToString(calendar, pattern));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        biaya = findViewById(R.id.input_text);
        setupToolBar();
        db = DompetkuSqLite.getInstance(this);
        service = APIClient.getService();

        //load all data from sqlite
        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getAllType();

        //finding views
        input_value = findViewById(R.id.input_value);
        input_note = findViewById(R.id.input_note);
        icon_kategori = findViewById(R.id.icon_kategori);
        kategori_input = findViewById(R.id.kategori_input);
        input_date = findViewById(R.id.input_date);

        //isntance Calendar
        myCalendar = Calendar.getInstance();
        //spinner
        ArrayAdapter<LihatKategoriItem> adapter = new ArrayAdapter<LihatKategoriItem>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_input.setAdapter(adapter);
        tabKat();

        transaksi = new LihatTransaksiItem();
        try {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            transaksi = (LihatTransaksiItem) bundle.getSerializable("Transaksi");
            isAdd = false;
        } catch (Exception e){

        }

        if(isAdd){
            getSupportActionBar().setTitle(R.string.tambahTransaksi);
        } else {
            getSupportActionBar().setTitle(R.string.editTransaksi);
            myCalendar = Util.stringToCalendar(transaksi.getTanggal(),"yyyy-MM-dd");
            LihatKategoriItem type = db.getInstance(this).getType(transaksi.getIdKategori());
            Log.d("spinner", String.valueOf(type));
            int type_position = adapter.getPosition(type);
            Log.e("spinner", String.valueOf(type_position));
            kategori_input.setSelection(3);

            populateData(transaksi);
        }

        input_date.setText(Util.calendarToStringFriendly(myCalendar, "yyyy-MM-dd"));

        //display datepicker
        input_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                input_date.setText(Util.calendarToStringFriendly(myCalendar, "MMMM dd, yyyy"));
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

    }

    private void tabKat(){
        TabLayout tabLayout =  findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("select tab", String.valueOf(position));
                switch (tab.getPosition()){
                    case 0:
                        Log.d("tab", "1");
                        biaya.setText(R.string.minus);
                        tipe("Pengeluaran");
                        break;
                    case 1:
                        biaya.setText(R.string.plus);
                        tipe("Pemasukan");
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabUnselected: " + tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("TAG", "onTabReselected: " + tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        biaya.setText(R.string.minus);
                        tipe("Pengeluaran");
                        break;
                    case 1:
                        biaya.setText(R.string.plus);
                        Log.d("tab", "2");
                        tipe("Pemasukan");
                        break;
                }
            }

        });
    }

    private void tipe(String tipe){
        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getPengeluaranKategori(tipe);
        //spinner
        ArrayAdapter<LihatKategoriItem> adapter = new ArrayAdapter<LihatKategoriItem>(getApplicationContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_input.setAdapter(adapter);

        kategori_input.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                icon_kategori.setImageResource(types.get(i).getIcon());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (isAdd){
            inflater.inflate(R.menu.add_done, menu);
            save = menu.findItem(R.id.action_add);
            save.setEnabled(true);
        }else{
            inflater.inflate(R.menu.delete, menu);
            inflater.inflate(R.menu.add_done, menu);
            delete = menu.findItem(R.id.action_delete);
            save = menu.findItem(R.id.action_add);
            delete.setEnabled(true);
            save.setEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                LihatKategoriItem selectedType = (LihatKategoriItem) kategori_input.getSelectedItem();
                SharedPreferences sharedPreferences = getSharedPreferences("dataPengguna", MODE_PRIVATE);
                int id_user = sharedPreferences.getInt("id",0);
                transaksi.setIdUser(id_user);
                transaksi.setIdKategori(selectedType.getId());
                transaksi.setTanggal(Util.calendarToString(myCalendar, "yyyy-MM-dd"));
                if (input_note.getText().toString().equals("")){
                    transaksi.setCatatan("-");
                }else {
                    transaksi.setCatatan(input_note.getText().toString());
                }
                transaksi.setJumlah(Integer.parseInt(input_value.getText().toString()));

                Date now = Calendar.getInstance().getTime();
                transaksi.setUpdatedAt(now.toString());
                transaksi.setCreatedAt(now.toString());
                transaksi.setStatusSync(1);
                if(isAdd) {
                    insertTrans();
                } else {
                    updateTrans();
                    if(isUpdate) transaksi.setStatusUpdate(1);
                    else transaksi.setStatusUpdate(0);
                    Intent add_activity = new Intent(getApplicationContext(), AddActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Transaksi", transaksi);
                    add_activity.putExtras(bundle);
                    setResult(RESULT_OK, add_activity);
                }
                finish();
                return true;

            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Hapus Transaksi")
                        .setMessage("Apakah kamu benar akan menghapus transaksi ini?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteTrans();
                                Toast.makeText(getApplicationContext(), "Hapus data transaksi sukses", Toast.LENGTH_LONG).show();
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertTrans() {
        isInsert = false;
        service.buatTransaksi( transaksi.getIdUser(), transaksi.getIdKategori(), transaksi.getTanggal(), transaksi.getCatatan(), transaksi.getJumlah())
                .enqueue(new Callback<BuatTransaksi>() {
                    @Override
                    public void onResponse(Call<BuatTransaksi> call, Response<BuatTransaksi> response) {
                        if (response.isSuccessful()) {
                            isInsert = true;
                            Toast.makeText(AddActivity.this, "Berhasil insert transaksi Bulan "+ Util.calendarToString(Util.stringToCalendar(transaksi.getTanggal(),"yyyy MM dd"), "MMMM"), Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).insertTransaksi(transaksi);
                        } else {
                            Toast.makeText(AddActivity.this, "Koneksi DB Gagal", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BuatTransaksi> call, Throwable t) {
                        Toast.makeText(AddActivity.this, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                    }
                });
        Log.d("isInsert", String.valueOf(isInsert));
        if (isInsert) transaksi.setStatusSync(1);
        else transaksi.setStatusSync(0);
        transaksi.setStatusUpdate(1);
        transaksi.setStatusDelete(1);
    }

    private void updateTrans(){
        isUpdate = false;
        Log.e("Transaksi Update", String.valueOf(transaksi.getId()));
        service.updateTransaksi(transaksi.getId(), transaksi.getJumlah(), transaksi.getIdKategori(), transaksi.getTanggal()
                , transaksi.getCatatan()).
                enqueue(new Callback<UpdateTransaksi>() {
                    @Override
                    public void onResponse(Call<UpdateTransaksi> call, Response<UpdateTransaksi> response) {
                        if (response.isSuccessful()) {
                            Log.d("TaskUpdate", response.body().getUpdateTransaksi().toString());
                            isUpdate = true;
                            Toast.makeText(AddActivity.this,"Berhasil update transaksi",Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).editTransaksi(transaksi);
                        }
                        else {
                            Toast.makeText(AddActivity.this,"Koneksi DB Gagal",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateTransaksi> call, Throwable t) {
                        Toast.makeText(AddActivity.this,"Koneksi Bermasalah",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void deleteTrans(){
        isUpdate = false;
        Log.e("Transaksi Delete", String.valueOf(transaksi.getId()));
        service.deleteTransaksi(transaksi.getId())
                .enqueue(new Callback<DeleteTransaksi>() {
                    @Override
                    public void onResponse(Call<DeleteTransaksi> call, Response<DeleteTransaksi> response) {
                        if (response.isSuccessful()) {
                            isUpdate = true;
                            Toast.makeText(AddActivity.this,"Berhasil delete transaksi",Toast.LENGTH_LONG).show();
                            DompetkuSqLite.getInstance(getApplicationContext()).deleteTransaksi(transaksi);
                        }
                        else {
                            Toast.makeText(AddActivity.this,"Koneksi DB Bermasalah",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteTransaksi> call, Throwable t) {
                        Toast.makeText(AddActivity.this,"Koneksi Bermasalah",Toast.LENGTH_LONG).show();
                    }
                });

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this transaksi as oppose to navigating up

        return false;
    }

}