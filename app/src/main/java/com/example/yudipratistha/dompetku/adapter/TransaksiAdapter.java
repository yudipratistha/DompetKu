package com.example.yudipratistha.dompetku.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yudipratistha.dompetku.AddActivity;
import com.example.yudipratistha.dompetku.R;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;

import java.util.List;


public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    Context context;
    List<LihatTransaksiItem> activities;

    public TransaksiAdapter(Context context, List<LihatTransaksiItem> activities) {
        this.context = context;
        this.activities = activities;
    }

    public void setItems(List<LihatTransaksiItem> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaksi_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder viewHolder, int i) {
        LihatTransaksiItem activity = activities.get(i);

        String[] datetime = activity.getTanggal().split(" ");
        String prevDate = (i == 0) ? "" : activities.get(i - 1).getTanggal().split(" ")[0];
        int jmlTransaksi = 100000;

        if (!datetime[0].equals(prevDate)) {
            viewHolder.text_number_day.setText("01");
            viewHolder.text_day.setText("Sabtu");
            viewHolder.text_month.setText("Desember 2018");
            viewHolder.text_value.setText(String.valueOf(jmlTransaksi));

        } else {
            viewHolder.text_number_day.setVisibility(View.GONE);
            viewHolder.text_day.setVisibility(View.GONE);
            viewHolder.text_month.setVisibility(View.GONE);
            viewHolder.text_value.setVisibility(View.GONE);
        }
//        viewHolder.text_month.setText(activity.getTanggal());

        viewHolder.balance_kategori.setText(String.valueOf(activity.getIdKategori()));
        viewHolder.balance_value.setText(String.valueOf(activity.getJumlah()));
        viewHolder.balance_notes.setText(activity.getCatatan());
        viewHolder.has_image.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View line;

        TextView text_number_day;
        TextView text_day;
        TextView text_month;
        TextView text_value;
        ImageView icon;
        TextView balance_kategori;
        TextView balance_value;
        TextView balance_notes;
        ImageView has_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_number_day = itemView.findViewById(R.id.text_number_day);
            text_day = itemView.findViewById(R.id.text_day);
            text_month = itemView.findViewById(R.id.text_month);
            text_value = itemView.findViewById(R.id.text_value);
            icon = itemView.findViewById(R.id.icon);
            balance_kategori = itemView.findViewById(R.id.balance_kategori);
            balance_value = itemView.findViewById(R.id.balance_value);
            balance_notes = itemView.findViewById(R.id.balance_notes);
            has_image = itemView.findViewById(R.id.has_image);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            LihatTransaksiItem activity = activities.get(position);

            Intent detail_activity = new Intent(view.getContext(), AddActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Activity", activity);
//            detail_activity.putExtras(bundle);
            view.getContext().startActivity(detail_activity);
        }
    }
}