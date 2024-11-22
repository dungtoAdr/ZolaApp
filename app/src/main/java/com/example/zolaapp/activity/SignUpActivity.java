package com.example.zolaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zolaapp.R;
import com.example.zolaapp.retrofit.ApiZola;
import com.example.zolaapp.retrofit.RetrofitClient;
import com.example.zolaapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    private EditText etRegisterPhone, etRegisterUsername, etRegisterPassword, etRegisterEmail;
    private Button btnRegister;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiZola apiZola;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        apiZola = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiZola.class);
        initView();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = etRegisterEmail.getText().toString().trim();
        String pass = etRegisterPassword.getText().toString().trim();
        String username = etRegisterUsername.getText().toString().trim();
        String mobile = etRegisterPhone.getText().toString().trim();
        if (TextUtils.isEmpty(etRegisterEmail.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(etRegisterPassword.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication created.", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    postData(email, pass, username, mobile, user.getUid());
                } else {
                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void postData(String email, String pass, String username, String mobile, String uid) {
        compositeDisposable.add(apiZola.signUp(email, pass, username, mobile, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            Intent intent = new Intent(SignUpActivity.this, ZolaActivity.class);
                            startActivity(intent);
                            finish();
                        }, throwable -> {
                            Log.d("Taggggg", throwable.getMessage());
                        }
                ));
    }

    private void initView() {
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPhone = findViewById(R.id.etRegisterPhone);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}