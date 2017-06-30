package com.project.work.projectviewer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by work on 4/25/2017.
 */


public class ProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 8675309;
    private EditText appname;
    private EditText members;
    private EditText description;
    private EditText status;
    private EditText downloadlink;
    private Button submit;
    private Button logout;
    private Button upload;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private static final int CAMERA_REQUEST_CODE= 1;
    private StorageReference mstorage;
   private  Uri download;
private int flag;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_page);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        mstorage = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        appname = (EditText) findViewById(R.id.appname);

        members = (EditText) findViewById(R.id.members);
        description = (EditText) findViewById(R.id.desc);
        status = (EditText) findViewById(R.id.status);
        downloadlink = (EditText) findViewById(R.id.link);
        submit = (Button)findViewById(R.id.submit);
        logout = (Button)findViewById(R.id.logout);
        upload = (Button)findViewById(R.id.upload);
            submit.setOnClickListener(this);
            logout.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    public void saveUserInfo(){
        String app_name = appname.getText().toString().trim();
        String member_name = members.getText().toString().trim();
        String desc_name = description.getText().toString().trim();
        String status_name = status.getText().toString().trim();
        String downloadlink_name = downloadlink.getText().toString().trim();
        String url = download.toString();
        if(TextUtils.isEmpty(app_name) || TextUtils.isEmpty(member_name) || TextUtils.isEmpty(desc_name)||TextUtils.isEmpty(status_name)|| TextUtils.isEmpty(downloadlink_name)){
            Toast.makeText(this,"please enter all the details",Toast.LENGTH_SHORT).show();
return;
        }

      /*  if(TextUtils.isEmpty(member_name)){
            Toast.makeText(this,"please enter the members",Toast.LENGTH_SHORT).show();


        }if(TextUtils.isEmpty(desc_name)){
            Toast.makeText(this,"please enter the description",Toast.LENGTH_SHORT).show();


        }if(TextUtils.isEmpty(status_name)){
            Toast.makeText(this,"please enter the status",Toast.LENGTH_SHORT).show();


        }
        if(TextUtils.isEmpty(downloadlink_name)){
            Toast.makeText(this,"please provide the download link",Toast.LENGTH_SHORT).show();


        }*/
else {
            ProjectInformation project = new ProjectInformation(app_name, member_name, desc_name, status_name, downloadlink_name, url);
            FirebaseUser user = firebaseAuth.getCurrentUser();
            databaseReference.child(user.getUid()).setValue(project);
            Toast.makeText(this, "Information saved", Toast.LENGTH_LONG).show();

        }
    }

    public void onClick(View view){
        if(view == upload){

           if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
               Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(intent1, CAMERA_REQUEST_CODE);
               flag =1;

           }
            else{
               String[] permission = {Manifest.permission.CAMERA};
               requestPermissions(permission, CAMERA_PERMISSION_REQUEST_CODE);
           }
        }

       if(view==logout){
            //firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,choose_function.class));
        }
        else if(view==submit){
           if(flag == 1) {
               saveUserInfo();
           }
           else{
               Toast.makeText(this,"please upload picture too",Toast.LENGTH_SHORT).show();
           }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, CAMERA_REQUEST_CODE);
                flag =1;
            }
            else{
                Toast.makeText(this,"cannot take photo without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            progressDialog.setMessage("uploading image");
            progressDialog.show();
//            Intent intent = getIntent();
//            Bundle bundle = intent.getExtras();
//            Uri uri = (Uri)bundle.get(Intent.EXTRA_STREAM);
//            Log.d("uri ",uri.toString());
           // Uri uri = data.getData();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] databaos = baos.toByteArray();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            StorageReference filepath = mstorage.child("photos").child(user.getUid());
            UploadTask uploadTask = filepath.putBytes(databaos);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   // productDownloadUrl = taskSnapshot.getDownloadUrl().toString();
                    //FirebaseUser user = firebaseAuth.getCurrentUser();

                    progressDialog.dismiss();
                    Toast.makeText(ProjectDetailsActivity.this, "Upload complete", Toast.LENGTH_LONG).show();

                    download = taskSnapshot.getDownloadUrl();


                   // Picasso.with(ProjectDetailsActivity.this).load(download).fit().centerCrop().into(ProjectDetailsView.upload_image);

                }
            });
        }
    }
}
