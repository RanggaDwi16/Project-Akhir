package com.example.tugasakhiruas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
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

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private TextView lupapassword;
    private Button Login;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ImageButton sliderkanan = findViewById(R.id.Slidekanan);
        sliderkanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(v);
            }
        });

        Login = (Button) findViewById(R.id.login);
        Login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText)  findViewById(R.id.Password);

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
        mAuth = FirebaseAuth.getInstance();

        lupapassword = (TextView) findViewById(R.id.lupapassword);
        lupapassword.setOnClickListener(this);
    }

    private void Register(View v){
        Intent it = new Intent(this, RegisterPage. class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
            case R.id.login:
                userLogin();
                break;
            case R.id.lupapassword:
                startActivity(new Intent(this, LupaPassword.class));
                break;
        }
    }

    private void userLogin() {
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if(Email.isEmpty()){
            editTextEmail.setError("Email harus diisi!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Tolong masukkan email yang valid");
            editTextEmail.requestFocus();
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

        progressBar.setVisibility(View.GONE);

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent it = new Intent(LoginPage.this, HomeActivity.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);

                }else{
                    Toast.makeText(LoginPage.this, "Gagal login! Cek kembali Email dan Password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}