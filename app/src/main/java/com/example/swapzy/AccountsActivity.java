package com.example.swapzy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import static java.lang.System.exit;

public class AccountsActivity extends AppCompatActivity {
    FloatingActionButton mFloatExit,mFloatExplore;
    Button mBtnAds,mBtnLogout;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView mtvMail,mtvUser,mtvPhone;
    private ImageView mIvProf;
    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        mIvProf = findViewById(R.id.imgProf);
        mtvUser = findViewById(R.id.tvUserS);
        mtvMail = findViewById(R.id.tvMailProfile);
        mtvPhone = findViewById(R.id.tvPhoneNumber);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                mtvUser.setText("Name: " + userProfile.getName());
                mtvMail.setText("Email: " + userProfile.getEmail());
                mtvPhone.setText("Phone Number: " + userProfile.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mFloatExit = findViewById(R.id.floatExit);
        mFloatExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountsActivity.this);
                builder.setMessage("Are you sure to exit");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        mFloatExplore = findViewById(R.id.floatingActionButton2);
        mFloatExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explore = new Intent(AccountsActivity.this,ImagesActivity.class);
                startActivity(explore);

            }
        });

        mBtnAds = findViewById(R.id.BtnAds);
        mBtnAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ads = new Intent(AccountsActivity.this,AdsActivity.class);
                startActivity(Ads);


            }
        });

        mBtnLogout = findViewById(R.id.btnLogout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            firebaseAuth.signOut();

            startActivity(new Intent(AccountsActivity.this,SignInActivity.class));
            }
        });


    }
}
