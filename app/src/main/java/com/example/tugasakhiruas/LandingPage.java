package com.example.tugasakhiruas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button Register = findViewById(R.id.register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });

        Button Login = findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPage(v);
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {
            startActivity(new Intent(LandingPage.this, HomeActivity.class));
        }
    }

    private void LoginPage(View v){
        Intent it = new Intent(this, LoginPage. class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
    }

    private void Register(View v){
        Intent it = new Intent(this, RegisterPage. class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}