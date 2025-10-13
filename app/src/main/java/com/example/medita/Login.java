package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText etUsuario, etClave;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Iniciar Sesión");

        etUsuario = findViewById(R.id.etusuario);
        etClave = findViewById(R.id.etclave);
        db = new DataBase(getApplicationContext(), "dbMedita", 1);
    }

    public void Acceder(View view) {
        String usuario = etUsuario.getText().toString();
        String clave = etClave.getText().toString();

        if (usuario.equals("") || clave.equals("")) {
            Toast.makeText(this, "Ingrese usuario y clave", Toast.LENGTH_SHORT).show();
        } else {
            boolean acceso = db.Verificar_Acceso(usuario, clave);
            if (acceso) {
                Toast.makeText(this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Inicio.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o clave incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void MostrarCrearUsuario(View view) {
        Intent intent = new Intent(this, crearUsuario.class);
        startActivity(intent);
    }
}
