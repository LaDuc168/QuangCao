package com.example.lavanduc.quangcao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.KhachHang;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.NguoiDung;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

import static com.example.lavanduc.quangcao.R.id.spinner;

public class SuaKhachHangActivity extends AppCompatActivity {

    EditText edtmakhach, edthoten, edtngaysinh, edtdiachi;
    Spinner spinnermaphong, spinnermaks;
    RadioButton rdNam, rdNu;
    LiveButton btnSua, btnHuy;
    String MALE = "Nữ";
    String MA_PHONG = "MP01";
    String MAKS = "MKS01";
    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    ArrayAdapter adaptermaphong, adaptermakhachsan;
    ArrayList<String> mangphong, mangks;

    KhachHang kh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_khach_hang);
        init();

        kh = (KhachHang) getIntent().getSerializableExtra("KH");
        setMaKS();
        setValue();
        addEvent();
    }

    private void setValue() {
        edtmakhach.setText(kh.getMakhach());
        edthoten.setText(kh.getHoten());
        edtngaysinh.setText(kh.getNgaysinh());
        edtdiachi.setText(kh.getDiachi());
        if (kh.getGioitinh().equals("Nam")) {
            rdNam.setChecked(true);
            rdNu.setChecked(false);
        } else if (kh.getGioitinh().equals("Nữ")) {
            rdNam.setChecked(false);
            rdNu.setChecked(true);
        }
        String name = getTenKS(kh.getMaks());
        String kq = kh.getMaks() + "-" + name;
        mangks.add(0, kq);
        adaptermakhachsan = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangks);
        spinnermaks.setAdapter(adaptermakhachsan);


    }

    private void addEvent() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String makhach = edtmakhach.getText().toString();
                String hoten = edthoten.getText().toString();
                String ngaysinh = edtngaysinh.getText().toString();
                String diachi = edtdiachi.getText().toString();

                KhachHang khachHang = new KhachHang(makhach, hoten, ngaysinh, diachi, MA_PHONG, MAKS, MALE);

                long check = SuaKhachHang(khachHang);
                if(check>0){
                    Toast.makeText(SuaKhachHangActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SuaKhachHangActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(SuaKhachHangActivity.this,KhachHangActivity.class));
                finish();

            }
        });
        rdNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNam.setChecked(true);
                MALE = "Nam";
                rdNu.setChecked(false);
            }
        });
        rdNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdNu.setChecked(true);
                MALE = "Nữ";
                rdNam.setChecked(false);
            }
        });
        spinnermaphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MA_PHONG = mangphong.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnermaks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                MAHANGHOA = mangMaHH.get(i);
                String kq = mangks.get(i);
                String s[] = kq.split("-");
                setMaPhong(s[0]);
                MAKS = s[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private long SuaKhachHang(KhachHang nd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("makhach",nd.getMakhach());
        contentValues.put("hoten",nd.getHoten() );
        contentValues.put("ngaysinh",nd.getNgaysinh() );
        contentValues.put("diachi",nd.getDiachi() );
        contentValues.put("maphong", nd.getMaphong());
        contentValues.put("maks",nd.getMaks() );
        contentValues.put("gioitinh",nd.getGioitinh() );

        return sqLiteDatabase.update("KhachHang", contentValues, "makhach=?", new String[]{
                nd.getMakhach()
        });
    }
    private void init() {
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        edtmakhach = (EditText) findViewById(R.id.edtma);
        edthoten = (EditText) findViewById(R.id.edthoten);
        edtngaysinh = (EditText) findViewById(R.id.edtngaysinh);
        edtdiachi = (EditText) findViewById(R.id.edtdiachi);
        rdNam = (RadioButton) findViewById(R.id.radioNam);
        rdNu = (RadioButton) findViewById(R.id.radioNu);
        btnSua = (LiveButton) findViewById(R.id.btnSua);
        btnHuy = (LiveButton) findViewById(R.id.btnHuy);
        spinnermaphong = (Spinner) findViewById(R.id.spinnermaphong);
        spinnermaks = (Spinner) findViewById(R.id.spinnermaks);

        mangphong = new ArrayList<>();
        mangks = new ArrayList<>();


    }

    private void setMaPhong(String ma) {
        mangphong.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select maphong from PhongKS where maks='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String maphong = cursor.getString(0);
            mangphong.add(maphong);
        }
        adaptermaphong = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mangphong);
        spinnermaphong.setAdapter(adaptermaphong);
    }

    private ArrayList<String> setMaKS() {
        mangks.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select maks,tenks from KhachSan", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String maks = cursor.getString(0);
            String tenks = cursor.getString(1);
            mangks.add(maks + "-" + tenks);
        }
        return mangks;
    }

    private String getTenKS(String ma) {
//        mangks.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select tenks from KhachSan where maks='" + ma + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            return ten;
        }
        return "";
    }
}
