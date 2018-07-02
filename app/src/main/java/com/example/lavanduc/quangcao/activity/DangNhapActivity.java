package com.example.lavanduc.quangcao.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.Temp;

import ru.katso.livebutton.LiveButton;

public class DangNhapActivity extends AppCompatActivity {

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    EditText edtUsername, edtPassword;
    LiveButton btnDangNhap, btnHuy;
    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        init();
        addEvent();
    }

    private void init() {

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        btnDangNhap = (LiveButton) findViewById(R.id.btnDangNhap);
        btnHuy = (LiveButton) findViewById(R.id.btnHuy);

//        edtUsername.setText("manager");
//        edtPassword.setText("manager");
    }

    private void addEvent() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();
                if (name.equals("") || pass.equals("")) {
                    Toast.makeText(DangNhapActivity.this, "Mời nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CheckTK(name, pass)) {
                    Temp.USER=LayQuyen(name,pass);
                    dialog = new ProgressDialog(DangNhapActivity.this);
                    dialog.setTitle("Đăng nhập");
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
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                            if (!TempAccount.ACCOUNT.equals(TempAccount.BAN_LANH_DAO) &&
//                                    !TempAccount.ACCOUNT.equals(TempAccount.NHAN_VIEN_TO_CHUC)){
                                startActivity(new Intent(DangNhapActivity.this, QuanLyActivity.class));
//                            }else
                        }
                    }.start();
                    {

//                        startActivity(new Intent(DangNhapActivity.this, NavigationActivity.class));
                    }

                } else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean CheckTK(String user, String pas) {
        Cursor cursor = sqLiteDatabase.rawQuery("select tentk from NguoiDung where tentk='" + user + "' and matkhau='" + pas + "'", null);
        cursor.moveToFirst();
//        if(cursor.getCount()>0)
//            TempAccount.ACCOUNT=cursor.getString(0);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    private String LayQuyen(String user, String pas) {
        Cursor cursor = sqLiteDatabase.rawQuery("select quyen from NguoiDung where tentk='" + user + "' and matkhau='" + pas + "'", null);
        cursor.moveToFirst();
//        if(cursor.getCount()>0)
//            TempAccount.ACCOUNT=cursor.getString(0);
        return cursor.getString(0);
    }
}
