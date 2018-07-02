package com.example.lavanduc.quangcao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.HoatDongGT;
import com.example.lavanduc.quangcao.model.MyDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.katso.livebutton.LiveButton;

public class ThemHDGTActivity extends AppCompatActivity {

    EditText edtma,edtthoigianbd,edtthoigiankt,edtmota,edtdiadiem,edtbinhluan,edtten;
    ImageView imgHinh;
    CircleImageView imgChon,imgChup;
    LiveButton btnThem,btnHuy;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hdgt);
        init();
        addEvent();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2017 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 2017);
        } else {
            Toast.makeText(this, "Bạn k cho phép mở camera!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                String ten=edtten.getText().toString();
                String thoigianbd=edtthoigianbd.getText().toString();
                String thoigiankt=edtthoigiankt.getText().toString();
                String mota=edtmota.getText().toString();
                String diadiem=edtdiadiem.getText().toString();
                String binhluan=edtbinhluan.getText().toString();
                byte[] anh = ImageView_To_Byte(imgHinh);
                HoatDongGT hd=new HoatDongGT(ma,thoigianbd,thoigiankt,mota,binhluan,0,diadiem,anh,ten);
                long check = ThemHDGT(hd);
                if(check>0){
                    Toast.makeText(ThemHDGTActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ThemHDGTActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(ThemHDGTActivity.this,HoatDongGTActivity.class));
                finish();
            }
        });

        imgChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2018);
            }
        });
        imgChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ThemHDGTActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, 2017);
            }
        });
    }

    private long ThemHDGT(HoatDongGT hd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma", hd.getMa());
        contentValues.put("thoigianbd",hd.getThoigiankt() );
        contentValues.put("thoigiankt",hd.getThoigianbd() );
        contentValues.put("motachitiet",hd.getMotachitiet() );
        contentValues.put("binhluan",hd.getBinhluan() );
        contentValues.put("luotthich",hd.getLuotthich());
        contentValues.put("diadiem",hd.getDiadiem() );
        contentValues.put("anh",hd.getAnh() );
        contentValues.put("ten",hd.getTen() );

       return sqLiteDatabase.insert("HoatDongGT", null, contentValues);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2017) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgHinh.setImageBitmap(bitmap);


            }
            if (requestCode == 2018) {
                Uri img = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(img);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgHinh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void init() {
        edtma= (EditText) findViewById(R.id.edtma);
        edtten= (EditText) findViewById(R.id.edtten);
        edtthoigianbd= (EditText) findViewById(R.id.edtthoigianbd);
        edtthoigiankt= (EditText) findViewById(R.id.edtthoigiankt);
        edtmota= (EditText) findViewById(R.id.edtmotachitiet);
        edtdiadiem= (EditText) findViewById(R.id.edtdiadiem);
        edtbinhluan= (EditText) findViewById(R.id.edtbinhluan);
        imgHinh= (ImageView) findViewById(R.id.imgHinhOk);
        imgChon= (CircleImageView) findViewById(R.id.imgchonhinh);
        imgChup= (CircleImageView) findViewById(R.id.imgchuphinh);

        btnThem= (LiveButton) findViewById(R.id.btnThem);
        btnHuy= (LiveButton) findViewById(R.id.btnHuy);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
