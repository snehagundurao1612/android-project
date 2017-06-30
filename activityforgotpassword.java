package com.project.work.projectviewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class activityforgotpassword extends AppCompatActivity implements View.OnClickListener{
    Button buttonSend;
    EditText email;
    private FirebaseAuth auth;
    String email_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityforgotpassword);
        buttonSend = (Button) findViewById(R.id.email_submit_button);
        email = (EditText) findViewById(R.id.email);
        buttonSend.setOnClickListener(this);

    }



    public void onClick(View view){
        if(view == buttonSend){
            initSendEmail();

        }

    }
    private void initSendEmail() {
        auth = FirebaseAuth.getInstance();
        email_text = email.getText().toString().trim();
        if (TextUtils.isEmpty(email_text)) {
            Toast.makeText(this, "please enter the email id", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(activityforgotpassword.this, "Click Successful ", Toast.LENGTH_SHORT).show();
        else {
            auth.sendPasswordResetEmail(email_text).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(activityforgotpassword.this, "Email sent to your mail ID ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    } else {
                        Toast.makeText(activityforgotpassword.this, "Please enter the registered email address", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }



}
