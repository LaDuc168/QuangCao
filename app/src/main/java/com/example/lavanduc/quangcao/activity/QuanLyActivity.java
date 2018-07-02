package com.example.lavanduc.quangcao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lavanduc.quangcao.R;

import static com.example.lavanduc.quangcao.R.id.btnkhachshang;

public class QuanLyActivity extends AppCompatActivity {

    Button btnkhachsan,btnphong,btnnguoidung,btnkhachhang,btnhdgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        init();
        addEvent();
    }

    private void addEvent() {
        btnkhachsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuanLyActivity.this,DanhSachKhachSan.class);
                intent.putExtra("DUC","COOKEI");
                startActivity(intent);
            }
        });
        btnphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanLyActivity.this,DanhSachPhongActivity.class));
            }
        });
        btnnguoidung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanLyActivity.this,NguoiDungActivity.class));
            }
        });
        btnkhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanLyActivity.this,KhachHangActivity.class));
            }
        });
        btnhdgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanLyActivity.this,HoatDongGTActivity.class));
            }
        });
    }

    private void init() {
        btnkhachsan= (Button) findViewById(R.id.btnkhachsan);
        btnphong= (Button) findViewById(R.id.btnphong);
        btnnguoidung= (Button) findViewById(R.id.btnnguoidung);
        btnkhachhang= (Button) findViewById(btnkhachshang);
        btnhdgt= (Button) findViewById(R.id.btnhdgt);
    }


}
