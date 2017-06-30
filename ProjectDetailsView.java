package com.project.work.projectviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by work on 4/28/2017.
 */

public class ProjectDetailsView extends AppCompatActivity implements View.OnClickListener{
    private TextView application_name;
    private TextView member_name;
    private TextView desc_name;
    private TextView status_name;
    private TextView link_name;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button done;
    public static ImageView upload_image;
    private String download_link;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_upload);
        application_name = (TextView) findViewById(R.id.app_text);
        member_name = (TextView) findViewById(R.id.mem_text);
        desc_name = (TextView) findViewById(R.id.desc_text);
        status_name = (TextView) findViewById(R.id.status_text);
        link_name = (TextView) findViewById(R.id.link_text);

        done = (Button) findViewById(R.id.button_done);
        done.setOnClickListener(this);
        upload_image = (ImageView) findViewById(R.id.imageView);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        progressDialog = new ProgressDialog(this);
        final String project_name = getIntent().getStringExtra("Projectname");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog.setMessage("Registering user...");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                   if(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[4]).getValue().toString().equalsIgnoreCase(project_name)){
                       application_name.setText(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[4]).getValue().toString());
                       member_name.setText(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[0]).getValue().toString());
                       desc_name.setText(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[2]).getValue().toString());
                       status_name.setText(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[3]).getValue().toString());
                       link_name.setText(((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[1]).getValue().toString());
                        String upload_link = ((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[5]).getValue().toString();
                        Picasso.with(ProjectDetailsView.this).load(upload_link).fit().centerCrop().into(upload_image);
                        //link_name.setMovementMethod(LinkMovementMethod.getInstance());
                       download_link = "https://play.google.com/store/search?q=" + ((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[1]).getValue().toString();


                   }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onClick(View view){
       if(view == done){
           finish();
           startActivity(new Intent(this,ProjectListView.class));
       }
    }
    public void click_link(View view){
        Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse(download_link));
        startActivity(link);
    }
}
