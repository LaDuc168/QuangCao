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

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.NguoiDung;

import ru.katso.livebutton.LiveButton;

public class SuaNguoiDungActivity extends AppCompatActivity {

    EditText edtma, edthoten, edtphone, edtemail, edttentk, edtmk;
    RadioButton rdNam, rdNu, rdUser, rdAdmin;
    LiveButton btnThem, btnHuy;
    String MAND = "";
    String CK = null;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    NguoiDung nd = null;

    String MALE="Nam";
    String QUYEN="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nguoi_dung2);
        init();
        MAND = getIntent().getStringExtra("MAND");
        CK = getIntent().getStringExtra("CHECK");
        nd=getNguoiDUng(MAND);

        setValue();

        addEvent();

    }

    private void addEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma=edtma.getText().toString();
                String hoten=edthoten.getText().toString();
                String phone=edtphone.getText().toString();
                String email=edtemail.getText().toString();
                String tentk=edttentk.getText().toString();
                String mk=edtmk.getText().toString();

                NguoiDung nguoiDung=new NguoiDung(ma,hoten,MALE,phone,email,tentk,mk,QUYEN);
                long check = SuaNguoiDung(nguoiDung);
                if(check>0){
                    Toast.makeText(SuaNguoiDungActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SuaNguoiDungActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();

                }
                if(CK!=null){
                    startActivity(new Intent(SuaNguoiDungActivity.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SuaNguoiDungActivity.this,NguoiDungActivity.class));
                    finish();
                }


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private long SuaNguoiDung(NguoiDung nd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("manguoidung",nd.getManguoidung());
        contentValues.put("hoten",nd.getHoten() );
        contentValues.put("gioitinh",nd.getGioitinh() );
        contentValues.put("phone",nd.getPhone() );
        contentValues.put("email", nd.getEmail());
        contentValues.put("tentk",nd.getTentk() );
        contentValues.put("matkhau",nd.getMatkhau() );
        contentValues.put("quyen", nd.getQuyen());

        return sqLiteDatabase.update("NguoiDung", contentValues, "manguoidung=?", new String[]{
                nd.getManguoidung()
        });
    }

    private void setValue() {
        edtma.setText(nd.getManguoidung());
        edthoten.setText(nd.getHoten());
        edtphone.setText(nd.getPhone());
        edtemail.setText(nd.getEmail());
        edttentk.setText(nd.getTentk());
        edtmk.setText(nd.getMatkhau());
        if(nd.getGioitinh().equals("Nam")){
            rdNam.setChecked(true);
            rdNu.setChecked(false);
        }else if(nd.getGioitinh().equals("Nữ")){
            rdNam.setChecked(false);
            rdNu.setChecked(true);
        }

        if(nd.getQuyen().equals("User")){
            rdUser.setChecked(true);
            rdAdmin.setChecked(false);
        }else if(nd.getQuyen().equals("Admin")){
            rdUser.setChecked(false);
            rdAdmin.setChecked(true);
        }
    }

    private NguoiDung getNguoiDUng(String mand) {
        NguoiDung khachSan = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NguoiDung where manguoidung='" + mand + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String gioitinh = cursor.getString(2);
            String phone = cursor.getString(3);
            String email = cursor.getString(4);
            String tentk = cursor.getString(5);
            String mk = cursor.getString(6);
            String quyen = cursor.getString(7);

            khachSan = new NguoiDung(ma, ten, gioitinh, phone, email, tentk, mk, quyen);
        }
        return khachSan;
    }

    private void init() {
        edtma = (EditText) findViewById(R.id.edtma);
        edthoten = (EditText) findViewById(R.id.edthoten);
        edtphone = (EditText) findViewById(R.id.edtphone);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edttentk = (EditText) findViewById(R.id.edttentk);
        edtmk = (EditText) findViewById(R.id.edtmk);
        rdNam = (RadioButton) findViewById(R.id.radioNam);
        rdNu = (RadioButton) findViewById(R.id.radioNu);
        rdUser = (RadioButton) findViewById(R.id.radiouser);
        rdAdmin = (RadioButton) findViewById(R.id.radioadmin);
        btnThem = (LiveButton) findViewById(R.id.btnThem);
        btnHuy = (LiveButton) findViewById(R.id.btnHuy);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
