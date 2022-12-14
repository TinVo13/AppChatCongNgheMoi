package com.example.nhom1_messagemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom1_messagemobileapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button btnToLoginPage,btnRegister;
    EditText edtName, edtRegisterEmail, edtPassword, edtRePassword, edtLoginEmail;
    private Context context;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();

        btnToLoginPage.setOnClickListener((v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = onClickRegister();
            }
        });


    }
    private void initUi() {
        context = this;

        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.regis_edtUserHandleName);
        edtRegisterEmail = findViewById(R.id.regis_edtEmailInput);
        edtLoginEmail = findViewById(R.id.regis_edtEmailInput);
        edtPassword = findViewById(R.id.regis_edtPassInput);
        edtRePassword = findViewById(R.id.regis_edtConfirmPassInput);

        btnToLoginPage = findViewById(R.id.btn_to_login_page);
        btnRegister = findViewById(R.id.btn_register);
    }
    private boolean onClickRegister() {
        String name = edtName.getText().toString().trim();
        String email = edtRegisterEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String rePassword = edtRePassword.getText().toString().trim();
        if (name.isEmpty() || name.length() <= 0) {
            Toast.makeText(context, "T??n kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
            edtName.requestFocus();
            return false;
        }
        if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            Toast.makeText(context,"Email kh??ng h???p l???",Toast.LENGTH_SHORT).show();
        }
        if (email.isEmpty() || email.length() <= 0) {
            Toast.makeText(context, "Email kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
            edtRegisterEmail.requestFocus();
            return false;
        }
        if (password.isEmpty() || password.length() <= 0) {
            Toast.makeText(context, "M???t kh???u kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(context, "M???t kh???u ph???i t??? 8 k?? t???", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return false;
        }
        if (rePassword.isEmpty() || rePassword.length() <= 0) {
            Toast.makeText(context, "M???t kh???u nh???p l???i kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
            edtRePassword.requestFocus();
            return false;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(context, "M???t kh???u nh???p l???i kh??ng ????ng", Toast.LENGTH_SHORT).show();
            edtRePassword.requestFocus();
            return false;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //send verify email
                            FirebaseUser fuser = mAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Email not send "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            String uid = mAuth.getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("user");
                            User user = new User(name, email,"https://firebasestorage.googleapis.com/v0/b/messagemobileapp.appspot.com/o/images%2Fooui_user-avatar.png?alt=media&token=5e572c2d-4f38-4f54-b39d-0b8574f1e8e5");
                            user.setUid(uid);
                            myRef.child(uid).setValue(user);

                            Toast.makeText(context, "Vui l??ng x??c th???c ?????a ch??? Email ????? ????ng nh???p v??o ???ng d???ng!", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(context, "????ng k?? t??i kho???n th???t b???i do Email ???? ???????c s??? d???ng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return true;
    }
}