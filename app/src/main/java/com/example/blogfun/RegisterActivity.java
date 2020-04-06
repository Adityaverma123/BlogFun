package com.example.blogfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,name,password;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingbar=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.registeremail);
        name=findViewById(R.id.registername);
        password=findViewById(R.id.registerpassword);
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().equals("") || password.getText().equals("") || name.getText().equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"All fields are required to signin",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final Map map =new HashMap();
                    map.put("email",email.getText().toString());
                    map.put("name",name.getText().toString());
                    loadingbar.setTitle("Signing up");
                    loadingbar.setMessage("Please wait...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isComplete())
                                    {
                                        FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).updateChildren(map);
                                        //Add on database
                                       loadingbar.dismiss();
                                        login();

                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this,"Signup failed",Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                }
            }
        });


    }
    private void login() {
        Intent intent=new Intent(RegisterActivity.this,HomeScreen.class);
        startActivity(intent);
        intent.putExtra("name",name.getText().toString());
        finish();
        //Main Screen
    }
}
