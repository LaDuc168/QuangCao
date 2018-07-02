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
import com.example.lavanduc.quangcao.adapter.AdapterKhachSan;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;

import static android.R.attr.id;

public class DanhSachKhachSan extends AppCompatActivity {

    ListView listViewKS;
    ArrayList<KhachSan> mang;

    AdapterKhachSan adapterKhachSan;
    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    int POSITION = 0;

    String TEMP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_khach_san);
        init();
        setData();
        TEMP=getIntent().getStringExtra("DUC");

        addEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuthem) {
            if ( Temp.USER.equals("Admin")) {
                startActivity(new Intent(DanhSachKhachSan.this, ThemKhachSanActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_nguoi_dung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvent() {
        listViewKS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachKhachSan.this, ChiTietKhachSanActivity.class);
                intent.putExtra("MAKS", mang.get(position).getMaks());
                intent.putExtra("DUC",TEMP);
                startActivity(intent);
            }
        });
        listViewKS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Temp.USER.equals("guest")) {

                    registerForContextMenu(listViewKS);
                }
                POSITION = position;
                return false;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_listview, menu);
        menu.setHeaderTitle("Lựa chọn của bạn?");
        //Co the set icon cho no menu.setHeaderIcon(truyen hinh vao);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusua:
                if (Temp.USER.equals("Admin")) {
                    Intent intent = new Intent(DanhSachKhachSan.this, SuaKhachSanActivity.class);
                    intent.putExtra("MA", mang.get(POSITION).getMaks());
                    startActivity(intent);

                }else {
                    Toast.makeText(this, "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
                }



                break;
            case R.id.menuxoa:

                if (Temp.USER.equals("Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachKhachSan.this);
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
                            long check = sqLiteDatabase.delete("KhachSan", "maks=?", new String[]{mang.get(POSITION).getMaks()});
                            if (check > 0) {
                                Toast.makeText(DanhSachKhachSan.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                setData();
                            } else {
                                Toast.makeText(DanhSachKhachSan.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//        Dialog hopThoai=builder.create();
                    builder.show();

                }else {
                    Toast.makeText(this, "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        listViewKS = (ListView) findViewById(R.id.listViewKhachSan);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mang = new ArrayList<>();
        adapterKhachSan = new AdapterKhachSan(this, mang);
        listViewKS.setAdapter(adapterKhachSan);
    }

    private void setData() {
        mang.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select maks,tenks,danhgia,anh from KhachSan", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            int danhgia = cursor.getInt(2);
            byte[] anh = cursor.getBlob(3);

            KhachSan khachSan = new KhachSan(ma, ten, danhgia, anh);
            mang.add(khachSan);
        }
        adapterKhachSan.notifyDataSetChanged();
    }


}
