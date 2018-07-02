package com.example.lavanduc.quangcao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.DichVuCC;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.PhongKS;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.media.CamcorderProfile.get;

public class DatPhongActivity extends AppCompatActivity {


    ImageView imgdatphong;
    TextView txtthoigianbd, txtthoigiankt, txttinhtrang, txtuudai, txtgia, txtmotachitiet;
    Spinner spinner;
    Button btnDatPhong;
    String MA_KS = "";
    String MA_PHONG = "";
    ScrollView scrollView;


    ArrayList<String> mangMaPhong;
    ArrayAdapter adapter;
    ArrayList<Integer> mangALLHA;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    private ViewFlipper viewFlipper;
    String TINH_TRANG="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_phong);
        init();
        MA_KS = getIntent().getStringExtra("MAKS");

        addEvent();
        setMaPhong(MA_KS);

        mangALLHA=new ArrayList<>();
        initHinhAnh();
        ActionViewFlipper();

    }
    private void ActionViewFlipper() {
        ArrayList<Integer> mangquangcao = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            mangquangcao.add(mangALLHA.get(0 + new Random().nextInt(mangALLHA.size() - 1)));
        }

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

    private void initHinhAnh() {

        mangALLHA.add(R.drawable.mot);
        mangALLHA.add(R.drawable.hai);
        mangALLHA.add(R.drawable.ba);
        mangALLHA.add(R.drawable.bon);
        mangALLHA.add(R.drawable.nam);
        mangALLHA.add(R.drawable.mot_ks);
        mangALLHA.add(R.drawable.hai_ks);
        mangALLHA.add(R.drawable.ba_ks);
        mangALLHA.add(R.drawable.bon_ks);
    }

    private void addEvent() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=mangMaPhong.get(i);
                String m[]=s.split("-");
                PhongKS pb = LayThongTinMaPhong(MA_KS,m[0] );

                txtthoigianbd.setText("Thời gian bắt đầu: " + pb.getThoigianbd() + " h");
                txtthoigiankt.setText("Thời gian kết thúc: " + pb.getThoigiankt() + " h");
                txttinhtrang.setText("Tình trạng: " + pb.getTinhtrang());
                TINH_TRANG=pb.getTinhtrang();
                txtuudai.setText("Giảm: " + pb.getUudai() + " %");
                Locale locale = new Locale("vi", "VN");
                NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
                String gia = numberFormat.format(pb.getGia());

                txtgia.setText("Giá: " + gia + " VNĐ");
                txtmotachitiet.setText(pb.getMotachitiet());
                Bitmap bitmap = null;
                if (pb.getAnh()!=null ) {

                    bitmap = BitmapFactory.decodeByteArray(pb.getAnh(), 0, pb.getAnh().length);
                    imgdatphong.setImageBitmap(bitmap);
                }
//                scrollView.setVisibility(View.VISIBLE);
//                btnDatPhong.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TINH_TRANG!=null){
                    if(TINH_TRANG.equals("Có người đặt")){
                        Toast.makeText(DatPhongActivity.this, "Phòng này đã có ngưới đặt", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                long check=SuaPhong(new PhongKS(MA_KS,MA_PHONG,"Có người đặt"));
                if(check>0){
                    Toast.makeText(DatPhongActivity.this, "Phòng "+MA_PHONG+" được cập nhật", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DatPhongActivity.this, "Phòng "+MA_PHONG+" không cập nhật", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(DatPhongActivity.this, KhachHangThanhToanActivity.class);
                intent.putExtra("MAKS", MA_KS);
                intent.putExtra("MAP", MA_PHONG);
                startActivity(intent);
            }
        });


    }

    private long SuaPhong(PhongKS p){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maks",p.getMaks() );
        contentValues.put("maphong",p.getMaphong() );
        contentValues.put("tinhtrang", p.getTinhtrang());

        return sqLiteDatabase.update("PhongKS", contentValues, "maks=? and maphong=?", new String[]{
                p.getMaks(),p.getMaphong()
        });
    }

    private void init() {
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        imgdatphong = (ImageView) findViewById(R.id.imgdatphong);
        txtthoigianbd = (TextView) findViewById(R.id.txtthoigianbd);
        txtthoigiankt = (TextView) findViewById(R.id.txtthoigiankt);
        txttinhtrang = (TextView) findViewById(R.id.txttinhtrang);
        txtuudai = (TextView) findViewById(R.id.txtuudai);
        txtgia = (TextView) findViewById(R.id.txtgia);
        txtmotachitiet = (TextView) findViewById(R.id.txtmotachitiet);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnDatPhong = (Button) findViewById(R.id.btnDatPhong);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mangMaPhong = new ArrayList<>();
        scrollView = (ScrollView) findViewById(R.id.motachitiet);


    }

    private PhongKS LayThongTinMaPhong(String ma, String mp) {
//        mangMaPhong.clear();
        MA_PHONG = mp;
        PhongKS phongKS = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from PhongKS where maks='" + ma + "' and maphong='" + mp + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            String maks = cursor.getString(0);
            String maphog = cursor.getString(1);
            String timebd = cursor.getString(2);
            String timekt = cursor.getString(3);
            String tinhtrang = cursor.getString(4);
            int uudai = cursor.getInt(5);
            int gia = cursor.getInt(6);
            String mota = cursor.getString(7);
            byte[] anh = cursor.getBlob(8);
            phongKS = new PhongKS(maks, maphog, timebd, timekt, tinhtrang, uudai, gia, mota, anh);

        }
        return phongKS;
    }

    private void setMaPhong(String ma) {
        mangMaPhong.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select maphong,maloai from PhongKS where maks='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String maphong = cursor.getString(0);
            String maloai = cursor.getString(1);

            String tenl = layTenPhong(maloai);

            mangMaPhong.add(maphong+"-"+tenl);

        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangMaPhong);
        spinner.setAdapter(adapter);
    }

    private String  layTenPhong(String ma) {
        Cursor cursor = sqLiteDatabase.rawQuery("select tenloai from LoaiPhong where maloai='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
           return ten;
        }
       return "";
    }
}
