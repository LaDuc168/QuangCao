package com.example.lavanduc.quangcao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.adapter.AdapterKhachHang;
import com.example.lavanduc.quangcao.model.KhachHang;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.NguoiDung;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;

import static android.R.attr.id;

public class KhachHangActivity extends AppCompatActivity {

    ListView listView;
    AdapterKhachHang adapterKhachHang;
    ArrayList<KhachHang> mang;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    int POSITION = 0;
    String KH = "NOT";
    String MA_KH = "NOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        init();
        KH = getIntent().getStringExtra("LICHSU");
        MA_KH = getIntent().getStringExtra("MAKH");
        setData();
        addEvent();
    }

    private void addEvent() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Temp.USER.equals("guest")){

                registerForContextMenu(listView);
                }
                POSITION = position;
                return false;
            }
        });
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listViewKhachHang);
        mang = new ArrayList<>();
        adapterKhachHang = new AdapterKhachHang(this, mang);
        listView.setAdapter(adapterKhachHang);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
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

                Intent intent = new Intent(KhachHangActivity.this, SuaKhachHangActivity.class);
                intent.putExtra("KH", mang.get(POSITION));
                startActivity(intent);

                break;
            case R.id.menuxoa:
                if(Temp.USER.equals("Admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(KhachHangActivity.this);
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
                            int check = sqLiteDatabase.delete("KhachHang", "makhach=?", new String[]{mang.get(POSITION).getMakhach()});
                            if (check > 0) {
                                Toast.makeText(KhachHangActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                setData();
                            } else {
                                Toast.makeText(KhachHangActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = null;
        if ( KH == null) {
            cursor = sqLiteDatabase.rawQuery("select * from KhachHang", null);
        } else if(KH.equals("OK")) {
            cursor = sqLiteDatabase.rawQuery("select * from KhachHang where makhach='" + MA_KH + "'", null);
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String ngaysinh = cursor.getString(2);
            String diachi = cursor.getString(3);
            String maphong = cursor.getString(4);
            String maks = cursor.getString(5);
            String gioitinh = cursor.getString(6);

            KhachHang khachSan = new KhachHang(ma, ten, ngaysinh, diachi, maphong, maks, gioitinh);
            mang.add(khachSan);
        }
        adapterKhachHang.notifyDataSetChanged();
    }
}
