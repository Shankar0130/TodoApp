package com.shankaryadav.www.todoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ShimmerRecyclerViewX recyclerView;

    List<Nodes> list = new ArrayList<> ();

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        final ProgressDialog progressBar = new ProgressDialog(this);

        progressBar.setCancelable(true);
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        recyclerView = findViewById (R.id.recyclerview);


        recyclerView.setHasFixedSize (true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager (linearLayoutManager);

        final TodoAdapter todoAdapter = new TodoAdapter (this,list);

        recyclerView.setAdapter (todoAdapter);
       // recyclerView.showShimmerAdapter ();


        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                startActivity (new Intent (HomeActivity.this,AddNodes.class));

                finish ();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance ();

       DatabaseReference databaseReference = firebaseDatabase.getReference ("notes");
       databaseReference.keepSynced (true);


        databaseReference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren ()){
                    Nodes nodes = dataSnapshot1.getValue (Nodes.class);
                    list.add (nodes);
                }

                todoAdapter.notifyDataSetChanged ();


                progressBar.cancel ();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText (HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show ();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish ();
        super.onBackPressed ();
    }
}
