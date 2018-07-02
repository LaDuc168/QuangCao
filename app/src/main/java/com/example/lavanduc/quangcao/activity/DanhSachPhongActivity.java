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
import com.example.lavanduc.quangcao.adapter.AdapterPhong;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.PhongKS;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;

import static android.R.attr.id;

public class DanhSachPhongActivity extends AppCompatActivity {

    ListView listViewPhong;

    AdapterPhong adapterPhong;
    ArrayList<PhongKS> mang;
    int POSITION=0;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phong);
        init();
        setData();
        addEvent();

    }

    private void addEvent() {

        listViewPhong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listViewPhong);
                POSITION=position;
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menuthem){
            if (Temp.USER.equals("Admin")) {
                startActivity(new Intent(DanhSachPhongActivity.this,ThemPhongActivity.class));
                finish();

            }else {
                Toast.makeText(this, "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_nguoi_dung,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_listview,menu);
        menu.setHeaderTitle("Lựa chọn của bạn?");
        //Co the set icon cho no menu.setHeaderIcon(truyen hinh vao);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menusua:
                if (Temp.USER.equals("Admin")) {
                    Intent intent=new Intent(DanhSachPhongActivity.this,SuaPhongActivity.class);
                    intent.putExtra("PH",mang.get(POSITION));
                    startActivity(intent);

                }else {
                    Toast.makeText(this, "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menuxoa:
                if (Temp.USER.equals("Admin")) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(DanhSachPhongActivity.this);
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
                            long check= sqLiteDatabase.delete("PhongKS","maks=? and maphong=?",new String[]{
                                    mang.get(POSITION).getMaks(),mang.get(POSITION).getMaphong()
                            });
                            if(check>0){
                                Toast.makeText(DanhSachPhongActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                setData();
                            }
                            else {
                                Toast.makeText(DanhSachPhongActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//        Dialog hopThoai=builder.create();
                    builder.show();
                }else {
                    Toast.makeText(this, "Bạn không có quyền xóa", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void setData() {
        mang.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from PhongKS",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String maks=cursor.getString(0);
            String maphong=cursor.getString(1);
            String thoigianbd=cursor.getString(2);
            String thoigiankt=cursor.getString(3);
            String tinhtrang=cursor.getString(4);
            int uudai=cursor.getInt(5);
            int gia=cursor.getInt(6);
            String mota=cursor.getString(7);
            String maloai=cursor.getString(9);
            PhongKS dkh=new PhongKS(maks,maphong,thoigianbd,thoigiankt,tinhtrang,uudai,gia,mota,maloai);

            mang.add(dkh);
        }
        adapterPhong.notifyDataSetChanged();
    }


    private void init() {
        mang=new ArrayList<>();
        listViewPhong= (ListView) findViewById(R.id.listViewPhong);
        adapterPhong=new AdapterPhong(this,mang);
        listViewPhong.setAdapter(adapterPhong);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
