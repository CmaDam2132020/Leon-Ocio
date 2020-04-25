package com.example.leonocio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OpcionesUsuario extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_usuarios);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final GestorSesion gestor_sesion = new GestorSesion();
        final TextView text_view_opciones_usuarios = findViewById(R.id.text_view_opciones_usuarios);
        final TextView textView9 = findViewById(R.id.textView9);
        final EditText edit_text_nombre_opciones_usuarios = findViewById(R.id.edit_text_nombre_opciones_usuarios);
        final Button boton_comprobar_nombre_opciones_usuarios = findViewById(R.id.boton_comprobar_nombre_opciones_usuarios);
        final Button boton_cambiar_nombre_opciones_usuarios = findViewById(R.id.boton_cambiar_nombre_opciones_usuarios);
        final TextView textView10 = findViewById(R.id.textView10);
        final EditText edit_text_pass_vieja_opciones_usuarios = findViewById(R.id.edit_text_pass_vieja_opciones_usuarios);
        final TextView textView11 = findViewById(R.id.textView11);
        final EditText edit_text_pass_nueva_opciones_usuarios = findViewById(R.id.edit_text_pass_nueva_opciones_usuarios);
        final Button boton_cambiar_pass_opciones_usuarios = findViewById(R.id.boton_cambiar_pass_opciones_usuarios);
        final TextView textView12 = findViewById(R.id.textView12);
        final EditText edit_text_email_opciones_usuarios = findViewById(R.id.edit_text_email_opciones_usuarios);
        final Button boton_cambiar_email_opciones_usuarios = findViewById(R.id.boton_cambiar_email_opciones_usuarios);
        if (hasFocus) {
            if (gestor_sesion.comprobar_sesion(getApplicationContext())) {
                text_view_opciones_usuarios.setText("Hola: " + gestor_sesion.sacar_nombre(getApplicationContext()));
                textView9.setVisibility(View.VISIBLE);
                edit_text_nombre_opciones_usuarios.setVisibility(View.VISIBLE);
                boton_comprobar_nombre_opciones_usuarios.setVisibility(View.VISIBLE);
                boton_cambiar_nombre_opciones_usuarios.setVisibility(View.VISIBLE);
                textView10.setVisibility(View.VISIBLE);
                edit_text_pass_vieja_opciones_usuarios.setVisibility(View.VISIBLE);
                textView11.setVisibility(View.VISIBLE);
                edit_text_pass_nueva_opciones_usuarios.setVisibility(View.VISIBLE);
                boton_cambiar_pass_opciones_usuarios.setVisibility(View.VISIBLE);
                textView12.setVisibility(View.VISIBLE);
                edit_text_email_opciones_usuarios.setVisibility(View.VISIBLE);
                boton_cambiar_email_opciones_usuarios.setVisibility(View.VISIBLE);
                edit_text_nombre_opciones_usuarios.setText(gestor_sesion.sacar_nombre(getApplicationContext()));
                edit_text_email_opciones_usuarios.setText(gestor_sesion.sacar_email(getApplicationContext()));
            } else {
                text_view_opciones_usuarios.setText("Estas en modo invitado");
            }

            View.OnClickListener listener_comprobar_nombre = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_text_nombre_opciones_usuarios.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/comprobar_nombre.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //text_view_panel_busqueda.setText(response);
                                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);

                                    Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                    if (encontrado) {

                                        Toast.makeText(getApplicationContext(), "Ya hay un nombre asi", Toast.LENGTH_SHORT).show();


                                    } else {

                                        Toast.makeText(getApplicationContext(), "No hay un nombre asi", Toast.LENGTH_SHORT).show();
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

                                Map<String, String> parametros = new HashMap<String, String>();
                                parametros.put("nombreUsuario", edit_text_nombre_opciones_usuarios.getText().toString());
                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);

                    }

                }
            };
            boton_comprobar_nombre_opciones_usuarios.setOnClickListener(listener_comprobar_nombre);

            View.OnClickListener listener_cambiar_nombre = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_text_nombre_opciones_usuarios.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/modificar_nombre_usuario.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //text_view_opciones_usuarios.setText(response);
                                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                                ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);
                                    Boolean cambio = respuesta_JSON.getBoolean("cambio");
                                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                    if (cambio) {
                                        Toast.makeText(getApplicationContext(),"Cambio realizado",Toast.LENGTH_SHORT).show();

                                        gestor_sesion.cambiar_nombre(getApplicationContext(),edit_text_nombre_opciones_usuarios.getText().toString());
                                        text_view_opciones_usuarios.setText("Hola: " + gestor_sesion.sacar_nombre(getApplicationContext()));
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Cambio no realizado",Toast.LENGTH_SHORT).show();
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

                                Map<String, String> parametros = new HashMap<String, String>();



                                parametros.put("idUsuario", gestor_sesion.sacar_idUsuario(getApplicationContext()));
                                parametros.put("nombre", edit_text_nombre_opciones_usuarios.getText().toString());


                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);
                    }

                }
            };

            boton_cambiar_nombre_opciones_usuarios.setOnClickListener(listener_cambiar_nombre);

            View.OnClickListener listener_cambiar_pass = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_text_pass_vieja_opciones_usuarios.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce tu contraseña vieja", Toast.LENGTH_SHORT).show();
                    }else if(edit_text_pass_nueva_opciones_usuarios.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce tu contraseña nueva", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/modificar_pass_usuario.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //text_view_opciones_usuarios.setText(response);
                                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                                ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);
                                    Boolean cambio = respuesta_JSON.getBoolean("cambio");
                                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                    if (cambio) {
                                        Toast.makeText(getApplicationContext(),"Cambio realizado",Toast.LENGTH_SHORT).show();
                                        gestor_sesion.cambiar_pass(getApplicationContext(),edit_text_pass_nueva_opciones_usuarios.getText().toString());

                                    } else {
                                        Toast.makeText(getApplicationContext(),"Cambio no realizado",Toast.LENGTH_SHORT).show();
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

                                Map<String, String> parametros = new HashMap<String, String>();

                                EncriptadorMD5 mi_encriptador = new EncriptadorMD5();
                                String pass_vieja = mi_encriptador.generarMD5(edit_text_pass_vieja_opciones_usuarios.getText().toString());
                                String pass_nueva = mi_encriptador.generarMD5(edit_text_pass_nueva_opciones_usuarios.getText().toString());
                                parametros.put("idUsuario", gestor_sesion.sacar_idUsuario(getApplicationContext()));
                                parametros.put("pass_vieja", pass_vieja);
                                parametros.put("pass_nueva", pass_nueva);

                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);
                    }
                }
            };
            boton_cambiar_pass_opciones_usuarios.setOnClickListener(listener_cambiar_pass);

            View.OnClickListener listener_cambiar_email = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edit_text_email_opciones_usuarios.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce un correo electronico", Toast.LENGTH_SHORT).show();
                    }else if(!edit_text_email_opciones_usuarios.getText().toString().contains("@")){
                        Toast.makeText(getApplicationContext(), "El campo email no contiene @", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/modificar_email_usuario.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //text_view_opciones_usuarios.setText(response);
                                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                                ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);
                                    Boolean cambio = respuesta_JSON.getBoolean("cambio");
                                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                    if (cambio) {
                                        Toast.makeText(getApplicationContext(),"Cambio realizado",Toast.LENGTH_SHORT).show();
                                        gestor_sesion.cambiar_email(getApplicationContext(),edit_text_email_opciones_usuarios.getText().toString());

                                    } else {
                                        Toast.makeText(getApplicationContext(),"Cambio no realizado",Toast.LENGTH_SHORT).show();
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

                                Map<String, String> parametros = new HashMap<String, String>();



                                parametros.put("idUsuario", gestor_sesion.sacar_idUsuario(getApplicationContext()));
                                parametros.put("email", edit_text_email_opciones_usuarios.getText().toString());


                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);

                    }
                }
            };
            boton_cambiar_email_opciones_usuarios.setOnClickListener(listener_cambiar_email);


        }
    }

}
