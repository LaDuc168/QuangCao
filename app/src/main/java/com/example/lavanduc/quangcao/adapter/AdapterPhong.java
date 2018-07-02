package com.example.lavanduc.quangcao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.NguoiDung;
import com.example.lavanduc.quangcao.model.PhongKS;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.lavanduc.quangcao.R.id.txtemail;
import static com.example.lavanduc.quangcao.R.id.txtgioitinh;
import static com.example.lavanduc.quangcao.R.id.txthoten;
import static com.example.lavanduc.quangcao.R.id.txtma;
import static com.example.lavanduc.quangcao.R.id.txtmaks;
import static com.example.lavanduc.quangcao.R.id.txtmaloai;
import static com.example.lavanduc.quangcao.R.id.txtmaphong;
import static com.example.lavanduc.quangcao.R.id.txtmota;
import static com.example.lavanduc.quangcao.R.id.txtmotachitiet;
import static com.example.lavanduc.quangcao.R.id.txtphone;
import static com.example.lavanduc.quangcao.R.id.txtquyen;
import static com.example.lavanduc.quangcao.R.id.txttentk;
import static com.example.lavanduc.quangcao.R.id.txtthoigianbd;
import static com.example.lavanduc.quangcao.R.id.txtthoigiankt;
import static com.example.lavanduc.quangcao.R.id.txttinhtrang;
import static com.example.lavanduc.quangcao.R.id.txtuudai;

/**
 * Created by LaVanDuc on 5/3/2018.
 */

public class AdapterPhong extends BaseAdapter {
    Context myContext;
    ArrayList<PhongKS> mang;

    public AdapterPhong(Context myContext, ArrayList<PhongKS> mang) {
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
        convertView=inflater.inflate(R.layout.row_item_phong,null);

        TextView maks=convertView.findViewById(txtmaks);
        TextView maphong=convertView.findViewById(txtmaphong);
        TextView thoigianbd=convertView.findViewById(txtthoigianbd);
        TextView thoigiankt=convertView.findViewById(txtthoigiankt);
        TextView tinhtrang=convertView.findViewById(txttinhtrang);
        TextView uudai=convertView.findViewById(txtuudai);
        TextView gia=convertView.findViewById(R.id.txtgia);
        TextView mota=convertView.findViewById(txtmota);
        TextView maloai=convertView.findViewById(txtmaloai);

        PhongKS p=mang.get(position);
        maks.setText("Mã khách sạn: "+p.getMaks());
        maphong.setText("Mã phòng: "+p.getMaphong());
        thoigianbd.setText("Thời gian bắt đầu: "+p.getThoigianbd()+" h");
        thoigiankt.setText("Thời gian kết thúc: "+p.getThoigiankt()+" h");
        tinhtrang.setText("TÌnh trạng: "+p.getTinhtrang());
        uudai.setText("Ưu đãi: "+p.getUudai()+" %");
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat= NumberFormat.getNumberInstance(locale);
        String money=numberFormat.format(p.getGia());
        gia.setText("Giá: "+money +" VNĐ");
        mota.setText("Mô tả: "+p.getMotachitiet());
        maloai.setText("Mã loại phòng: "+p.getMaloai());

        return convertView;
    }
}
