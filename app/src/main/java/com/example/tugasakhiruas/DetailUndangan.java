package com.example.tugasakhiruas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;


public class DetailUndangan extends AppCompatActivity{

    private Database database;
    private Constants constants;
    private TextView namapasangan, tanggalpernikahan;
    private ImageView gambarpernikahan;
    Calendar calendar;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undangan_page);

        //inisialisasi
        ImageButton backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity(v);
            }
        });

        database = new Database(this);
        namapasangan = findViewById(R.id.nama);
        tanggalpernikahan = findViewById(R.id.tanggal);
        gambarpernikahan = findViewById(R.id.gambar);

        getData();
    }

    private void HomeActivity(View v) {
        Intent it = new Intent(DetailUndangan.this, HomeActivity.class);
        startActivity(it);
    }

    // Mendapatkan data dari database
    @SuppressLint("Range")
    public void getData(){

        sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                String nama = cursor.getString(cursor.getColumnIndex(Constants.C_NAMA));
                String tanggal = cursor.getString(cursor.getColumnIndex(Constants.C_TANGGAL));
                String gambar = cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE));

                namapasangan.setText(nama);
                tanggalpernikahan.setText(tanggal);
                gambarpernikahan.setImageURI(Uri.parse(gambar));

            }while (cursor.moveToNext());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}