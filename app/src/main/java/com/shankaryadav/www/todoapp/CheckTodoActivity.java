package com.shankaryadav.www.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckTodoActivity extends AppCompatActivity {

    EditText title,description;
    Button update, delete;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference tochangeref;

    String id = "";
    String titleStr = "";
    String descStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_check_todo);
        title = findViewById (R.id.titleedit);
        description = findViewById (R.id.descedit);
        update = findViewById (R.id.update);
        delete = findViewById (R.id.delete);

        firebaseDatabase = FirebaseDatabase.getInstance ();

        Intent intent = getIntent ();
         id = intent.getStringExtra ("id");
         titleStr = intent.getStringExtra ("title");
         descStr = intent.getStringExtra ("desc");

         title.setText (titleStr);
         description.setText (descStr);

        tochangeref = firebaseDatabase.getReference ("notes");

        update.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String newtitle = title.getText ().toString ();
                String newDesc = description.getText ().toString ();

                Nodes nodes = new Nodes (newtitle,newDesc,id);

                tochangeref.child (id).setValue (nodes)
                        .addOnCompleteListener (new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful ()){
                                    Toast.makeText (CheckTodoActivity.this, "Your Task is updated", Toast.LENGTH_SHORT).show ();
                                    startActivity (new Intent (CheckTodoActivity.this,HomeActivity.class));
                                    finish ();
                                }

                            }
                        });
            }
        });


        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                tochangeref.child (id).removeValue ()
                        .addOnCompleteListener (new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful ()){
                                    Toast.makeText (CheckTodoActivity.this, "Your task is removed ", Toast.LENGTH_SHORT).show ();

                                    startActivity (new Intent (CheckTodoActivity.this,HomeActivity.class));

                                    finish ();
                                }

                            }
                        });

            }
        });

    }

}
