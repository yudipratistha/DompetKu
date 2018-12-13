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


import com.example.yudipratistha.dompetku.API.APIService;
import com.example.yudipratistha.dompetku.model.BuatTransaksi;
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
    Button input_category;
    ImageView icon_kategori;
    Spinner kategori_input;
    EditText input_date;
    Calendar myCalendar;
    MenuItem save;
    MenuItem delete;
    TextView biaya;
    String tipe;
    LihatTransaksiItem activity;

    APIService service;
    boolean isAdd = true;
    boolean isInsert = true;
    boolean isUpdate = true;

    DompetkuSqLite db;

    private void populateData(LihatTransaksiItem activity){
        Calendar calendar = Util.stringToCalendar(activity.getTanggal(), "yyyy-MM-dd HH:mm");
        String pattern = "dd MMMM";
        if(calendar.get(Calendar.YEAR) != Calendar.getInstance().get(Calendar.YEAR))
            pattern+=" yyyy";

        LihatKategoriItem type = DompetkuSqLite.getInstance(this).getType(activity.getIdKategori());
        input_value.setText(String.valueOf(activity.getJumlah()));
        input_note.setText(activity.getCatatan());
        icon_kategori.setImageResource(type.getIcon());
        input_date.setText(Util.calendarToString(calendar, pattern));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TabLayout tabLayout =  findViewById(R.id.tabs);
        biaya = findViewById(R.id.input_text);
        setupToolBar();
                db = DompetkuSqLite.getInstance(this);


        //finding views
        input_value = findViewById(R.id.input_value);
        input_note = findViewById(R.id.input_note);
        icon_kategori = findViewById(R.id.icon_kategori);
        kategori_input = findViewById(R.id.kategori_input);
        input_date = findViewById(R.id.input_date);

        //isntance Calendar
        myCalendar = Calendar.getInstance();
        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getPengeluaranKategori("Pengeluaran");
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        biaya.setText(R.string.minus);
                        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getPengeluaranKategori("Pengeluaran");
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
                        Toast.makeText(AddActivity.this, tipe, Toast.LENGTH_LONG).show();
//                    case 1:
//                        biaya.setText(R.string.plus);
//                        Toast.makeText(AddActivity.this, "Gagal ada masalah pengisian", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        biaya.setText(R.string.minus);
                        Toast.makeText(AddActivity.this, "wee11111e", Toast.LENGTH_LONG).show();
                        tipe = "Pengeluaran";

                    case 1:
                        biaya.setText(R.string.plus);
                        final List<LihatKategoriItem> types = db.getInstance(getApplicationContext()).getPengeluaranKategori("Pemasukan");
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
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        //load all data from sqlite
//        Toast.makeText(AddActivity.this, tipe, Toast.LENGTH_LONG).show();


        activity = new LihatTransaksiItem();
        try {
            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            activity = (LihatTransaksiItem) bundle.getSerializable("Transaksi");
            Log.d("weee", String.valueOf(activity));
            isAdd = false;
        } catch (Exception e){

        }

        if(isAdd){
            setTitle("Add Activity");
        } else {
            setTitle("Edit Activity");
            myCalendar = Util.stringToCalendar(activity.getTanggal(),"yyyy-MM-dd");
            LihatKategoriItem type = db.getInstance(this).getType(activity.getIdKategori());

            int type_position = adapter.getPosition(type);
            this.kategori_input.setSelection(type_position);

            populateData(activity);
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
                activity.setIdUser(id_user);
                activity.setIdKategori(selectedType.getId());
                activity.setTanggal(Util.calendarToString(myCalendar, "yyyy-MM-dd"));
                activity.setCatatan(input_note.getText().toString());
                activity.setJumlah(Integer.parseInt(input_value.getText().toString()));

                Date now = Calendar.getInstance().getTime();
                activity.setUpdatedAt(now.toString());
                activity.setCreatedAt(now.toString());
                activity.setStatusSync(1);
                if(isAdd) {

                    DompetkuSqLite.getInstance(getApplicationContext()).insertTransaksi(activity);

                } else {
                    if(isUpdate) activity.setStatusUpdate(1);
                    else activity.setStatusUpdate(0);
                    DompetkuSqLite.getInstance(this).editTransaksi(activity);
                    Intent add_activity = new Intent(getApplicationContext(), AddActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Transaksi", activity);
                    add_activity.putExtras(bundle);
                    setResult(RESULT_OK, add_activity);
                }
                finish();
                return true;

            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete Activity")
                        .setMessage("Do you really want to delete this activity?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DompetkuSqLite.getInstance(getApplicationContext()).deleteTransaksi(activity);
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
        service.buatTransaksi(activity.getIdUser(), activity.getJumlah(), activity.getCatatan(),
                activity.getIdKategori(), activity.getTanggal())
                .enqueue(new Callback<BuatTransaksi>() {
                    @Override
                    public void onResponse(Call<BuatTransaksi> call, Response<BuatTransaksi> response) {
                        if (response.isSuccessful()) {
                            isInsert = true;
                            Toast.makeText(AddActivity.this, "Berhasil insert task", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddActivity.this, "laravel ok, DB no", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BuatTransaksi> call, Throwable t) {
                        Toast.makeText(AddActivity.this, "laravel no", Toast.LENGTH_LONG).show();
                    }
                });
        if (isInsert) activity.setStatusSync(1);
        else activity.setStatusSync(0);
        activity.setStatusUpdate(1);
        activity.setStatusDelete(1);
        DompetkuSqLite.getInstance(getApplicationContext()).insertTransaksi(activity);
    }

    private void updateTrans(){
        isUpdate = false;
        service.updateTransaksi(activity.getId(), activity.getJumlah(), activity.getIdKategori(), activity.getCatatan()
                , activity.getTanggal()).
                enqueue(new Callback<UpdateTransaksi>() {
                    @Override
                    public void onResponse(Call<UpdateTransaksi> call, Response<UpdateTransaksi> response) {
                        if (response.isSuccessful()) {
                            Log.d("TaskUpdate", response.body().getUpdateTransaksi().toString());
                            isUpdate = true;
                            Toast.makeText(AddActivity.this,"Berhasil update task",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(AddActivity.this,"laravel ok, DB no",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateTransaksi> call, Throwable t) {
                        Toast.makeText(AddActivity.this,"laravel no",Toast.LENGTH_LONG).show();
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
        finish(); // close this activity as oppose to navigating up

        return false;
    }

}