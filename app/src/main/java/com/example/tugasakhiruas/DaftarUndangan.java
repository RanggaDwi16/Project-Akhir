package com.example.tugasakhiruas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DaftarUndangan extends AppCompatActivity {

    RecyclerView recyclerView;
    Database databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_undangan_page);

        recyclerView = findViewById(R.id.recycleview);
        databaseHelper = new Database(this);
        showRecord();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity(v);
            }
        });
    }

    private void showRecord() {
        Adapter adapter = new Adapter(DaftarUndangan.this, databaseHelper.getAllData(Constants.C_ADD_TIMESTAMP + " DESC"));
        recyclerView.setAdapter(adapter);
    }

    private void HomeActivity(View v) {
        Intent it = new Intent(DaftarUndangan.this, HomeActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DaftarUndangan.this, HomeActivity.class);
        startActivity(intent);
    }
}