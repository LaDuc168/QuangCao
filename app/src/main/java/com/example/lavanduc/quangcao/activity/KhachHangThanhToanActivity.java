package com.example.lavanduc.quangcao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.KhachHang;
import com.example.lavanduc.quangcao.model.MyDatabase;

public class KhachHangThanhToanActivity extends AppCompatActivity {

    EditText edtmakhach,edthoten,edtngaysinh,edtdiachi;
    RadioButton rdNam,rdNu;
    Button btnDatPhong;
    String GIOITINH="Nam";
    String MA_KS="";
    String MA_PHONG="";

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang_thanh_toan);
        init();
        MA_KS=getIntent().getStringExtra("MAKS");
        MA_PHONG=getIntent().getStringExtra("MAP");
        addEvent();
    }

    private void addEvent() {
        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makhach=edtmakhach.getText().toString();
                String hoten=edthoten.getText().toString();
                String ngaysinh=edtngaysinh.getText().toString();
                String diachi=edtdiachi.getText().toString();

                KhachHang khachHang=new KhachHang(makhach,hoten,ngaysinh,diachi,MA_PHONG,MA_KS,GIOITINH);
                long check = DatPhong(khachHang);
                if(check>0){
                    Toast.makeText(KhachHangThanhToanActivity.this, "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(KhachHangThanhToanActivity.this, "Đặt phòng thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(KhachHangThanhToanActivity.this, MainActivity.class));
                finish();
            }
        });

        rdNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNam.setChecked(true);
                    GIOITINH="Nam";
                    rdNu.setChecked(false);
            }
        });
        rdNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNu.setChecked(true);
                    GIOITINH="Nam";
                    rdNam.setChecked(false);
            }
        });
    }

    private long DatPhong(KhachHang kh){
        ContentValues contentValues = new ContentValues();
        contentValues.put("makhach",kh.getMakhach());
        contentValues.put("hoten", kh.getHoten());
        contentValues.put("ngaysinh", kh.getNgaysinh());
        contentValues.put("diachi", kh.getDiachi());
        contentValues.put("maphong", kh.getMaphong());
        contentValues.put("maks", kh.getMaks());
        contentValues.put("gioitinh", kh.getGioitinh());


        return sqLiteDatabase.insert("KhachHang", null, contentValues);
    }

    private void init() {
        edtmakhach= (EditText) findViewById(R.id.edtmakhach);
        edthoten= (EditText) findViewById(R.id.edttenkhach);
        edtngaysinh= (EditText) findViewById(R.id.edtngaysinh);
        edtdiachi= (EditText) findViewById(R.id.edtdiachi);
        rdNam= (RadioButton) findViewById(R.id.radioNam);
        rdNu= (RadioButton) findViewById(R.id.radioNu);
        btnDatPhong= (Button) findViewById(R.id.btnDatPhong);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
