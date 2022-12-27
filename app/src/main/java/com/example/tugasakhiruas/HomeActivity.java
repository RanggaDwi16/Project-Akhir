package com.example.tugasakhiruas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Database databaseHelper;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycleview);
        databaseHelper = new Database(this);
        showRecord();

        firebaseAuth = FirebaseAuth.getInstance();
        user  = firebaseAuth.getCurrentUser();

        ImageButton logout = (ImageButton) findViewById(R.id.keluar);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Terima kasih sudah berkunjung!", Toast.LENGTH_SHORT).show();
            }
        });

        Button tambahundangan = findViewById(R.id.tambahundangan);
        tambahundangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahPage(v);
                Intent intent = new Intent(HomeActivity.this, TambahUndangan.class);
                intent.putExtra("editMode", false);
                startActivity(intent);
            }
        });

        ImageButton bukuundangan = findViewById(R.id.bukuundangan);
        bukuundangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BukuUndangan(v);
            }
        });

        // Get user data from firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        final TextView welcomeText = (TextView) findViewById(R.id.txtUser);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String Username = userProfile.Username;
                    welcomeText.setText("Welcome, " + Username + "!");
                }
            }

            // Error handling
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Ada kesalahan!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showRecord() {
        Adapter adapter = new Adapter(HomeActivity.this, databaseHelper.getAllData(Constants.C_ADD_TIMESTAMP + " DESC"));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }

    private void TambahPage (View v) {
        Intent it = new Intent(this, TambahUndangan.class);
        startActivity(it);
    }

    private void BukuUndangan (View v) {
        Intent it = new Intent(this, DaftarUndangan.class);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        startActivity(it);
    }

    // Disable back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            moveTaskToBack(true);
        }
            return super.onKeyDown(keyCode, event);
    }
}