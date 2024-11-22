package com.example.zolaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.zolaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageSlider imageSlider;
    private Button btnSignUp,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent=new Intent(MainActivity.this, ZolaActivity.class);
            startActivity(intent);
            finish();
        }
        imageSlider =findViewById(R.id.image_slider);
        btnSignUp=findViewById(R.id.bt_signup);
        btnLogin=findViewById(R.id.bt_login);
        ArrayList<SlideModel> list =new ArrayList<>();
        list.add(new SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.CENTER_CROP));
        list.add(new SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.CENTER_CROP));
        list.add(new SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(list);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}