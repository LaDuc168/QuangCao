package com.example.lavanduc.quangcao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.adapter.AdapterNguoiDung;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.NguoiDung;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;

import static android.R.attr.id;

public class NguoiDungActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<NguoiDung> mang;
    AdapterNguoiDung adapterNguoiDung;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    int POSITION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        init();
        setData();
        addEvent();
    }

    private void addEvent() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    
                registerForContextMenu(listView);
                
                POSITION=position;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_listview,menu);
        menu.setHeaderTitle("Lựa chọn của bạn?");
        menu.setHeaderIcon(R.drawable.giohang);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menusua:
                if (Temp.USER.equals("Admin")) {
                    Intent intent=new Intent(NguoiDungActivity.this,SuaNguoiDungActivity.class);
                    intent.putExtra("MAND",mang.get(POSITION).getManguoidung());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
                }
                
                break;
            case R.id.menuxoa:

                if (Temp.USER.equals("Admin")) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(NguoiDungActivity.this);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn muốn xóa không?");
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            long check=  sqLiteDatabase.delete("NguoiDung","manguoidung=?",new String[]{mang.get(POSITION).getManguoidung()});
                            if(check>0){
                                Toast.makeText(NguoiDungActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                setData();
                            }else {
                                Toast.makeText(NguoiDungActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(this, "Bạn không có quyền xóa", Toast.LENGTH_SHORT).show();
                }
                
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menuthem){

            if (Temp.USER.equals("Admin")) {
                startActivity(new Intent(NguoiDungActivity.this,ThemNguoiDungActivity.class));
                finish();
            }else {
                Toast.makeText(this, "Bạn không có quyên thêm", Toast.LENGTH_SHORT).show();
            }
            
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_nguoi_dung,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setData() {
        mang.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from NguoiDung",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String gioitinh=cursor.getString(2);
            String phone=cursor.getString(3);
            String email=cursor.getString(4);
            String tentk=cursor.getString(5);
            String mk=cursor.getString(6);
            String quyen=cursor.getString(7);

            NguoiDung khachSan=new NguoiDung(ma,ten,gioitinh,phone,email,tentk,mk,quyen);
            mang.add(khachSan);
        }
        adapterNguoiDung.notifyDataSetChanged();
    }

    private void init() {
        listView= (ListView) findViewById(R.id.listViewNguoiDung);
        mang=new ArrayList<>();
        adapterNguoiDung=new AdapterNguoiDung(this,mang);
        listView.setAdapter(adapterNguoiDung);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
