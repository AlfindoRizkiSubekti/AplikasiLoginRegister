package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    boolean ifRemember;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth= FirebaseAuth.getInstance();
        ifRemember=(fAuth.getCurrentUser() != null);
        if(ifRemember){
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            finish();
        }
    }

    public void login(View view) {
        Intent intent= new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent= new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }

    
}