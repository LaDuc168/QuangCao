package com.example.lavanduc.quangcao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.NguoiDung;

import ru.katso.livebutton.LiveButton;

public class ThemNguoiDungActivity extends AppCompatActivity {

    EditText edtma,edthoten,edtphone,edtemail,edttentk,edtmk;
    RadioButton rdNam,rdNu,rdUser,rdAdmin;
    LiveButton btnThem,btnHuy;
    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    String MALE="Nam";
    String QUYEN="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        init();

        addEvent();

    }

    private void addEvent() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma=edtma.getText().toString();
                String hoten=edthoten.getText().toString();
                String phone=edtphone.getText().toString();
                String email=edtemail.getText().toString();
                String tentk=edttentk.getText().toString();
                String mk=edtmk.getText().toString();

                boolean checktk = CheckTK(tentk);
                if(checktk){
                    Toast.makeText(ThemNguoiDungActivity.this, "Tên tài khoản đã có người sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }

                NguoiDung nguoiDung=new NguoiDung(ma,hoten,MALE,phone,email,tentk,mk,QUYEN);


                long check = ThemNguoiDung(nguoiDung);
                if(check>0){
                    Toast.makeText(ThemNguoiDungActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ThemNguoiDungActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                }
                startActivity(new Intent(ThemNguoiDungActivity.this,NguoiDungActivity.class));
                finish();
            }
        });
        rdNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNam.setChecked(true);
                MALE="Nam";
                rdNu.setChecked(false);
            }
        });
        rdNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNu.setChecked(true);
                MALE="Nữ";
                rdNam.setChecked(false);
            }
        });
        rdUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdUser.setChecked(true);
                QUYEN="User";
                rdAdmin.setChecked(false);
            }
        });
        rdAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdAdmin.setChecked(true);
                QUYEN="Admin";
                rdUser.setChecked(false);
            }
        });

    }

    private boolean CheckTK(String user) {
        Cursor cursor = sqLiteDatabase.rawQuery("select quyen from NguoiDung where tentk='" + user + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
    private long ThemNguoiDung(NguoiDung nd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("manguoidung",nd.getManguoidung());
        contentValues.put("hoten",nd.getHoten() );
        contentValues.put("gioitinh",nd.getGioitinh() );
        contentValues.put("phone",nd.getPhone() );
        contentValues.put("email", nd.getEmail());
        contentValues.put("tentk",nd.getTentk() );
        contentValues.put("matkhau",nd.getMatkhau() );
        contentValues.put("quyen", nd.getQuyen());

       return sqLiteDatabase.insert("NguoiDung", null, contentValues);
    }

    private void init() {
        edtma= (EditText) findViewById(R.id.edtma);
        edthoten= (EditText) findViewById(R.id.edthoten);
        edtphone= (EditText) findViewById(R.id.edtphone);
        edtemail= (EditText) findViewById(R.id.edtemail);
        edttentk= (EditText) findViewById(R.id.edttentk);
        edtmk= (EditText) findViewById(R.id.edtmk);
        rdNam= (RadioButton) findViewById(R.id.radioNam);
        rdNu= (RadioButton) findViewById(R.id.radioNu);
        rdUser= (RadioButton) findViewById(R.id.radiouser);
        rdAdmin= (RadioButton) findViewById(R.id.radioadmin);
        btnThem= (LiveButton) findViewById(R.id.btnThem);
        btnHuy= (LiveButton) findViewById(R.id.btnHuy);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
