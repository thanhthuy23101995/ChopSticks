package com.nhimcoi.yuh.chopsticks.View.Login_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nhimcoi.yuh.chopsticks.DataBase.DataMembers;
import com.nhimcoi.yuh.chopsticks.Model.MemberModel;
import com.nhimcoi.yuh.chopsticks.R;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    //khởi tạo firebase auth
    FirebaseAuth firebaseAuth;
    EditText edtEmail, edtEnterPass, edtPass;
    ProgressBar progressBar;
    DataMembers dataMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        edtEmail = (EditText) findViewById(R.id.edtEmailSignUp);
        edtEnterPass = (EditText) findViewById(R.id.edtEnterPasss);
        edtPass = (EditText) findViewById(R.id.edtPassSignUp);
        firebaseAuth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar = (ProgressBar) findViewById(R.id.progessSignUp);
                progressBar.setVisibility(View.VISIBLE);
                final String email = edtEmail.getText().toString();
                String password = edtPass.getText().toString();
                String enterpass = edtEnterPass.getText().toString();
                String erro = getString(R.string.erro);
                if (email.trim().length() == 0) {
                    erro += getString(R.string.Email);

                    Toast.makeText(SignUpActivity.this, erro, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("kiemtra", email);
                } else if (password.trim().length() == 0) {
                    erro += getString(R.string.Password);

                    Toast.makeText(SignUpActivity.this, erro, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("kiemtra", password);
                } else if (enterpass.trim().length() == 0) {

                    Toast.makeText(SignUpActivity.this, enterpass, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("kiemtra", enterpass);
                } else if (!enterpass.equals(password)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.erroenterpass), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                MemberModel memberModel = new MemberModel();
                                memberModel.setHoten(email);
                                memberModel.setHinhanh("user.png");
                                String useriD = task.getResult().getUser().getUid();
                                dataMembers = new DataMembers();
                                dataMembers.AddMemberModel(memberModel, useriD);
                                Log.d("user","ok");
                                Toast.makeText(SignUpActivity.this, getString(R.string.loginsuccess), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }

            }
        });
    }
}
