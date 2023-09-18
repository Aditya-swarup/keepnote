package com.example.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverybutton;
    private TextView mgobacktologin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mforgotpassword=findViewById(R.id.forgotpassword);
        mpasswordrecoverybutton=findViewById(R.id.passwordrecoverbutton);
        mgobacktologin=findViewById(R.id.gobacktologin);
        firebaseAuth=FirebaseAuth.getInstance();
        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgotpassword.this,MainActivity.class);
                        startActivity(intent);
            }
        });
           mpasswordrecoverybutton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String mail=mforgotpassword.getText().toString().trim();
                   if (mail.isEmpty() ){
                       Toast.makeText(getApplicationContext(),"Enter Your mail first", Toast.LENGTH_SHORT).show();
                   }
                    else{
                       // send email for recover
                       firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()){
                                   Toast.makeText(getApplicationContext(),"Mail Sent , You can recover your password using mail",Toast.LENGTH_SHORT).show();

                              finish();
                              startActivity(new Intent(forgotpassword.this,MainActivity.class));
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),"Account Doesn't Exists",Toast.LENGTH_SHORT).show();


                               }
                           }
                       });

                   }

               }
           });



    }
}