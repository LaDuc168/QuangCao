package com.example.lavanduc.quangcao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.KhachSan;

import java.util.ArrayList;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class AdapterKhachSan extends BaseAdapter {
    Context myContext;
    ArrayList<KhachSan> mang;

    public AdapterKhachSan(Context myContext, ArrayList<KhachSan> mang) {
        this.myContext = myContext;
        this.mang = mang;
    }

    @Override
    public int getCount() {
        return mang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_item_khach_san,null);
        ImageView img=convertView.findViewById(R.id.imgks);
        TextView txttenks=convertView.findViewById(R.id.txttenks);
        TextView txtdanhgia=convertView.findViewById(R.id.txtdanhgiaks);
        KhachSan khachSan=mang.get(position);

        Bitmap bitmap= BitmapFactory.decodeByteArray(khachSan.getAnh(),0,khachSan.getAnh().length);
        img.setImageBitmap(bitmap);
        txttenks.setText(khachSan.getTenks());
        txtdanhgia.setText(khachSan.getDanhgia()+" đánh giá");
        return convertView;
    }
}
