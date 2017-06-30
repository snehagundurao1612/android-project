package com.project.work.projectviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class choose_function extends AppCompatActivity implements View.OnClickListener{
    private Button logout;
    private Button edit;
    private Button viewbutton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth =FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        setContentView(R.layout.activity_choose_function);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        logout= (Button) findViewById(R.id.logout);
        edit= (Button) findViewById(R.id.editbutton);
        viewbutton= (Button) findViewById(R.id.viewbutton);
        edit.setOnClickListener(this);
       logout.setOnClickListener(this);
        viewbutton.setOnClickListener(this);
    }
public void onClick(View view){
    if(view == logout){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
    else if(view == edit){
        finish();
        startActivity(new Intent(this, ProjectDetailsActivity.class));
    }
    else if(view==viewbutton){
        finish();
        startActivity(new Intent(this,ProjectListView.class));
    }
}
}
