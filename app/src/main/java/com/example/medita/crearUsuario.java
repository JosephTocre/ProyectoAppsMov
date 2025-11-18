package com.example.medita;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class crearUsuario extends AppCompatActivity {

    EditText etNombres, etUsuario, etClave, etConfirmaClave;
    Button btnCrear;
    String URL_REGISTRO = "http://192.168.1.36/medita/registro.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        setTitle("Crear Usuario");

        etNombres = findViewById(R.id.txtapellido);
        etUsuario = findViewById(R.id.txtusuario);
        etClave = findViewById(R.id.txtclave1);
        etConfirmaClave = findViewById(R.id.txtclave2);
        btnCrear = findViewById(R.id.btnCrear);
    }

    public void RegistrarUsuario(View view) {
        String nombres = etNombres.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String clave = etClave.getText().toString().trim();
        String confirma = etConfirmaClave.getText().toString().trim();

        if (nombres.isEmpty() || usuario.isEmpty() || clave.isEmpty() || confirma.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!clave.equals(confirma)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show();

        // CAMBIO PRINCIPAL: Usar StringRequest en lugar de JsonObjectRequest
        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTRO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REGISTRO", "Respuesta: " + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getBoolean("success")) {
                                Toast.makeText(crearUsuario.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(crearUsuario.this, Login.class));
                                finish();
                            } else {
                                Toast.makeText(crearUsuario.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(crearUsuario.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REGISTRO", "Error: " + error.toString());
                        Toast.makeText(crearUsuario.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombres", nombres);
                params.put("usuario", usuario);
                params.put("clave", clave);
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // Agregar política de reintentos
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000, // 15 segundos timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void MostrarLogin(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}