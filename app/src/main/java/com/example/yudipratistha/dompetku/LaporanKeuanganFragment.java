package com.example.yudipratistha.dompetku;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.util.Calendar;
import java.util.Date;

import lecho.lib.hellocharts.view.PieChartView;

public class LaporanKeuanganFragment extends Fragment {
    Calendar myCalendar;
    Calendar myCalendar2;
    public Button filterstart;
    public Button filterend;
    public String filterStartStr;
    public String filterEndStr;
    TextView text_pendapatan_total;
    TextView text_pengeluaran_total;
    TextView text_jumlah_total;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.app_bar_text);
        TextView text_judul = getActivity().findViewById(R.id.text_judul);
        text_judul.setText(R.string.laporanKeuangan);
        text_pendapatan_total = getActivity().findViewById(R.id.text_pendapatan_total);
        text_pengeluaran_total = getActivity().findViewById(R.id.text_pengeluaran_total);
        text_jumlah_total = getActivity().findViewById(R.id.text_jumlah_total);
        PieChartView chart_pemasukan = getActivity().findViewById(R.id.chart_pemasukan);
        PieChartView chart_pengeluaran = getActivity().findViewById(R.id.chart_pengeluaran);

        myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DATE, -7);
        myCalendar2 = Calendar.getInstance();
        filterstart = getActivity().findViewById(R.id.btn_date_start);
        filterend = getActivity().findViewById(R.id.btn_date_end);
        filterstart.setText(Util.calendarToString(myCalendar, "MMMM dd, yyyy"));
        filterStartStr = Util.calendarToString(myCalendar, "yyyy-MM-dd");
        filterend.setText(Util.calendarToString(myCalendar2, "MMMM dd, yyyy"));
        filterEndStr = Util.calendarToString(myCalendar2, "yyyy-MM-dd");
        pemasukanPengeluaran(filterStartStr, filterEndStr);

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
                                pemasukanPengeluaran(filterStartStr, filterEndStr);
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
                                pemasukanPengeluaran(filterStartStr, filterEndStr);
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

    private void pemasukanPengeluaran(String date, String date2){
        int text_pengeluaran = DompetkuSqLite.getInstance(getContext()).getLaporanPeng(date, date2);
        int text_pendapatan = DompetkuSqLite.getInstance(getContext()).getLaporanPem(date, date2);
        int total_saldo = DompetkuSqLite.getInstance(getContext()).getLaporanSaldoTotal(date, date2);

        if (total_saldo > 0 ){
            text_jumlah_total.setTextColor(getResources().getColor(R.color.colorAccentBlue));
        }else if (total_saldo < 0){
            text_jumlah_total.setTextColor(getResources().getColor(R.color.colorAccentRed));
        }
        String totalPendapatan = "Rp. " + String.valueOf(text_pendapatan);
        String totalPeng = "Rp. " + String.valueOf(text_pengeluaran);
        String totalSaldo = "Rp. " + String.valueOf(total_saldo);

        text_pendapatan_total.setText(totalPendapatan);
        text_pengeluaran_total.setText(totalPeng);
        text_jumlah_total.setText(totalSaldo);
    }


}

