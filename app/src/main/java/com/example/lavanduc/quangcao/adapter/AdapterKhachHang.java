package com.example.lavanduc.quangcao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.KhachHang;
import com.example.lavanduc.quangcao.model.NguoiDung;

import java.util.ArrayList;

import static com.example.lavanduc.quangcao.R.id.txtemail;
import static com.example.lavanduc.quangcao.R.id.txtmatkhau;
import static com.example.lavanduc.quangcao.R.id.txtphone;
import static com.example.lavanduc.quangcao.R.id.txtquyen;
import static com.example.lavanduc.quangcao.R.id.txttentk;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class AdapterKhachHang extends BaseAdapter {
    Context myContext;
    ArrayList<KhachHang> mang;

    public AdapterKhachHang(Context myContext, ArrayList<KhachHang> mang) {
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
        convertView=inflater.inflate(R.layout.row_item_khach_hang,null);

        TextView txtma=convertView.findViewById(R.id.txtma);
        TextView txthoten=convertView.findViewById(R.id.txthoten);
        TextView txtgioitinh=convertView.findViewById(R.id.txtgioitinh);
        TextView ngaysinh=convertView.findViewById(R.id.txtngaysinh);
        TextView txtmaphong=convertView.findViewById(R.id.txtmaphong);
        TextView txtmaks=convertView.findViewById(R.id.txtmaks);
        TextView diachi=convertView.findViewById(R.id.txtdiachi);

        KhachHang nguoiDung=mang.get(position);
        txtma.setText("Mã: "+nguoiDung.getMakhach());
        txthoten.setText("Họ tên: "+nguoiDung.getHoten());
        txtgioitinh.setText("Giới tính: "+nguoiDung.getGioitinh());
        ngaysinh.setText("Ngày sinh: "+nguoiDung.getNgaysinh());
        txtmaphong.setText("Mã phòng: "+nguoiDung.getMaphong());
        txtmaks.setText("Mã khách sạn: "+nguoiDung.getMaks());
        diachi.setText("Địa chỉ: "+nguoiDung.getDiachi());

        return convertView;
    }
}
