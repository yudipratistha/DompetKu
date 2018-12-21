package com.example.yudipratistha.dompetku.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yudipratistha.dompetku.AddActivity;
//import com.example.yudipratistha.dompetku.AddKategoriActivity;
import com.example.yudipratistha.dompetku.R;
import com.example.yudipratistha.dompetku.model.LihatKategoriItem;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.sqllite.DompetkuSqLite;
import com.example.yudipratistha.dompetku.util.Util;

import java.util.Calendar;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder>{
    Context context;
    List<LihatKategoriItem> kategoris;
    private final int REQUEST_UPDATE = 1;


    public KategoriAdapter(Context context, List<LihatKategoriItem> kategoris) {
        this.context = context;
        this.kategoris = kategoris;
    }

    public void setItems(List<LihatKategoriItem> kategoris) {
        this.kategoris = kategoris;
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.kategori_item, viewGroup, false);
        KategoriAdapter.ViewHolder vh = new KategoriAdapter.ViewHolder(view);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder viewHolder, int i) {
        LihatKategoriItem kategori = kategoris.get(i);

        Glide.with(context).load(kategori.getIcon()).into(viewHolder.icon);
        viewHolder.kategori.setText(kategori.getNamaKategori());

//        viewHolder.balance_value.setText(String.valueOf(activity.getJumlah()));
//        viewHolder.has_image.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return kategoris == null ? 0 : kategoris.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View line;

        TextView kategori;
        ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            kategori = itemView.findViewById(R.id.kategori);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
//            LihatKategoriItem kategori = kategoris.get(position);
//            Intent detail_activity = new Intent(view.getContext(), AddKategoriActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Kategori", kategori);
//            detail_activity.putExtras(bundle);
//            view.getContext().startActivity(detail_activity);
        }
    }
}
