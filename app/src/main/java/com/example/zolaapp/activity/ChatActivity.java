package com.example.zolaapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zolaapp.R;

public class ChatActivity extends AppCompatActivity {
    private Toolbar toolbar_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar_chat=findViewById(R.id.toolbar_chat);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        ActionBar(name);
    }
    public void ActionBar(String name){
        setSupportActionBar(toolbar_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_chat.setTitle(name);
    }
}