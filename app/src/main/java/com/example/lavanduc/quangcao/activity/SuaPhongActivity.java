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
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.PhongKS;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

public class SuaPhongActivity extends AppCompatActivity {

    EditText edtmaks,edtmaphong,edtthoigianbd,edtthoigiankt,edttinhtrang,edtuudai,edtgia,edtmota;
    LiveButton btnThem,btnHuy;
    ArrayList<String> mangks;
    ArrayAdapter adapter;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    String  MAKS="";
    PhongKS pp=null;

    Spinner spinnerloaiphong;
    ArrayList<String > mangloaiphong;
    ArrayAdapter adapterloai;

    String LOAI_PHONG="";

    String TINH_TRANG="";

    RadioButton rdchuathue,rdduocthue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phong);

        init();

        pp= (PhongKS) getIntent().getSerializableExtra("PH");
        layLoaiphong();
        setValue();
        addEvent();
    }

    private void setValue() {
        edtmaks.setText(pp.getMaks());
        edtmaphong.setText(pp.getMaphong());
        edtthoigianbd.setText(pp.getThoigianbd());
        edtthoigiankt.setText(pp.getThoigiankt());

        edtuudai.setText(pp.getUudai()+"");
        edtgia.setText(pp.getGia()+"");
        edtmota.setText(pp.getMotachitiet());

        String ten = layTenPhong(pp.getMaloai());
        mangloaiphong.add(0,pp.getMaloai()+"-"+ten);

        if(pp.getTinhtrang().equals("Chưa ai đặt")){
            rdchuathue.setChecked(true);
        }
        if(pp.getTinhtrang().equals("Có người đặt")){
            rdduocthue.setChecked(true);
        }
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

    private void addEvent() {
        rdduocthue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdduocthue.setChecked(true);
                TINH_TRANG="Có người đặt";
                rdchuathue.setChecked(false);
            }
        });
        rdchuathue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdchuathue.setChecked(true);
                TINH_TRANG="Chưa ai đặt";
                rdduocthue.setChecked(false);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maphong=edtmaphong.getText().toString();
                String maks=edtmaks.getText().toString();
                String thoigianbd=edtthoigianbd.getText().toString();
                String thoigiankt=edtthoigiankt.getText().toString();
                if(edtuudai.getText().toString().equals("")){
                    Toast.makeText(SuaPhongActivity.this, "Nhập ưu đãi", Toast.LENGTH_SHORT).show();
                    return;
                }
                int uudai=Integer.parseInt(edtuudai.getText().toString());
                if(edtgia.getText().toString().equals("")){
                    Toast.makeText(SuaPhongActivity.this, "Nhập giá", Toast.LENGTH_SHORT).show();
                    return;
                }
                int gia=Integer.parseInt(edtgia.getText().toString());
                String mota=edtmota.getText().toString();

                PhongKS ph=new PhongKS(maks,maphong,thoigianbd,thoigiankt,TINH_TRANG,uudai,gia,mota,LOAI_PHONG);
                long check = SuaPhong(ph);
                if(check>0){
                    Toast.makeText(SuaPhongActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SuaPhongActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(SuaPhongActivity.this,DanhSachPhongActivity.class));
                finish();

            }
        });

        spinnerloaiphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=mangloaiphong.get(i);
                String sk[]=s.split("-");
                LOAI_PHONG=sk[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private long SuaPhong(PhongKS p){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maks",p.getMaks() );
        contentValues.put("maphong",p.getMaphong() );
        contentValues.put("thoigianbd",p.getThoigianbd() );
        contentValues.put("thoigiankt", p.getThoigiankt());
        contentValues.put("tinhtrang", p.getTinhtrang());
        contentValues.put("uudai",p.getUudai() );
        contentValues.put("gia",p.getGia() );
        contentValues.put("motachitiet",p.getMotachitiet() );
        contentValues.put("maloai",p.getMaloai() );

        return sqLiteDatabase.update("PhongKS", contentValues, "maks=? and maphong=?", new String[]{
                p.getMaks(),p.getMaphong()
        });
    }

    private void  layLoaiphong() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoaiPhong", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            mangloaiphong.add(ma+"-"+ten);

        }
        adapterloai.notifyDataSetChanged();
    }

    private void init() {
        rdduocthue= (RadioButton) findViewById(R.id.radioduocthue);
        rdchuathue= (RadioButton) findViewById(R.id.radiochuaduocthue);
        spinnerloaiphong= (Spinner) findViewById(R.id.spinnerloaiphong);
        mangloaiphong=new ArrayList<>();
        adapterloai=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mangloaiphong);
        spinnerloaiphong.setAdapter(adapterloai);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mangks=new ArrayList<>();
        edtmaphong= (EditText) findViewById(R.id.edtmaphong);
        edtmaks= (EditText) findViewById(R.id.edtmaks);
        edtthoigianbd= (EditText) findViewById(R.id.edtthoigianbd);
        edtthoigiankt= (EditText) findViewById(R.id.edtthoigiankt);
        edtuudai= (EditText) findViewById(R.id.edtuudai);
        edtgia= (EditText) findViewById(R.id.edtgia);
        edtmota= (EditText) findViewById(R.id.edtmota);
        btnHuy= (LiveButton) findViewById(R.id.btnHuy);
        btnThem= (LiveButton) findViewById(R.id.btnThem);
    }

}
