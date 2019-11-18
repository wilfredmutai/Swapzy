package com.example.swapzy;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends Activity {
    EditText username,email,phone,password;
    Button signup;
    TextView login;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupUIviews();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog  = new ProgressDialog(this);
        progressDialog.setMessage("Registering\nPlease Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
//                    Upload data to database
//                    String user_name = username.getText().toString().trim();
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
//                                        Toast.makeText(RegistrationActivity.this, "Registration Was Successful", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        sendEmailVerification();
                                    }else {
                                        Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            }
        });

    }
    private void setupUIviews(){
        username = findViewById(R.id.edtUserName2);
        email = findViewById(R.id.edtEmail2);
        phone = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword2);
        signup = findViewById(R.id.btnSignUp);
        login = findViewById(R.id.tvlogin);
    }

    private boolean validate(){
        boolean result = false;
        String name = username.getText().toString();
        String mail = email.getText().toString();
        String mPhone = phone.getText().toString();
        String pass = password.getText().toString();
        if (name.isEmpty()|| mail.isEmpty()||mPhone.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Input all the fields", Toast.LENGTH_SHORT).show();
        }else {
            long time = System.currentTimeMillis();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users/"+time);
            User x = new User(name,mail,mPhone,pass,String.valueOf(time));
            ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "User details saved", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUpActivity.this, "Failed to store user details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Registered Successfully. Verification mail has been sent to "+email.getText().toString(), Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                    }else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed. Please try again. \n Verification mail not sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}