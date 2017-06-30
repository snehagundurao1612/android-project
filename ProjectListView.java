package com.project.work.projectviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created by work on 4/25/2017.
 */

public class ProjectListView extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button done;

     String eventName = " ";
    private ListView projectlist;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        projectlist = (ListView) findViewById(R.id.listview);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent= new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    eventName = ((Map.Entry)((java.util.HashMap)snap.getValue()).entrySet().toArray()[4]).getValue().toString();

                    list.add(eventName);
                }
               loadAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        projectlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),ProjectDetailsView.class);
                intent.putExtra("Projectname", list.get(position));
                startActivity(intent);

               /* databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
            }
        });

    }

    private void loadAdapter() {
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        projectlist.setAdapter(adapter);
    }
    public void onClick(View view){
        if(view == done){
            finish();
            startActivity(new Intent(this,choose_function.class));
        }
    }
}
