package com.shankaryadav.www.todoapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNodes extends AppCompatActivity {

    EditText title;
    EditText des;
    Button button;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_nodes);

        title = findViewById (R.id.title);
        des = findViewById (R.id.description);
        button = findViewById (R.id.button);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();

        databaseReference = firebaseDatabase.getReference ();

        /**
         * Redundant code
         * to update and change activity
         * ***/

//        Intent intent = getIntent ();
//
//        String id = intent.getStringExtra ("id");
//        String tit = intent.getStringExtra ("title");
//        String desc = intent.getStringExtra ("desc");
//
//        title.setText (tit);
//        des.setText (desc);

        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });


    }

    public void addTask(){
        String mytitle = title.getText ().toString ();
        String mydes = des.getText ().toString ();

        String id = databaseReference.push ().getKey ();

        Nodes nodes = new Nodes(mytitle,mydes,id);

        databaseReference.child ("notes").child(id).setValue(nodes)
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText (AddNodes.this, "Node is added to the database", Toast.LENGTH_SHORT).show ();
                            startActivity (new Intent (AddNodes.this,HomeActivity.class));
                            finish ();
                        }
                    }
                });
    }
}
