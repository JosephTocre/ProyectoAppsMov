package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class crearUsuario extends AppCompatActivity {

    EditText etapellido, etusuario, etclave, etconfirmaclave;
    Button btnCrear;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        setTitle("Crear Usuario");

        etapellido = findViewById(R.id.txtapellido);
        etusuario = findViewById(R.id.txtusuario);
        etclave = findViewById(R.id.txtclave1);
        etconfirmaclave = findViewById(R.id.txtclave2);
        btnCrear = findViewById(R.id.btnCrear);
        db = new DataBase(getApplicationContext(), "dbMedita", 1);
    }

    public void RegistrarUsuario(View view) {
        String apellidos = etapellido.getText().toString();
        String usuario = etusuario.getText().toString();
        String clave = etclave.getText().toString();
        String confirmacion = etconfirmaclave.getText().toString();

        if (apellidos.equals("") || usuario.equals("") || clave.equals("")) {
            Toast.makeText(this, "No olvide ingresar todos los datos", Toast.LENGTH_SHORT).show();
        } else {
            if (clave.equals(confirmacion)) {
                boolean verificar = db.VerifcarUsuarioIngresado(usuario);
                if (verificar) {
                    boolean insertar = db.RegistrarUsuario(apellidos, usuario, clave);
                    if (insertar) {
                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "El usuario ya se encuentra registrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Las claves no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void MostrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
