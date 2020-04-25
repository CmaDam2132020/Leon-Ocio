package com.example.leonocio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            setContentView(R.layout.activity_main);
            final Context ctx_main = getApplicationContext();



            Button boton_iniciar_sesion = findViewById(R.id.boton_iniciar_sesion_main);
            Button boton_acceso_invitado = findViewById(R.id.boton_ir_a_busqueda);
            final Button boton_cerrar_sesion_inicio = findViewById(R.id.boton_cerrar_sesion_main);
            final TextView text_view_main = findViewById(R.id.text_view_main);
            final Button boton_abrir_registro = findViewById(R.id.boton_abrir_registro);
            final Button boton_comprobar_nombre = findViewById(R.id.boton_comprobar_nombre_main);
            final Button boton_enviar_registro = findViewById(R.id.boton_enviar_registro);
            final EditText editText_nombre_main = findViewById(R.id.editText_nombre_main);
            final EditText editText_pass_main = findViewById(R.id.editText_pass_main);
            final EditText editText_email_main = findViewById(R.id.editText_email_main);
            final GestorSesion gestor_sesion = new GestorSesion();
            if(gestor_sesion.comprobar_sesion(ctx_main)){
                text_view_main.setText("Hola: "+gestor_sesion.sacar_nombre(ctx_main));
                boton_cerrar_sesion_inicio.setVisibility(View.VISIBLE);
            }else{
                text_view_main.setText("Estas en modo invitado");
            }


            View.OnClickListener listener_boton_iniciar_sesion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(gestor_sesion.comprobar_sesion(ctx_main)){
                        Toast.makeText(ctx_main,"Cierra sesion antes",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent_iniciar_sesion = new Intent(ctx_main,IniciarSesion.class);
                        startActivityForResult(intent_iniciar_sesion,0);

                    }

                }
            };
            boton_iniciar_sesion.setOnClickListener(listener_boton_iniciar_sesion);

            View.OnClickListener listener_boton_acceso_invitado = new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent_panel_busqueda = new Intent(ctx_main,PanelBusqueda.class);
                    startActivityForResult(intent_panel_busqueda,1);
                }
            };
            boton_acceso_invitado.setOnClickListener(listener_boton_acceso_invitado);

            View.OnClickListener listener_boton_cerrar_sesion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView text_view_main = findViewById(R.id.text_view_main);
                    gestor_sesion.cerrar_sesion(ctx_main);
                    text_view_main.setText("Estas en modo invitado");
                    boton_cerrar_sesion_inicio.setVisibility(View.GONE);

                }
            };

            boton_cerrar_sesion_inicio.setOnClickListener(listener_boton_cerrar_sesion);

            View.OnClickListener listener_boton_abrir_registro = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView_nombre_main = findViewById(R.id.textView_nombre_main);
                    textView_nombre_main.setVisibility(View.VISIBLE);
                    editText_nombre_main.setVisibility(View.VISIBLE);
                    boton_comprobar_nombre.setVisibility(View.VISIBLE);
                    TextView textView_pass_main = findViewById(R.id.textView_pass_main);
                    textView_pass_main.setVisibility(View.VISIBLE);
                    editText_pass_main.setVisibility(View.VISIBLE);
                    TextView textView_email_main = findViewById(R.id.textView_email_main);
                    textView_email_main.setVisibility(View.VISIBLE);
                    editText_email_main.setVisibility(View.VISIBLE);
                    boton_enviar_registro.setVisibility(View.VISIBLE);

                }
            };
            boton_abrir_registro.setOnClickListener(listener_boton_abrir_registro);

            View.OnClickListener listener_boton_comprobar_nombre = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editText_nombre_main.getText().toString().equals("")){
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
                                parametros.put("nombreUsuario", editText_nombre_main.getText().toString());
                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);

                    }

                }
            };

            boton_comprobar_nombre.setOnClickListener(listener_boton_comprobar_nombre);

            View.OnClickListener listener_enviar_registro = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editText_nombre_main.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show();
                    }else if(editText_pass_main.getText().toString().equals("admin")){
                        Toast.makeText(getApplicationContext(), "Nombre no permitido", Toast.LENGTH_SHORT).show();
                    } else if(editText_pass_main.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce una contraseña", Toast.LENGTH_SHORT).show();
                    }else if(editText_email_main.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Introduce un correo electronico", Toast.LENGTH_SHORT).show();
                    }else if(editText_email_main.getText().toString().contains("@")){
                        Toast.makeText(getApplicationContext(), "El campo email no contiene @", Toast.LENGTH_SHORT).show();
                    }else{
                        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/registrar_usuario.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //text_view_main.setText(response);
                                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject respuesta_JSON = new JSONObject(response);
                                    Boolean registrado = respuesta_JSON.getBoolean("registrado");
                                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                    if (registrado) {
                                        Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Usuario no registrado",Toast.LENGTH_SHORT).show();
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
                                parametros.put("nombre", editText_nombre_main.getText().toString());
                                EncriptadorMD5 mi_encriptador = new EncriptadorMD5();
                                String pass_usuario = mi_encriptador.generarMD5(editText_pass_main.getText().toString());
                                parametros.put("pass_usuario", pass_usuario);

                                parametros.put("email", editText_email_main.getText().toString());
                                return parametros;
                            }
                        };


                        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                        mi_queue.add(php_request);
                    }
                }
            };
            boton_enviar_registro.setOnClickListener(listener_enviar_registro);
        }
    }
}
