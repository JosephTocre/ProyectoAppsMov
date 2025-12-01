package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText etUsuario, etClave;
    private final String URL_LOGIN = "http://192.168.100.3/medita/login.php";

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

        StringRequest request = new StringRequest(Request.Method.POST, URL_LOGIN,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getBoolean("success")) {
                            Toast.makeText(Login.this, "Acceso correcto", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, barra_nav.class);
                            intent.putExtra("usuario", jsonResponse.getString("nombres"));
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Error procesando respuesta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Login.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("clave", clave);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void MostrarCrearUsuario(View view) {
        startActivity(new Intent(this, crearUsuario.class));
    }
}
