package com.example.loginandregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard extends AppCompatActivity {
    TextView nama,email,phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        nama=findViewById(R.id.xnama);
        email=findViewById(R.id.xemail);
        phone=findViewById(R.id.xphone);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nama.setText(documentSnapshot.getString("Nama"));
                email.setText(documentSnapshot.getString("Email"));
                phone.setText(documentSnapshot.getString("Phone"));
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}