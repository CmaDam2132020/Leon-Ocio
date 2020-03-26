package com.example.leonocio;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PanelBusqueda extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_busqueda);



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            Context ctx_panel = getApplicationContext();
            final TextView text_view_panel_busqueda = findViewById(R.id.text_view_panel_busqueda);
            final Button boton_cerrar_sesion_panel = findViewById(R.id.boton_cerrar_sesion_panel);
            final GestorSesion gestor_login = new GestorSesion();
            final Button boton_realizar_busqueda = findViewById(R.id.boton_realizar_busqueda);
            final EditText edit_text_panel_busqueda = findViewById(R.id.edit_text_panel_busqueda);
            if(gestor_login.comprobar_sesion(ctx_panel)){
                text_view_panel_busqueda.setText("Hola: "+gestor_login.sacar_nombre(ctx_panel));
                Button boton_ver_favoritos = findViewById(R.id.boton_ver_favoritos);
                boton_ver_favoritos.setVisibility(View.VISIBLE);
                Button boton_administrar_negocios = findViewById(R.id.boton_administar_negocios);
                if(gestor_login.comprobar_responsable(ctx_panel)){
                    boton_administrar_negocios.setVisibility(View.VISIBLE);
                }

                boton_cerrar_sesion_panel.setVisibility(View.VISIBLE);
            }else{
                text_view_panel_busqueda.setText("Estas en modo invitado");
            }

            View.OnClickListener listener_cerrar_sesion_panel = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gestor_login.cerrar_sesion(getApplicationContext());
                    text_view_panel_busqueda.setText("Estas en modo invitado");
                    boton_cerrar_sesion_panel.setVisibility(View.GONE);
                    Button boton_ver_favoritos = findViewById(R.id.boton_ver_favoritos);
                    boton_ver_favoritos.setVisibility(View.GONE);
                    Button boton_administrar_negocios = findViewById(R.id.boton_administar_negocios);
                    boton_administrar_negocios.setVisibility(View.GONE);

                }
            };
            boton_cerrar_sesion_panel.setOnClickListener(listener_cerrar_sesion_panel);

            View.OnClickListener listener_boton_realizar_busqueda = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/sacar_lugares_busqueda.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject respuesta_JSON = new JSONObject(response);
                                Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                                Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                if (encontrado) {

                                    JSONArray array_lugares = respuesta_JSON.getJSONArray("lugares");
                                    Toast.makeText(getApplicationContext(),"Hay resultados " + array_lugares.length(),Toast.LENGTH_SHORT).show();
                                    text_view_panel_busqueda.setText("");
                                    for(int i=0;i<array_lugares.length();i++){
                                        JSONObject lugar = array_lugares.getJSONObject(i);

                                        text_view_panel_busqueda.append("Lugar indice: "+i+'\n');
                                        Integer idLugar = lugar.getInt("idLugar");
                                        text_view_panel_busqueda.append("idLugar: "+idLugar+'\n');
                                        String nombre = lugar.getString("nombre");
                                        text_view_panel_busqueda.append("nombre: "+nombre+'\n');
                                        String direccion = lugar.getString("direccion");
                                        text_view_panel_busqueda.append("direccion: "+direccion+'\n');
                                        Double latitud = lugar.getDouble("latitud");
                                        text_view_panel_busqueda.append("latitud: "+latitud+'\n');
                                        Double longitud = lugar.getDouble("longitud");
                                        text_view_panel_busqueda.append("longitud: "+longitud+'\n');
                                        String descripcion = lugar.getString("descripcion");
                                        text_view_panel_busqueda.append("descripcion: "+descripcion+'\n');
                                        String nombreCategoria = lugar.getString("nombreCategoria");
                                        text_view_panel_busqueda.append("nombreCategoria: "+nombreCategoria+'\n');

                                    }



                                } else {
                                    Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_SHORT).show();

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
                            parametros.put("sql_adicional",edit_text_panel_busqueda.getText().toString() );
                            return parametros;
                        }
                    };


                    RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                    mi_queue.add(php_request);

                }
            };
            boton_realizar_busqueda.setOnClickListener(listener_boton_realizar_busqueda);
        }
    }
}
