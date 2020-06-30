package com.alfaco_1.testno1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
private ImageView create_btn_img1,img3,img4;
private TextView email_txt1,phone_txt1,password_txt1,log_txt1,txt01;
private TextInputLayout email_layout1,phone_layout2,password_layout3;
private ProgressBar progressBar;
private FirebaseAuth fire_auth;
FirebaseDatabase rootNode1;
DatabaseReference reference1;

//public static boolean setSignUpFragment = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        //TextInputLayout section
        email_layout1 = (TextInputLayout)findViewById(R.id.create_textInputLayout2);
        phone_layout2 = (TextInputLayout)findViewById(R.id.create_textInputLayout3);
        password_layout3 = (TextInputLayout)findViewById(R.id.create_textInputLayout4);
        //ImageView Section
        img3 = (ImageView)findViewById(R.id.imageView67);
        create_btn_img1 = (ImageView) findViewById(R.id.create_imageView11);
        //TextView section
        email_txt1 = (TextView)findViewById(R.id.create_textView13);
        phone_txt1 = (TextView)findViewById(R.id.textView15);
        password_txt1 = (TextView)findViewById(R.id.textView17);
        log_txt1 = (TextView)findViewById(R.id.textView19);
        txt01 = (TextView)findViewById(R.id.create_txt);
        //progressbar
        progressBar = (ProgressBar)findViewById(R.id.create_progressBar3);
        //firebase auth
        fire_auth = FirebaseAuth.getInstance();

        //work section
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(SignUpActivity.this,SignIn.class);
                startActivity(signin);
                finish();
                overridePendingTransition(R.anim.slide_from_left,R.anim.slideout_from_right);
            }
        });

        log_txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(SignUpActivity.this,SignIn.class);
                startActivity(signin);
                finish();
                overridePendingTransition(R.anim.slide_from_left,R.anim.slideout_from_right);
            }
        });

        create_btn_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegistration();
            }
        });


    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SignUpActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private Boolean validateEmail() {
        String val = email_layout1.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email_layout1.setError(".");
            email_txt1.setVisibility(View.VISIBLE);
            return false;
        } else if (!val.matches(emailPattern)) {
            email_txt1.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Invalid email address",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            email_txt1.setVisibility(View.INVISIBLE);
            email_layout1.setError(null);
            email_layout1.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password_layout3.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password_layout3.setError(".");
            password_txt1.setVisibility(View.VISIBLE);
            return false;
        } else if (!val.matches(passwordVal)) {
            password_layout3.setError(".");
            password_txt1.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Password too weak",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(val.length()<=7){
            password_layout3.setError(".");
            password_txt1.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Password should be greater than 8 character",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            password_txt1.setVisibility(View.INVISIBLE);
            password_layout3.setError(null);
            password_layout3.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = phone_layout2.getEditText().getText().toString();
        String phoneVal = "(^([+]{1}[8]{2}|0088)?(01){1}[3-9]{1}\\d{8})$"
                ;
        if (val.isEmpty()) {
            phone_layout2.setError(".");
            phone_txt1.setVisibility(View.VISIBLE);
            return false;
        }else if(!val.matches(phoneVal))
        {
            phone_txt1.setVisibility(View.INVISIBLE);
            phone_layout2.setError(".");
            Toast.makeText(this,"Please enter valid number",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            phone_txt1.setVisibility(View.INVISIBLE);
            phone_layout2.setError(null);
            phone_layout2.setErrorEnabled(false);
            return true;
        }
    }
    public void userRegistration() {
        if(!validatePassword() | !validatePhoneNo() | !validateEmail())
        {
            return;
        }
        rootNode1 = FirebaseDatabase.getInstance();
        reference1 = rootNode1.getReference("users");
        String email = email_layout1.getEditText().getText().toString();
        String password = password_layout3.getEditText().getText().toString();
        String phone = phone_layout2.getEditText().getText().toString();
        Helper helper = new Helper(email,password,phone);
        reference1.child(phone).setValue(helper);
        userRegis2();
    }
    private void userRegis2() {
        progressBar.setVisibility(View.VISIBLE);
        String email = email_layout1.getEditText().getText().toString();
        String password = password_layout3.getEditText().getText().toString();
       fire_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    create_btn_img1.setEnabled(false);
                    Toast.makeText(SignUpActivity.this," Registration Successful",Toast.LENGTH_LONG).show();
                }else{
                    ConnectivityManager manager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                    if(null!= activeNetwork){
                        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this,"Registration failed please check your internet connection",Toast.LENGTH_SHORT).show();
                        }
                        if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this,"Registration failed please check your internet connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }
}