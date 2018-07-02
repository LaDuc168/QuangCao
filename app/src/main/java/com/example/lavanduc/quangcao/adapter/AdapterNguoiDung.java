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
import com.example.lavanduc.quangcao.model.NguoiDung;

import java.util.ArrayList;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class AdapterNguoiDung extends BaseAdapter {
    Context myContext;
    ArrayList<NguoiDung> mang;

    public AdapterNguoiDung(Context myContext, ArrayList<NguoiDung> mang) {
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
        convertView=inflater.inflate(R.layout.row_item_nguoi_dung,null);

        TextView txtma=convertView.findViewById(R.id.txtma);
        TextView txthoten=convertView.findViewById(R.id.txthoten);
        TextView txtgioitinh=convertView.findViewById(R.id.txtgioitinh);
        TextView txtphone=convertView.findViewById(R.id.txtphone);
        TextView txtemail=convertView.findViewById(R.id.txtemail);
        TextView txttentk=convertView.findViewById(R.id.txttentk);
        TextView txtmatkhau=convertView.findViewById(R.id.txtmatkhau);
        TextView txtquyen=convertView.findViewById(R.id.txtquyen);

        NguoiDung nguoiDung=mang.get(position);
        txtma.setText("Mã: "+nguoiDung.getManguoidung());
        txthoten.setText("Họ tên: "+nguoiDung.getHoten());
        txtgioitinh.setText("Giới tính: "+nguoiDung.getGioitinh());
        txtphone.setText("Phone: "+nguoiDung.getPhone());
        txtemail.setText("Email: "+nguoiDung.getEmail());
        txttentk.setText("Tên tài khoản: "+nguoiDung.getTentk());
        txtmatkhau.setText("Mật khẩu: "+nguoiDung.getMatkhau());
        txtquyen.setText("Quyền: "+nguoiDung.getQuyen());
        return convertView;
    }
}
