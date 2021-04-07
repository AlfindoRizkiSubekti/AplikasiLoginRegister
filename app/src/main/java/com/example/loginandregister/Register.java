package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.invoke.ConstantCallSite;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String TAG = "TAG";
    EditText mNama, mEmail, mPhone, mPassword;
    Button mRegisterbtn;
    TextView mLoginbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNama=findViewById(R.id.Nama);
        mEmail=findViewById(R.id.Email);
        mPhone=findViewById(R.id.Phone);
        mPassword=findViewById(R.id.Password);
        mRegisterbtn=findViewById(R.id.Registerbtn);
        mLoginbtn=findViewById(R.id.Loginbtn);

        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                String nama= mNama.getText().toString().trim();
                String phone= mPhone.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("email dibutuhkan!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password dibutuhkan!");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("pasbord harus lebih dari 6 karakter");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //register user in fairebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User berhasil dibuat", Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("Users").document(userID);
                            Map<String,Object>user=new HashMap<>();
                            user.put("Nama",nama);
                            user.put("Email",email);
                            user.put("Phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess for user profile"+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }else{
                            Toast.makeText(Register.this, "user gagal dibuat"   + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}