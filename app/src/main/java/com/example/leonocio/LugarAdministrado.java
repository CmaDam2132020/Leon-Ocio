package com.example.leonocio;

import android.app.Activity;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LugarAdministrado extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lugar_administrado);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final GestorSesion gestor_sesion = new GestorSesion();
        final TextView text_view_lugar_administrado = findViewById(R.id.text_view_lugar_administrado);
        final Intent intent_lugar_administrado = getIntent();
        final EditText edit_text_nombre_lugar_administrado = findViewById(R.id.edit_text_nombre_lugar_administrado);
        final Button boton_cambiar_nombre = findViewById(R.id.boton_cambiar_nombre);
        final EditText edit_text_direccion_lugar_administrado = findViewById(R.id.edit_text_direccion_lugar_administrado);
        final Button boton_cambiar_direccion = findViewById(R.id.boton_cambiar_direccion);
        final EditText edit_text_visiblidad_lugar_administrado = findViewById(R.id.edit_text_visiblidad_lugar_administrado);
        final Button boton_cambiar_visiblidad = findViewById(R.id.boton_cambiar_visiblidad);
        final EditText edit_text_latitud_lugar_administrado = findViewById(R.id.edit_text_latitud_lugar_administrado);
        final Button boton_cambiar_latitud = findViewById(R.id.boton_cambiar_latitud);
        final EditText edit_text_longitud_lugar_administrado = findViewById(R.id.edit_text_longitud_lugar_administrado);
        final Button boton_cambiar_longitud = findViewById(R.id.boton_cambiar_longitud);
        final EditText edit_text_descripcion_lugar_administrado = findViewById(R.id.edit_text_descripcion_lugar_administrado);
        final Button boton_cambiar_descripcion = findViewById(R.id.boton_cambiar_descripcion);
        final TextView text_view_categoria = findViewById(R.id.textView7);
        final Spinner spinner_categorias_lugar_administrado = findViewById(R.id.spinner_categorias_lugar_administrado);
        final Button boton_cambiar_categoria = findViewById(R.id.boton_cambiar_categoria);
        if (hasFocus) {
            if (gestor_sesion.comprobar_sesion(getApplicationContext())) {
                text_view_lugar_administrado.setText("Hola: " + gestor_sesion.sacar_nombre(getApplicationContext()));
            } else {
                text_view_lugar_administrado.setText("Estas en modo invitado");
            }

            edit_text_nombre_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("nombre"));
            edit_text_direccion_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("direccion"));
            edit_text_visiblidad_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("visible"));
            edit_text_latitud_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("latitud"));
            edit_text_longitud_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("longitud"));
            edit_text_descripcion_lugar_administrado.setText(intent_lugar_administrado.getStringExtra("descripcion"));
            text_view_categoria.setText("Categoria: "+intent_lugar_administrado.getStringExtra("nombreCategoria"));
            View.OnClickListener listener_cambiar_nombre = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"Boton cambiar nombre pulsado",Toast.LENGTH_SHORT).show();
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"nombre",edit_text_nombre_lugar_administrado);
                }
            };
            boton_cambiar_nombre.setOnClickListener(listener_cambiar_nombre);

            View.OnClickListener listener_cambiar_direccion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"direccion",edit_text_direccion_lugar_administrado);
                }
            };
            boton_cambiar_direccion.setOnClickListener(listener_cambiar_direccion);
            View.OnClickListener listener_cambiar_visiblidad = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"visible",edit_text_visiblidad_lugar_administrado);
                }
            };
            boton_cambiar_visiblidad.setOnClickListener(listener_cambiar_visiblidad);
            View.OnClickListener listener_cambiar_latitud = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"latitud",edit_text_latitud_lugar_administrado);
                }
            };
            boton_cambiar_latitud.setOnClickListener(listener_cambiar_latitud);
            View.OnClickListener listener_cambiar_longitud  = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"longitud",edit_text_longitud_lugar_administrado);
                }
            };
            boton_cambiar_longitud.setOnClickListener(listener_cambiar_longitud);

            View.OnClickListener listener_cambiar_descripcion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiar_parametro(text_view_lugar_administrado,intent_lugar_administrado,"descripcion",edit_text_descripcion_lugar_administrado);
                }
            };
            boton_cambiar_descripcion.setOnClickListener(listener_cambiar_descripcion);
            View.OnClickListener listener_cambiar_categoria = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/modificar_parametro_lugar_categoria.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //text_view_lugar_administrado.setText(response);
                            //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                            ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                            try {
                                JSONObject respuesta_JSON = new JSONObject(response);
                                Boolean cambio = respuesta_JSON.getBoolean("cambio");
                                //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                if (cambio) {
                                    Toast.makeText(getApplicationContext(),"Cambio realizado",Toast.LENGTH_SHORT).show();
                                    text_view_categoria.setText("Categoria: "+spinner_categorias_lugar_administrado.getSelectedItem().toString());
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



                            parametros.put("idLugar", intent_lugar_administrado.getStringExtra("idLugar"));

                            parametros.put("valor", spinner_categorias_lugar_administrado.getSelectedItem().toString() );

                            return parametros;
                        }
                    };


                    RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                    mi_queue.add(php_request);
                }
            };
            boton_cambiar_categoria.setOnClickListener(listener_cambiar_categoria);

        }

    }
    public void cambiar_parametro(final TextView text_view_lugar_administrado, final Intent intent_lugar_administrado, final String parametro, final EditText edit_text_nombre_lugar_administrado){
        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/modificar_parametro_lugar.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //text_view_lugar_administrado.setText(response);
                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                try {
                    JSONObject respuesta_JSON = new JSONObject(response);
                    Boolean cambio = respuesta_JSON.getBoolean("cambio");
                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                    if (cambio) {
                        Toast.makeText(getApplicationContext(),"Cambio realizado",Toast.LENGTH_SHORT).show();

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



                parametros.put("idLugar", intent_lugar_administrado.getStringExtra("idLugar"));
                parametros.put("parametro",parametro );
                parametros.put("valor", edit_text_nombre_lugar_administrado.getText().toString() );

                return parametros;
            }
        };


        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
        mi_queue.add(php_request);
    }



}
