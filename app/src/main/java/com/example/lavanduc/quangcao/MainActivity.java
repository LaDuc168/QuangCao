package com.example.lavanduc.quangcao;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lavanduc.quangcao.activity.DangNhapActivity;
import com.example.lavanduc.quangcao.activity.DanhSachKhachSan;
import com.example.lavanduc.quangcao.activity.HoatDongGTActivity;
import com.example.lavanduc.quangcao.activity.KhachHangActivity;
import com.example.lavanduc.quangcao.activity.QuanLyActivity;
import com.example.lavanduc.quangcao.activity.SuaNguoiDungActivity;
import com.example.lavanduc.quangcao.adapter.RecyHDGT;
import com.example.lavanduc.quangcao.adapter.RecyKhachSan;
import com.example.lavanduc.quangcao.model.HoatDongGT;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBar;
    NavigationView navigationView;

    private ViewFlipper viewFlipper;
    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    RecyclerView khachsan, hoatdonggiaitri;
    ArrayList<KhachSan> mang;
    ArrayList<HoatDongGT> manghdgt;
    String []data_thanh_pho=null;

    CheckBox checkBoxKS,checkBoxHDGT;
    Spinner spinnerTP;

    ArrayAdapter adapterTP;
    Button btnTimKiem;

    String TP="";
    ProgressDialog dialog=null;

    TextView txtks,txthdgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data_thanh_pho = getResources().getStringArray(R.array.mang_thanh_pho);
        khoitao();
        ActionViewFlipper();

        addEvent();



    }

    private void addEvent() {
        spinnerTP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TP = data_thanh_pho[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Tìm kiếm thông tin");
                dialog.setMessage("Đang xử lý....");
                dialog.setCanceledOnTouchOutside(false);//click ra ngoài vẫn ko tắt
                dialog.show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Xử lý hoàn tất", Toast.LENGTH_SHORT).show();
//
                        if(checkBoxKS.isChecked()){
                            TimKiemKhachSan();
                        }else {
                            txtks.setVisibility(View.GONE);
                            mang.clear();
                            RecyKhachSan adRecyKhachSan = new RecyKhachSan(mang, getApplicationContext());
                            khachsan.setAdapter(adRecyKhachSan);
                        }

                        if(checkBoxHDGT.isChecked()){
                            TimKiemHDGT();
                        }else {
                            txthdgt.setVisibility(View.GONE);
                            manghdgt.clear();
                            RecyHDGT adRecyKhachSan1 = new RecyHDGT(manghdgt, getApplicationContext());
                            hoatdonggiaitri.setAdapter(adRecyKhachSan1);
                        }
                    }
                }.start();


            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<Integer> mangquangcao = new ArrayList<>();
        mangquangcao.add(R.drawable.mot);
        mangquangcao.add(R.drawable.hai);
        mangquangcao.add(R.drawable.ba);
        mangquangcao.add(R.drawable.bon);
        mangquangcao.add(R.drawable.nam);
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

    private void TimKiemKhachSan() {
        mang.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select maks,tenks,danhgia,anh from KhachSan where diadiem like '%"+TP+"%'",
                null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            int danhgia = cursor.getInt(2);
            byte[] anh = cursor.getBlob(3);

            KhachSan khachSan = new KhachSan(ma, ten, danhgia, anh);
            mang.add(khachSan);
        }
        if(mang.size()==0){
            txtks.setVisibility(View.GONE);
        }else {
            txtks.setVisibility(View.VISIBLE);
        }
        RecyKhachSan adRecyKhachSan = new RecyKhachSan(mang, getApplicationContext());
        khachsan.setAdapter(adRecyKhachSan);
    }

    private void TimKiemHDGT() {
        manghdgt.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select ma,luotthich,anh,ten from HoatDongGT where diadiem like '%"+TP+"%'",
                null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            int luotthich = cursor.getInt(1);
            byte[] anh = cursor.getBlob(2);
            String ten = cursor.getString(3);
            HoatDongGT hd = new HoatDongGT(ma, luotthich, anh, ten);

            manghdgt.add(hd);

        }
        if(manghdgt.size()==0){
            txthdgt.setVisibility(View.GONE);
        }else {
            txthdgt.setVisibility(View.VISIBLE);
        }
        RecyHDGT adRecyKhachSan1 = new RecyHDGT(manghdgt, getApplicationContext());
        hoatdonggiaitri.setAdapter(adRecyKhachSan1);
    }

    void khoitao() {
        txtks= (TextView) findViewById(R.id.txtks);
        txthdgt= (TextView) findViewById(R.id.txthdgt);
        txtks.setVisibility(View.GONE);
        txthdgt.setVisibility(View.GONE);

        checkBoxKS= (CheckBox) findViewById(R.id.checkboxKS);
        checkBoxHDGT= (CheckBox) findViewById(R.id.checkboxHDGT);
        spinnerTP= (Spinner) findViewById(R.id.spinnerTP);
        btnTimKiem= (Button) findViewById(R.id.btntimkiem);
        mang = new ArrayList<>();
        manghdgt = new ArrayList<>();
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

        //Khách san recyclerview
        khachsan = (RecyclerView) findViewById(R.id.recyclerviewKhachSan);
        hoatdonggiaitri = (RecyclerView) findViewById(R.id.recyclerviewHDGT);

        khachsan.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        khachsan.setLayoutManager(manager);


        //Hoạt dộng giải trí recyclerview
        hoatdonggiaitri.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        hoatdonggiaitri.setLayoutManager(manager1);


        adapterTP=new ArrayAdapter(this,android.R.layout.simple_list_item_1,data_thanh_pho);

        spinnerTP.setAdapter(adapterTP);
    }


    //<Action bar>
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBar.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//</Action bar>


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dangnhap) {
            startActivity(new Intent(MainActivity.this, DangNhapActivity.class));
        }
        if (id == R.id.nav_lichsu) {

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Nhập mã khách hàng của bạn");
            dialog.setContentView(R.layout.dialog_tim_kiem);
            dialog.show();

            final EditText edtTk = dialog.findViewById(R.id.edtmakhach);
            LiveButton btnTk = dialog.findViewById(R.id.btnOk);

            btnTk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chuoi = edtTk.getText().toString();
                    if (chuoi.equals("")) {
                        Toast.makeText(MainActivity.this, "Bạn chưa nhập mã khách hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean check = CheckMKH(chuoi);
                    if (check) {
                        Intent intent = new Intent(MainActivity.this, KhachHangActivity.class);
                        intent.putExtra("LICHSU", "OK");
                        intent.putExtra("MAKH", chuoi);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Mã khách không đúng", Toast.LENGTH_SHORT).show();
                    }

                }
            });
//            startActivity(new Intent(MainActivity.this, DangNhapActivity.class));

        }
        if (id == R.id.nav_khachsan) {
            startActivity(new Intent(MainActivity.this, DanhSachKhachSan.class));
        }
        if (id == R.id.nav_hdgt) {
            startActivity(new Intent(MainActivity.this, HoatDongGTActivity.class));
        }
        if (id == R.id.nav_ttncc) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thông tin nhà cung cấp");
            builder.setMessage("+ Họ tên: Đinh Kiên Tân" + "\n\n+ SĐT: 0941360203" + "\n\n+Gmail: tandkcntt@gmail.com" + "\n\n+ Facebook: https://www.facebook.com/profile.php?id=100003819038302");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();

        }
        if (id == R.id.nav_capnhat) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Cập nhật");
            builder.setMessage("Phiên bản hiện tại 1.0");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        }
        if (id == R.id.nav_suatt) {
//            Toast.makeText(this, "Sua th", Toast.LENGTH_SHORT).show();

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Nhập mã người dùng");
            dialog.setContentView(R.layout.dialog_tim_kiem);
            dialog.show();

            final EditText edtTk = dialog.findViewById(R.id.edtmakhach);
            LiveButton btnTk = dialog.findViewById(R.id.btnOk);
            edtTk.setHint("Nhập mã người dùng...");


            btnTk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chuoi = edtTk.getText().toString();
                    if (chuoi.equals("")) {
                        Toast.makeText(MainActivity.this, "Bạn chưa nhập mã người dùng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean check = CheckMND(chuoi);
                    if (check) {
                        Intent intent = new Intent(MainActivity.this, SuaNguoiDungActivity.class);
                        intent.putExtra("CHECK", "OK");
                        intent.putExtra("MAND", chuoi);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Mã người dùng không đúng", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        if (id == R.id.nav_thoat) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn muốn đăng xuất không?");
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    finish();
                    System.exit(0);
                }
            });
//        Dialog hopThoai=builder.create();
            builder.show();
        }
        return true;
    }

    private boolean CheckMKH(String m) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from KhachHang where makhach='" + m + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckMND(String m) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NguoiDung where manguoidung='" + m + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

}
