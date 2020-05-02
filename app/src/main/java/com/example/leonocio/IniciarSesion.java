package com.example.leonocio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class IniciarSesion extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {


            final EditText edit_text_nombre = findViewById(R.id.edit_text_nombre);
            final EditText edit_text_pass = findViewById(R.id.edit_text_pass);
            Button boton_iniciar_sesion_is = findViewById(R.id.boton_iniciar_sesion_is);
            final GestorSesion gestor_sesion = new GestorSesion();
            final TextView text_view_iniciar_sesion = findViewById(R.id.text_view_iniciar_sesion);
            if(gestor_sesion.comprobar_sesion(getApplicationContext())){
                text_view_iniciar_sesion.setText("Hola: "+gestor_sesion.sacar_nombre(getApplicationContext()));
                Button boton_cerrar_sesion_inicio_sesion = findViewById(R.id.boton_cerrar_sesion_inicio_sesion);
                boton_cerrar_sesion_inicio_sesion.setVisibility(View.VISIBLE);
            }else{
                text_view_iniciar_sesion.setText("Estas en modo invitado");
            }


            View.OnClickListener listener_boton_iniciar_sesion_is = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gestor_sesion.comprobar_sesion(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Cierra sesion antes", Toast.LENGTH_SHORT).show();
                    } else {
                        final TextView text_view_iniciar_sesion = findViewById(R.id.text_view_iniciar_sesion);

                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/login.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);
                                    Boolean encontrado = respuesta_JSON.getBoolean("encontrado");

                                    if (encontrado) {
                                        String idUsuario = respuesta_JSON.getString("idUsuario");

                                        String nombre = respuesta_JSON.getString("nombre");
                                        String pass = respuesta_JSON.getString("pass");
                                        String email = respuesta_JSON.getString("email");
                                        Boolean responsable = false;
                                        Boolean bool_responsable = respuesta_JSON.getBoolean("responsable");
                                        if (bool_responsable) {
                                            responsable = true;
                                        }
                                        GestorSesion gestor_sesion = new GestorSesion();
                                        gestor_sesion.iniciar_sesion(getApplicationContext(), idUsuario, nombre, pass, email, responsable);
                                        Toast.makeText(getApplicationContext(),"Login con exito",Toast.LENGTH_SHORT).show();
                                        Intent intent_panel_busqueda = new Intent(getApplicationContext(), PanelBusqueda.class);
                                        startActivityForResult(intent_panel_busqueda, 2);
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Usuario o contraseña erroneos",Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error al conectarse con el servidor php", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                EncriptadorMD5 mi_encriptador = new EncriptadorMD5();
                                Map<String, String> parametros = new HashMap<String, String>();
                                parametros.put("usuario", edit_text_nombre.getText().toString());
                                parametros.put("password", mi_encriptador.generarMD5(edit_text_pass.getText().toString()));
                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);


                    }


                }
            };
            boton_iniciar_sesion_is.setOnClickListener(listener_boton_iniciar_sesion_is);

            final Button boton_cerrar_sesion_inicio_sesion = findViewById(R.id.boton_cerrar_sesion_inicio_sesion);
            View.OnClickListener listener_boton_cerrar_sesion_inicio_sesion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gestor_sesion.cerrar_sesion(getApplicationContext());
                    text_view_iniciar_sesion.setText("Estas en modo invitado");
                    boton_cerrar_sesion_inicio_sesion.setVisibility(View.GONE);
                }
            };
            boton_cerrar_sesion_inicio_sesion.setOnClickListener(listener_boton_cerrar_sesion_inicio_sesion);

        }
    }
}
