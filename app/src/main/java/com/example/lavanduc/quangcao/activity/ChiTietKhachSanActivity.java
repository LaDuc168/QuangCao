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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.adapter.AdapterBinhLuanKhachSan;
import com.example.lavanduc.quangcao.model.DichVuCC;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.example.lavanduc.quangcao.R.drawable.bl;
import static com.example.lavanduc.quangcao.R.id.beboico;
import static com.example.lavanduc.quangcao.R.id.buasangco;
import static com.example.lavanduc.quangcao.R.id.dieuhoaco;
import static com.example.lavanduc.quangcao.R.id.duadonco;
import static com.example.lavanduc.quangcao.R.id.wifico;

public class ChiTietKhachSanActivity extends AppCompatActivity {

    ImageView img;
    TextView txtten, txtdiadiem, txtdanhgia, txtdichvucungcap;
    ListView listView;
    ArrayList<KhachSan> mang;
    AdapterBinhLuanKhachSan adapterBinhLuanKhachSan;
    ArrayList<String> mangbl;
    Button btnDatPhong;
    ArrayList<ImageView> mangH;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    String MA_KS = "";

    ImageView imgwifi, imgbuasang, imgdieuhoa, imgbeboi, imgduadon;
    ImageView imgmot,imghai,imgba,imgbon,imgnam;

    TextView txtblok;

    EditText edtbl;
    Button btnguibl;

    String MA_BL="";
    TextView txtslPhong;

    ArrayList<Integer> mangHA;
    private ViewFlipper viewFlipper;
    String TEMP="";
    TextView txtbl;
    ArrayList<Integer> mangALLHA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_khach_san);
        MA_KS = getIntent().getStringExtra("MAKS");
        init();
        mangALLHA=new ArrayList<>();
        initHinhAnh();
        ActionViewFlipper();
        setData(MA_KS);

        TEMP=getIntent().getStringExtra("DUC");
        if (TEMP!=null) {
            btnDatPhong.setVisibility(View.GONE);
            edtbl.setVisibility(View.GONE);
            btnguibl.setVisibility(View.GONE);
            txtblok.setVisibility(View.GONE);
        }


        txtslPhong.setText("Tổng số phòng: "+DemSLPhong(MA_KS)+" phòng"+" \n\nSố phòng trống: "+DemSLPhongTrong(MA_KS)+" phòng");
        addEvent();
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

    private void addEvent() {
        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietKhachSanActivity.this, DatPhongActivity.class);
                intent.putExtra("MAKS", MA_KS);
                startActivity(intent);
            }
        });

        btnguibl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bl=edtbl.getText().toString();
                if(bl.equals("")){
                    Toast.makeText(ChiTietKhachSanActivity.this, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
                    return;
                }

                long check = ThemBL(bl);
                if(check>0){
                    Toast.makeText(ChiTietKhachSanActivity.this, "Bình luận của bạn được ghi nhận", Toast.LENGTH_SHORT).show();
                    LayBinhLuan(MA_BL);
                    edtbl.setText("");
                }else {
                    Toast.makeText(ChiTietKhachSanActivity.this, "Bình luận thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private long ThemBL(String  nd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("binhluan", MA_BL);
        contentValues.put("stt",(1+ new Random().nextInt(5321)));
        contentValues.put("noidungbl",nd);

       return sqLiteDatabase.insert("BinhLuanKS", null, contentValues);

    }


    private void init() {
        txtblok= (TextView) findViewById(R.id.txtbl);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        mangHA=new ArrayList<>();
        mangHA.add(R.drawable.mot);
        mangHA.add(R.drawable.hai);
        mangHA.add(R.drawable.ba);
        mangHA.add(R.drawable.bon);
        mangHA.add(R.drawable.nam);

        txtslPhong= (TextView) findViewById(R.id.txtslphong);
        mangH=new ArrayList<>();
        imgmot= (ImageView) findViewById(R.id.imgmot);
        imghai= (ImageView) findViewById(R.id.imghai);
        imgba= (ImageView) findViewById(R.id.imgba);
        imgbon= (ImageView) findViewById(R.id.imgbon);
        imgnam= (ImageView) findViewById(R.id.imgnam);
        mangH.add(imgmot);
        mangH.add(imghai);
        mangH.add(imgba);
        mangH.add(imgbon);
        mangH.add(imgnam);

        img = (ImageView) findViewById(R.id.imgks);
        txtten = (TextView) findViewById(R.id.txttenks);
        txtdiadiem = (TextView) findViewById(R.id.txtdiadiemks);
        txtdanhgia = (TextView) findViewById(R.id.txtdanhgiaks);
        txtdichvucungcap = (TextView) findViewById(R.id.txtdichvucungcapks);
        listView = (ListView) findViewById(R.id.listViewBinhLuan);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mang = new ArrayList<>();

        mangbl = new ArrayList<>();
        adapterBinhLuanKhachSan = new AdapterBinhLuanKhachSan(this, mangbl,mangHA);
        listView.setAdapter(adapterBinhLuanKhachSan);

        btnDatPhong = (Button) findViewById(R.id.btnDatPhong);

        imgwifi = (ImageView) findViewById(R.id.imgwifi);
        imgbuasang = (ImageView) findViewById(R.id.imgbuasang);
        imgdieuhoa = (ImageView) findViewById(R.id.imgdieuhoa);
        imgbeboi = (ImageView) findViewById(R.id.imgbeboi);
        imgduadon = (ImageView) findViewById(R.id.imgduadon);
        txtbl= (TextView) findViewById(R.id.txtbinhluan);
        edtbl= (EditText) findViewById(R.id.edtnhapbinhluan);
        btnguibl= (Button) findViewById(R.id.btnguibl);
    }

    private void setData(String ma) {
        mang.clear();
//        Toast.makeText(this, "co vao="+ma, Toast.LENGTH_SHORT).show();
        Cursor cursor = sqLiteDatabase.rawQuery("select tenks,danhgia,diadiem,dichvucungcap,anh,binhluan from KhachSan where maks='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            int danhgia = cursor.getInt(1);
            String diadiem = cursor.getString(2);
            String dv = cursor.getString(3);
            byte[] anh = cursor.getBlob(4);
            String binhluan = cursor.getString(5);

            MA_BL=binhluan;
            Bitmap bitmap = null;
            if (anh != null) {
                bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);

                img.setImageBitmap(bitmap);
            }
            txtten.setText(ten);
            txtdiadiem.setText(diadiem);
            txtdanhgia.setText(danhgia + " Sao");
            for (int j = 0; j < danhgia; j++) {
                mangH.get(j).setVisibility(View.VISIBLE);
            }

            Set<DichVuCC> mangcc = DichVuCungCap(dv);
            Set<String> loc = new HashSet<>();
            for (DichVuCC d : mangcc
                    ) {
                loc.add(d.getTendichvu());
            }
            String s = "+ ";
            for (String dvcc : loc
                    ) {
                s += dvcc + "\n"+"\n"+"+ ";
                SetHA(dvcc);
            }
            int vt = s.lastIndexOf("+");
            s=s.substring(0,vt);
            txtdichvucungcap.setText(s);



            ///Lấy bình luận
            LayBinhLuan(binhluan);
//            KhachSan khachSan=new KhachSan(ten,danhgia,diadiem,anh);
//            mang.add(khachSan);
        }
    }

    private void SetHA(String s){
        if(s.equals("Wifi")){
            imgwifi.setVisibility(View.VISIBLE);
        }
        if(s.equals("Bữa sáng")){
            imgbuasang.setVisibility(View.VISIBLE);
        }
        if(s.equals("Điều hòa")){
            imgdieuhoa.setVisibility(View.VISIBLE);
        }
        if(s.equals("Bể bơi")){
            imgbeboi.setVisibility(View.VISIBLE);
        }
        if(s.equals("Đưa đón")){
            imgduadon.setVisibility(View.VISIBLE);
        }
    }

    private Set<DichVuCC> DichVuCungCap(String ma) {
        Set<DichVuCC> mangdv = new HashSet<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from DichVuCC where dichvucc='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String dichvu = cursor.getString(0);
            int stt = cursor.getInt(1);
            String tendv = cursor.getString(2);

            DichVuCC dv = new DichVuCC(dichvu, stt, tendv);
            mangdv.add(dv);

        }
        return mangdv;
    }

    private void LayBinhLuan(String bl) {
        mangbl.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select noidungbl from BinhLuanKS where binhluan='" + bl + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String blks = cursor.getString(0);
            mangbl.add(blks);
        }
        txtbl.setText("Bình luận ( "+cursor.getCount()+" )");
        adapterBinhLuanKhachSan.notifyDataSetChanged();
    }

    private int DemSLPhong(String mks) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from PhongKS where maks='" + mks + "'", null);

        return cursor.getCount();
    }
    private int DemSLPhongTrong(String mks) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from PhongKS where maks='" + mks + "' and tinhtrang='Chưa ai đặt'", null);

        return cursor.getCount();
    }
}
