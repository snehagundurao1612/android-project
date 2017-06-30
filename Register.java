package com.project.work.projectviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private  EditText password;
    private EditText confirm_password;
    private EditText project_name;
    private EditText username;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(Register.this, choose_function.class);
            startActivity(intent);
        }
        progressDialog = new ProgressDialog(this);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        register = (Button) findViewById(R.id.resgister_button);

        register.setOnClickListener(this);

    }

    public void onClick(View view){
if(view==register){
    registerUser();
}
}


    private void registerUser(){
        final String email_text=email.getText().toString().trim();
        final String password_text=password.getText().toString().trim();
        final String confirm_text=confirm_password.getText().toString().trim();
        if(TextUtils.isEmpty(email_text)){
            Toast.makeText(this,"please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password_text)){
            Toast.makeText(this,"please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirm_text)){
            Toast.makeText(this,"please confirm password",Toast.LENGTH_SHORT).show();
            return;
        }

if(password_text.equals(confirm_text)) {
    progressDialog.setMessage("Registering user...");
    progressDialog.show();
    firebaseAuth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, "registered succesfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } else {
                Toast.makeText(Register.this, "could not register", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
        else{
    Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }
        /*public void registerUser(){
         //   Log.d("goind inside user",String.valueOf(Request.Method.POST));
        final String email_text=email.getText().toString().trim();
          //  Log.d("email is",email_text);
        final String password_text=password.getText().toString().trim();
          //  Log.d("password is",password_text);
        final String confirm_password_text=confirm_password.getText().toString().trim();
        final String project_text=project_name.getText().toString().trim();
            final String username_text=username.getText().toString().trim();
           // Log.d("project  is",project_text);
        StringRequest stringrequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                       // Log.d("going inside resposne",REGISTER_URL);
                       test=response;
                        showToast();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                       // Log.d("going inside error",error.toString());
                        Toast.makeText(Register.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }


                ){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put(Key_Email,email_text);
                params.put(Key_password, password_text);
                params.put(Key_confirm_passord, confirm_password_text);
                params.put(Key_project, project_text);
                params.put(user_name, username_text);
                return params;

            }
        };

        RequestQueue requestqueue = Volley.newRequestQueue(this);

            stringrequest.setRetryPolicy(new DefaultRetryPolicy( 100, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestqueue.add(stringrequest);


    }
    private void showToast() {

        Toast.makeText(Register.this, test, Toast.LENGTH_SHORT).show();
    }*/
    }


