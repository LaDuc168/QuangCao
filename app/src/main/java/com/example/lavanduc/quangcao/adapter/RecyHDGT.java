package com.example.lavanduc.quangcao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.activity.ChiTietKhachSanActivity;
import com.example.lavanduc.quangcao.activity.HienThiChiTietHDGTActivity;
import com.example.lavanduc.quangcao.activity.HoatDongGTActivity;
import com.example.lavanduc.quangcao.model.HoatDongGT;
import com.example.lavanduc.quangcao.model.KhachSan;

import java.util.ArrayList;

/**
 * Created by LaVanDuc on 5/4/2018.
 */

public class RecyHDGT extends RecyclerView.Adapter<RecyHDGT.ViewHolder> {
    ArrayList<HoatDongGT> ks;
    Context context;

    public RecyHDGT(ArrayList<HoatDongGT> ks, Context context) {
        this.ks = ks;
        this.context = context;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
//        View itemview=inflater.inflate(R.layout.row_item_khach_san,parent,false);
//
//        return new ViewHolder(itemview);
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.row_item_khach_san, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Bitmap bitmap = null;
        if (ks.get(position).getAnh() != null) {

            bitmap = BitmapFactory.decodeByteArray(ks.get(position).getAnh(), 0, ks.get(position).getAnh().length);
        }
        holder.img.setImageBitmap(bitmap);

        holder.txttenks.setText(ks.get(position).getTen());
        holder.txtdanhgia.setText(ks.get(position).getLuotthich()+" lượt thích");
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//
//    }

    @Override
    public int getItemCount() {
        return ks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        TextView txttenks;
        TextView txtdanhgia;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgks);
            txttenks = itemView.findViewById(R.id.txttenks);
            txtdanhgia = itemView.findViewById(R.id.txtdanhgiaks);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HienThiChiTietHDGTActivity.class);
                    intent.putExtra("MA", ks.get(getAdapterPosition()).getMa());
                    context.startActivity(intent);
                }
            });
        }
    }
}
