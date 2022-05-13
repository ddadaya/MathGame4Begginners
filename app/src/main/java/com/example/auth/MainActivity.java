package com.example.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;
    private Switch language;
    private Button reg;
    private Button auth;

    User_data us = new User_data();
    DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("User_data");
    //String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    String uid="nCveT5xoR0VwmGmhNECyeChEZgy1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETemail = (EditText) findViewById(R.id.et_email);
        ETpassword = (EditText) findViewById(R.id.et_password);
        reg=findViewById(R.id.btn_registration);
        auth=findViewById(R.id.btn_sign_in);
        language=findViewById(R.id.lang);

        language.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    language.setText("Русский");
                    ETemail.setHint("почта");
                    ETpassword.setHint("пароль");
                    reg.setText("регистрация");
                    auth.setText("авторизация");
                } else {
                    language.setText("English");
                    ETemail.setHint("email");
                    ETpassword.setHint("password");
                    reg.setText("registration");
                    auth.setText("authorization");
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };

        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign_in) {
            signin(ETemail.getText().toString(),ETpassword.getText().toString());
        } else if(view.getId() == R.id.btn_registration){
            registration(ETemail.getText().toString(),ETpassword.getText().toString());
        }
    }

    public void signin(String email , String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    Boolean state=language.isChecked();
                    Intent intent = new Intent(MainActivity.this, Panel.class);
                    intent.putExtra("mail", ETemail.getText());
                    intent.putExtra("password", ETpassword.getText());
                    intent.putExtra("state", state);
                    uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    intent.putExtra("uid",  uid);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    us.setKey(uid);
                    re.child(uid).child("email").setValue(ETemail.getText().toString());
                    re.child(uid).child("pass").setValue(ETpassword.getText().toString());
                    re.child(uid).child("score").setValue(0);
                }
                else {
                    Toast.makeText(MainActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}