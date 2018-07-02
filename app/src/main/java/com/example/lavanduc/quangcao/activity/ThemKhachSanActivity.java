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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lavanduc.quangcao.R;
import com.example.lavanduc.quangcao.model.DichVuCC;
import com.example.lavanduc.quangcao.model.KhachSan;
import com.example.lavanduc.quangcao.model.MyDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.katso.livebutton.LiveButton;

public class ThemKhachSanActivity extends AppCompatActivity {

    EditText edtma, edtten, edtbinhluan, edtdiadiem;
    ImageView imgHinh;
    CircleImageView imgChon, imgChup;
    LiveButton btnThem, btnHuy;

    RadioButton wifico, wifiko, buasangco, buasangko,
            dieuhoaco, dieuhoako, beboico, beboiko, duadonco, duadonko;

    ArrayList<String> mangDV;

    final String DATABASE_NAME = "quangcao.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_san);
        init();
        addEvent();
        addEventRadio();
    }

    private void addEventRadio() {
        wifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiko.setChecked(false);
            }
        });
        wifiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifico.setChecked(false);
            }
        });

        buasangco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buasangko.setChecked(false);
            }
        });
        buasangko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buasangco.setChecked(false);
            }
        });

        dieuhoaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dieuhoako.setChecked(false);
            }
        });
        dieuhoako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dieuhoaco.setChecked(false);
            }
        });

        beboico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beboiko.setChecked(false);
            }
        });
        beboiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beboico.setChecked(false);
            }
        });

        duadonco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duadonko.setChecked(false);
            }
        });
        duadonko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duadonco.setChecked(false);
            }
        });
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

    public byte[] ImageView_To_Byte(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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

                String ma = edtma.getText().toString();
                String ten = edtten.getText().toString();
                String binhluan = edtbinhluan.getText().toString();
                String diadiem = edtdiadiem.getText().toString();
                byte[] anh = ImageView_To_Byte(imgHinh);
                if (wifico.isChecked()) {
                    mangDV.add("Wifi");
                }
                if (buasangco.isChecked()) {
                    mangDV.add("Bữa sáng");
                }
                if (dieuhoaco.isChecked()) {
                    mangDV.add("Điều hòa");
                }
                if (beboico.isChecked()) {
                    mangDV.add("Bể bơi");
                }
                if (duadonco.isChecked()) {
                    mangDV.add("Đưa đón");
                }

                int tem = 1 + new Random().nextInt(2000);
                String dichvu = tem + "";

                //Thêm dịch vụ

                ArrayList<DichVuCC> arr = new ArrayList<DichVuCC>();
                for (String ss : mangDV
                        ) {
                    int temp = 5 + new Random().nextInt(2000);

                   DichVuCC dvcc=new DichVuCC(dichvu, temp, ss);
                    ThemCC(dvcc);

                }

                KhachSan ks = new KhachSan(ma, ten, 0, binhluan, diadiem, dichvu, anh);

                long check = ThemKS(ks);
                if (check > 0) {
                    Toast.makeText(ThemKhachSanActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThemKhachSanActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(ThemKhachSanActivity.this, QuanLyActivity.class));
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
                ActivityCompat.requestPermissions(ThemKhachSanActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, 2017);
            }
        });


    }

    private long ThemKS(KhachSan kss) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maks", kss.getMaks());
        contentValues.put("tenks", kss.getTenks());
        contentValues.put("danhgia", kss.getDanhgia());
        contentValues.put("binhluan", kss.getBinhluan());
        contentValues.put("diadiem", kss.getDiadiem());
        contentValues.put("dichvucungcap", kss.getDichvucungcap());
        contentValues.put("anh", kss.getAnh());

        return sqLiteDatabase.insert("KhachSan", null, contentValues);
    }


    private long ThemCC(DichVuCC cc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("dichvucc", cc.getDichvucc());
        contentValues.put("stt", cc.getStt());
        contentValues.put("tendichvu", cc.getTendichvu());

        return sqLiteDatabase.insert("DichVuCC", null, contentValues);
    }

    private void init() {
        mangDV = new ArrayList<>();
        edtma = (EditText) findViewById(R.id.edtma);
        edtten = (EditText) findViewById(R.id.edtten);
        edtbinhluan = (EditText) findViewById(R.id.edtbinhluan);
        edtdiadiem = (EditText) findViewById(R.id.edtdiadiem);
        wifico = (RadioButton) findViewById(R.id.wifico);
        wifiko = (RadioButton) findViewById(R.id.wifiko);

        buasangco = (RadioButton) findViewById(R.id.buasangco);
        buasangko = (RadioButton) findViewById(R.id.buasangko);

        dieuhoaco = (RadioButton) findViewById(R.id.dieuhoaco);
        dieuhoako = (RadioButton) findViewById(R.id.dieuhoako);

        beboico = (RadioButton) findViewById(R.id.beboico);
        beboiko = (RadioButton) findViewById(R.id.beboiko);

        duadonco = (RadioButton) findViewById(R.id.duadonco);
        duadonko = (RadioButton) findViewById(R.id.duaddonko);

        btnThem = (LiveButton) findViewById(R.id.btnThem);
        btnHuy = (LiveButton) findViewById(R.id.btnHuy);
        imgChon = (CircleImageView) findViewById(R.id.imgchonhinh);
        imgChup = (CircleImageView) findViewById(R.id.imgchuphinh);
        imgHinh = (ImageView) findViewById(R.id.imgHinhOk);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

    }
}
