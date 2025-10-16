package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText etUsuario, etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Iniciar Sesión");

        etUsuario = findViewById(R.id.etusuario);
        etClave = findViewById(R.id.etclave);
    }

    public void Acceder(View view) {
        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Ingrese usuario y clave", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://10.0.2.2/medita/login.php";

        try {
            JSONObject json = new JSONObject();
            json.put("usuario", usuario);
            json.put("clave", clave);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    json,
                    response -> {
                        try {
                            if (response.getBoolean("success")) {
                                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(this, barra_nav.class);
                                intent.putExtra("usuario", response.getString("nombres"));
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error procesando respuesta", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void MostrarCrearUsuario(View view) {
        Intent intent = new Intent(this, crearUsuario.class);
        startActivity(intent);
    }
}
