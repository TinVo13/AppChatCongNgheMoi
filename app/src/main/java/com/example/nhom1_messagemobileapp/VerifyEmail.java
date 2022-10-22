package com.example.nhom1_messagemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity {

    private Button btnXacThuc;
    private EditText txtEmail;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        //String email = getIntent().getExtras().get("email").toString();
        //Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        txtEmail = findViewById(R.id.edtEmailInput);
        btnXacThuc = findViewById(R.id.btnGuiOTP);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!user.isEmailVerified()){
                    Toast.makeText(VerifyEmail.this, "Bạn chưa xác thực Email!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(VerifyEmail.this, "Xác thực Email thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyEmail.this,MainActivity.class);
                }
            }
        });
    }
}