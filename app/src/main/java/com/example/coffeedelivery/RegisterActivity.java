package com.example.coffeedelivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    public static EditText cadastronome, cadastrosobrenome, senha, repsenha;
    public static  TextView tvLogin;
    Button btRegister, btBack;
    Spinner spSetor;
    public String username;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("users");
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cadastronome = (EditText) findViewById(R.id.cadastronome);
        cadastrosobrenome = (EditText) findViewById(R.id.cadastrosobrenome);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        senha = (EditText) findViewById(R.id.senha);
        repsenha = (EditText) findViewById(R.id.repsenha);
        btRegister = (Button) findViewById(R.id.btRegister);
        btBack = (Button) findViewById(R.id.btBack);
        spSetor = (Spinner) findViewById(R.id.spSetor);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(550);
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String nome = cadastronome.getText().toString();
                            String sobrenome = cadastrosobrenome.getText().toString();
                            username = (nome + sobrenome).replace(" ", "").toLowerCase();
                            tvLogin.setText(username);
                        }});
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

        List<String> setor = new ArrayList<>(Arrays.asList("Selecione seu setor","Compras","Diretoria","Expedição","Faturamento","Faturamento","Financeiro","Laboratório","Logistica","Manutenção","Marketing","Qualidade","RH","TI","Vendas"));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, setor );

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSetor.setAdapter(dataAdapter);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtLogin();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldsverif();
                if(flag == 1) {
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if(username.equals(snapshot.getKey())) {
                                    Toast.makeText(getApplicationContext(), "Este usuário já existe, tente novamente"+snapshot, Toast.LENGTH_LONG).show();
                                } else {
                                    //register();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    private void fieldsverif(){
            if(cadastronome.getText().toString().equals("")){
                cadastronome.setError("Este campo é obrigatório!");
                Toast.makeText(getApplicationContext(), "Preencha todos os campos solicitados", Toast.LENGTH_SHORT).show();
            }if(cadastrosobrenome.getText().toString().equals("")){
                cadastrosobrenome.setError("Este campo é obrigatório!");
                Toast.makeText(getApplicationContext(), "Preencha todos os campos solicitados", Toast.LENGTH_SHORT).show();
            }if(senha.getText().toString().equals("")) {
                senha.setError("Este campo é obrigatório!");
                Toast.makeText(getApplicationContext(), "Preencha todos os campos solicitados", Toast.LENGTH_SHORT).show();
            }if(repsenha.getText().toString().equals("")){
                repsenha.setError("Este campo é obrigatório!");
                Toast.makeText(getApplicationContext(), "Preencha todos os campos solicitados", Toast.LENGTH_SHORT).show();
            }else if(spSetor.getSelectedItem().toString().equals("Selecione seu setor")){
                Toast.makeText(getApplicationContext(), "Nenhum setor selecionado", Toast.LENGTH_LONG).show();
            }else if(senha.getText().length() < 4) {
                Toast.makeText(getApplicationContext(), "Sua senha deve ter pelo menos 4 caracteres", Toast.LENGTH_LONG).show();
            }else if(((!(senha.getText().toString()).equals(repsenha.getText().toString())))){
                Toast.makeText(getApplicationContext(), "As senhas não conferem", Toast.LENGTH_LONG).show();
            }else{
                flag = 1;
            }
    }

        private void register(){
            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
            db.child(username).child("name").setValue(cadastronome.getText().toString());
            db.child(username).child("last").setValue(cadastrosobrenome.getText().toString());
            db.child(username).child("department").setValue(spSetor.getSelectedItem().toString());
            db.child(username).child("password").setValue(senha.getText().toString().toLowerCase());
            gtLogin();
        }

     private void gtLogin(){
         Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
         startActivity(i);
         finish();
         finishAffinity();
     }
}

