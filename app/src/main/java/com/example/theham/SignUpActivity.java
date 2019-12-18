package com.example.theham;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    //define view objects
    EditText editTextEmail;
    EditText name,makeid,makepw,checkpw;
    ImageView checkcircle1,checkcircle2,checkcircle3;
    EditText editTextPassword;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;

    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //추가해 줄 ProfileActivity
        }

        progressDialog = new ProgressDialog(this);
        //initializing views
        name = findViewById(R.id.NAME);
        makeid = findViewById(R.id.sign_up_email);
        makepw = findViewById(R.id.sign_up_pwd);
        checkpw = findViewById(R.id.CheckPW);

        checkcircle1 = findViewById(R.id.CheckCircle1);
        checkcircle2 = findViewById(R.id.CheckCircle2);
        checkcircle3 = findViewById(R.id.CheckCircle3);


        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (name.getText().toString().length() == 0) {
                    checkcircle1.clearColorFilter();

                } else {
                    checkcircle1.setColorFilter(Color.rgb(14, 168, 40));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (name.getText().toString().length() == 0) {
                    checkcircle1.clearColorFilter();

                }
            }

        });

        makeid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (makeid.getText().toString().length()==0){
                    checkcircle2.clearColorFilter();
                }
                else {

                    checkcircle2.setColorFilter(Color.rgb(14, 168, 40));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (makeid.getText().toString().length()==0){
                    checkcircle2.clearColorFilter();
                }

            }
        });

        makepw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (makepw.getText().toString().length()==0){
                    checkcircle3.clearColorFilter();
                }
                else {

                    checkcircle3.setColorFilter(Color.rgb(14, 168, 40));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (makepw.getText().toString().length()==0){
                    checkcircle3.clearColorFilter();
                }

            }
        });

        //button click event
//        buttonSignup.setOnClickListener((View.OnClickListener) this);
//        textviewSingin.setOnClickListener((View.OnClickListener) this);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        textviewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class)); //추가해 줄 로그인 액티비티
            }
        });
    }

    //Firebse creating a new user
    private void registerUser() {
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            //에러발생시
                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(SignUpActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }


    //button click event
//    @Override
//    public void onClick(View view) {
//        if(view == buttonSignup) {
//            //TODO
//            registerUser();
//        }
//
//        if(view == textviewSingin) {
//            //TODO
//            startActivity(new Intent(this, LoginActivity.class)); //추가해 줄 로그인 액티비티
//        }
//    }

}