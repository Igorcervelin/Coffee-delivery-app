package com.example.coffeedelivery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btSignup, btLogin;
    EditText userfield, passfield;
    String user, pass;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btLogin);
        btSignup = findViewById(R.id.btSignup);
        userfield = (EditText) findViewById(R.id.userfield);
        passfield = (EditText) findViewById(R.id.passfield);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userfield.getText().toString().toLowerCase();
                pass = passfield.getText().toString().toLowerCase();
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                            if(user.equals(snapshot.getKey())){
                                db.child(user).child("password").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(pass.equals(dataSnapshot.getValue().toString())){
                                            Toast.makeText(getApplicationContext(), "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                            gtList();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Usuário não cadastrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtRegister();
            }
        });


    }

    private void gtList(){
        Intent i = new Intent(LoginActivity.this, ListActivity.class);
        startActivity(i);
        finish();
    }

    private void gtRegister(){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

}
