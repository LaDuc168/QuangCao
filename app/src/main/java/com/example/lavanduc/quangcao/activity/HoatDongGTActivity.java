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
import android.widget.ViewFlipper;

import com.example.lavanduc.quangcao.MainActivity;
import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.adapter.AdapterHDGT;
import com.example.lavanduc.quangcao.adapter.AdapterKhachSan;
import com.example.lavanduc.quangcao.model.HoatDongGT;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;
import com.example.lavanduc.quangcao.model.Temp;

import java.util.ArrayList;

import static android.R.attr.id;

public class HoatDongGTActivity extends AppCompatActivity {

    ListView listViewKS;
    ArrayList<HoatDongGT> mang;

    AdapterHDGT adapterHDGT;
    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;
    private ViewFlipper viewFlipper;

    int POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoat_dong_gt);
        init();
        setData();
        addEvent();
    }

    private void addEvent() {
        listViewKS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HoatDongGTActivity.this, HienThiChiTietHDGTActivity.class);
                intent.putExtra("MA", mang.get(position).getMa());
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
                    Intent intent = new Intent(HoatDongGTActivity.this, SuaHDGTActivity.class);
                    intent.putExtra("MA", mang.get(POSITION).getMa());
                    startActivity(intent);


                }else {
                    Toast.makeText(this, "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menuxoa:
                if (Temp.USER.equals("Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HoatDongGTActivity.this);
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
                            long check = sqLiteDatabase.delete("HoatDongGT", "ma=?", new String[]{mang.get(POSITION).getMa()});
                            if (check > 0) {
                                Toast.makeText(HoatDongGTActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                setData();
                            } else {
                                Toast.makeText(HoatDongGTActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        int id = item.getItemId();
        if (id == R.id.menuthem) {
            if (Temp.USER.equals("Admin")) {
                startActivity(new Intent(HoatDongGTActivity.this, ThemHDGTActivity.class));

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

    private void init() {
        listViewKS = (ListView) findViewById(R.id.listViewHDGT);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mang = new ArrayList<>();
        adapterHDGT = new AdapterHDGT(this, mang);
        listViewKS.setAdapter(adapterHDGT);
    }

    private void setData() {
        mang.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select ma,luotthich,anh,ten from HoatDongGT", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            int luotthich = cursor.getInt(1);
            byte[] anh = cursor.getBlob(2);
            String ten = cursor.getString(3);
            HoatDongGT hd = new HoatDongGT(ma, luotthich, anh, ten);

            mang.add(hd);

        }
        adapterHDGT.notifyDataSetChanged();
    }
}
