package com.example.tugasakhiruas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private TextView registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mAuth = FirebaseAuth.getInstance();
        registerUser = (Button) findViewById(R.id.register);
        registerUser.setOnClickListener(this);

        editTextUsername = (EditText) findViewById(R.id.Username);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.Password);

        ImageView showhidepassword = findViewById(R.id.showhidepassword);
        showhidepassword.setImageResource(R.drawable.ic_hide_pwd);
        showhidepassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(editTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showhidepassword.setImageResource(R.drawable.ic_hide_pwd);
                }
                else {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showhidepassword.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ImageButton sliderkiri = findViewById(R.id.sliderkiri);
        sliderkiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPage(v);
            }
        });
    }

    private void LoginPage(View v){
        Intent it = new Intent(this, LoginPage. class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent u = new Intent(this, LandingPage.class);
        startActivity(u);
        overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String Username = editTextUsername.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if(Username.isEmpty()) {
            editTextUsername.setError("Username harus diisi!");
            editTextUsername.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            editTextEmail.setError("Email harus diisi!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Tolong masukkan email yang valid!");
        }

        if(Password.isEmpty()){
            editTextPassword.setError("Password harus diisi!");
            editTextPassword.requestFocus();
            return;
        }

        if(Password.length() < 6){
            editTextPassword.setError("Password harus minimal 6 karakter!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //register the user in firebase
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(Username, Email, Password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(RegisterPage.this, "User telah ditambahkan dengan sukses!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(RegisterPage.this, "User tidak berhasil ditambahkan! Coba lagi!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                                Toast.makeText(RegisterPage.this, "User tidak berhasil ditambahkan! Coba lagi!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
}