package com.example.lavanduc.quangcao.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.adapter.AdapterBinhLuanKhachSan;
import com.example.lavanduc.quangcao.model.HoatDongGT;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.PhongKS;

import java.util.ArrayList;
import java.util.Random;

public class HienThiChiTietHDGTActivity extends AppCompatActivity {

    TextView txtten, txtluotthich, txtdiadiem, txtthoigianbd, txtthoigiankt, txtmota;
    ImageView img;

    ListView listView;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    private ViewFlipper viewFlipper;

    String MA = "";

    AdapterBinhLuanKhachSan adapterBinhLuanKhachSan;
    ArrayList<String> mangbl;
    ArrayList<Integer> mangHA;
    TextView txtbl;

    ArrayList<Integer> mangALLHA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_chi_tiet_hdgt);
        init();
        MA = getIntent().getStringExtra("MA");
        LayThongHDGT(MA);

        initHinhAnh();
        ActionViewFlipper();
    }

    private void initHinhAnh() {
        mangALLHA.add(R.drawable.mot_gt);
        mangALLHA.add(R.drawable.hai_gt);
        mangALLHA.add(R.drawable.ba_gt);
        mangALLHA.add(R.drawable.bon_gt);
        mangALLHA.add(R.drawable.nam_gt);
        mangALLHA.add(R.drawable.sau_gt);
        mangALLHA.add(R.drawable.bay_gt);
        mangALLHA.add(R.drawable.tam_gt);
        mangALLHA.add(R.drawable.chin_gt);
        mangALLHA.add(R.drawable.mmot_gt);
    }

    private void ActionViewFlipper() {
        ArrayList<Integer> mangquangcao = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            mangquangcao.add(mangALLHA.get(0 + new Random().nextInt(mangALLHA.size() - 1)));
        }
//        mangquangcao.add(mangALLHA.get(0+new Random().nextInt(mangALLHA.size()-1)));
//        mangquangcao.add(R.drawable.ba_gt);
//        mangquangcao.add(R.drawable.bon_gt);
//        mangquangcao.add(R.drawable.nam_gt);
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(mangquangcao.get(i));
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(3000);//Chạy trong 5s
        viewFlipper.setAutoStart(true);//cho view flipper tự chạy
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void init() {
        mangALLHA = new ArrayList<>();
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        mangHA = new ArrayList<>();
        mangHA.add(R.drawable.mot);
        mangHA.add(R.drawable.hai);
        mangHA.add(R.drawable.ba);
        mangHA.add(R.drawable.bon);
        mangHA.add(R.drawable.nam);
        txtdiadiem = (TextView) findViewById(R.id.txtdiadiem);
        txtluotthich = (TextView) findViewById(R.id.txtluotthich);
        txtthoigianbd = (TextView) findViewById(R.id.txtthoigianbd);
        txtthoigiankt = (TextView) findViewById(R.id.txtthoigiankt);
        txtten = (TextView) findViewById(R.id.txttenhdgt);
        listView = (ListView) findViewById(R.id.listViewBinhLuan);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        img = (ImageView) findViewById(R.id.imggt);
        txtmota = (TextView) findViewById(R.id.txtmotachitiet);
        mangbl = new ArrayList<>();
        adapterBinhLuanKhachSan = new AdapterBinhLuanKhachSan(this, mangbl, mangHA);
        listView.setAdapter(adapterBinhLuanKhachSan);
        txtbl = (TextView) findViewById(R.id.txtbinhluan);
    }

    private void LayThongHDGT(String ma) {

        PhongKS phongKS = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from HoatDongGT where ma='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);


            String thoigianbd = cursor.getString(1);
            String thoigiankt = cursor.getString(2);
            String motachitiet = cursor.getString(3);
            String binhluan = cursor.getString(4);
            int luotthich = cursor.getInt(5);
            String diadiem = cursor.getString(6);
            byte[] anh = cursor.getBlob(7);
            String ten = cursor.getString(8);

            txtten.setText(ten);
            txtdiadiem.setText(diadiem);
            txtthoigiankt.setText("Thời gian kết thúc: " + thoigiankt + " h");
            txtthoigianbd.setText("Thời gian bắt đầu: " + thoigianbd + " h");
            txtluotthich.setText(luotthich + " lượt thích");
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            img.setImageBitmap(bitmap);
            txtmota.setText(motachitiet);

            LayBinhLuan(binhluan);
        }
    }

    private void LayBinhLuan(String bl) {
        mangbl.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select noidung from BinhLuanGT where binhluan='" + bl + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String blks = cursor.getString(0);
            mangbl.add(blks);
        }
        txtbl.setText("Bình luận ( " + cursor.getCount() + " )");
        adapterBinhLuanKhachSan.notifyDataSetChanged();
    }
}
