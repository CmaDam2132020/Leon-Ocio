package com.example.leonocio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
            final Context ctx_panel = getApplicationContext();
            final TextView text_view_panel_busqueda = findViewById(R.id.text_view_panel_busqueda);
            final Button boton_cerrar_sesion_panel = findViewById(R.id.boton_cerrar_sesion_panel);
            final Button boton_opciones_usuarios = findViewById(R.id.boton_opciones_usuarios);
            final GestorSesion gestor_login = new GestorSesion();
            final Button boton_realizar_busqueda = findViewById(R.id.boton_realizar_busqueda);
            final EditText edit_text_panel_busqueda = findViewById(R.id.edit_text_panel_busqueda);
            final Button boton_administrar_negocios = findViewById(R.id.boton_administar_negocios);
            final ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
            final Button boton_ver_favoritos = findViewById(R.id.boton_ver_favoritos);
            final Button boton_ver_recomendados = findViewById(R.id.boton_ver_recomendados);
            list_view_busqueda.setAdapter(new AdaptadorVacio());
            if(gestor_login.comprobar_sesion(ctx_panel)){
                text_view_panel_busqueda.setText("Hola: "+gestor_login.sacar_nombre(ctx_panel));

                boton_ver_favoritos.setVisibility(View.VISIBLE);

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
                    list_view_busqueda.setAdapter(new AdaptadorVacio());
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
                            //text_view_panel_busqueda.setText(response);
                            //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                            //ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                            try {
                                JSONObject respuesta_JSON = new JSONObject(response);
                                Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                                //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                if (encontrado) {

                                    JSONArray array_lugares = respuesta_JSON.getJSONArray("lugares");
                                    Toast.makeText(getApplicationContext(),"Hay resultados " + array_lugares.length(),Toast.LENGTH_SHORT).show();


                                    /*for(int i=0;i<array_lugares.length();i++){
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
                                        Double puntuacion = lugar.getDouble("puntuacion");
                                        text_view_panel_busqueda.append("puntuacion: "+puntuacion+'\n');



                                    }*/
                                    list_view_busqueda.setAdapter(new AdaptadorLista(ctx_panel,array_lugares));



                                } else {
                                    Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_SHORT).show();
                                    list_view_busqueda.setAdapter(new AdaptadorVacio());
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
                            parametros.put("sql_busqueda",edit_text_panel_busqueda.getText().toString() );
                            Spinner spinner_categorias = findViewById(R.id.spinner_categorias);
                            if(spinner_categorias.getSelectedItem().toString().equals("Cualquiera")){
                                parametros.put("sql_categoria","%" );
                            }else{
                                parametros.put("sql_categoria",spinner_categorias.getSelectedItem().toString());
                            }
                            Spinner spinner_orden_busqueda = findViewById(R.id.spinner_orden_busqueda);
                            if(spinner_orden_busqueda.getSelectedItem().toString().equals("Alfabetico")){
                                parametros.put("sql_orden"," ORDER BY nombre" );
                            }else if(spinner_orden_busqueda.getSelectedItem().toString().equals("Puntuacion")){
                                parametros.put("sql_orden"," ORDER BY puntuacion DESC " );

                            }


                            return parametros;
                        }
                    };


                    RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                    mi_queue.add(php_request);

                }
            };
            boton_realizar_busqueda.setOnClickListener(listener_boton_realizar_busqueda);

            View.OnClickListener listener_ver_favoritos = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idResponsable = gestor_login.sacar_idUsuario(getApplicationContext());
                    sacar_lugares_favoritos(ctx_panel,edit_text_panel_busqueda,idResponsable);

                }
            };
            boton_ver_favoritos.setOnClickListener(listener_ver_favoritos);

            View.OnClickListener listener_administrar_negocios = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/sacar_lugares_administrados.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //text_view_panel_busqueda.setText(response);
                            //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                            ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                            try {
                                JSONObject respuesta_JSON = new JSONObject(response);
                                Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                                //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                if (encontrado) {

                                    JSONArray array_lugares = respuesta_JSON.getJSONArray("lugares");
                                    Toast.makeText(getApplicationContext(),"Hay resultados " + array_lugares.length(),Toast.LENGTH_SHORT).show();

                                    list_view_busqueda.setAdapter(new AdaptadorAdministrados(ctx_panel,array_lugares));



                                } else {
                                    Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_SHORT).show();
                                    list_view_busqueda.setAdapter(new AdaptadorVacio());
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



                            parametros.put("idResponsable", gestor_login.sacar_idUsuario(getApplicationContext()));

                            parametros.put("sql_busqueda",edit_text_panel_busqueda.getText().toString() );
                            Spinner spinner_categorias = findViewById(R.id.spinner_categorias);
                            if(spinner_categorias.getSelectedItem().toString().equals("Cualquiera")){
                                parametros.put("sql_categoria","%" );
                            }else{
                                parametros.put("sql_categoria",spinner_categorias.getSelectedItem().toString());
                            }
                            Spinner spinner_orden_busqueda = findViewById(R.id.spinner_orden_busqueda);
                            if(spinner_orden_busqueda.getSelectedItem().toString().equals("Alfabetico")){
                                parametros.put("sql_orden"," ORDER BY nombre" );
                            }else if(spinner_orden_busqueda.getSelectedItem().toString().equals("Puntuacion")){
                                parametros.put("sql_orden"," ORDER BY puntuacion DESC " );

                            }

                            return parametros;
                        }
                    };


                    RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                    mi_queue.add(php_request);



                }
            };
            boton_administrar_negocios.setOnClickListener(listener_administrar_negocios);

            View.OnClickListener listener_ver_recomendados = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sacar_lugares_favoritos(ctx_panel,edit_text_panel_busqueda,"1");
                }
            };
            boton_ver_recomendados.setOnClickListener(listener_ver_recomendados);

            View.OnClickListener listener_opciones_usuario = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_opciones_usuarios = new Intent(ctx_panel,OpcionesUsuario.class);
                    startActivityForResult(intent_opciones_usuarios,7);

                }
            };
            boton_opciones_usuarios.setOnClickListener(listener_opciones_usuario);

        }
    }

    public void sacar_lugares_favoritos(final Context ctx_panel, final EditText edit_text_panel_busqueda, final String idUsuario){
        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/sacar_lugares_favoritos.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //text_view_panel_busqueda.setText(response);
                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                try {
                    JSONObject respuesta_JSON = new JSONObject(response);
                    Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                    if (encontrado) {

                        JSONArray array_lugares = respuesta_JSON.getJSONArray("lugares");
                        Toast.makeText(getApplicationContext(),"Hay resultados " + array_lugares.length(),Toast.LENGTH_SHORT).show();

                        list_view_busqueda.setAdapter(new AdaptadorLista(ctx_panel,array_lugares));



                    } else {
                        Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_SHORT).show();
                        list_view_busqueda.setAdapter(new AdaptadorVacio());
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



                parametros.put("idUsuario", idUsuario);

                parametros.put("sql_busqueda",edit_text_panel_busqueda.getText().toString() );
                Spinner spinner_categorias = findViewById(R.id.spinner_categorias);
                if(spinner_categorias.getSelectedItem().toString().equals("Cualquiera")){
                    parametros.put("sql_categoria","%" );
                }else{
                    parametros.put("sql_categoria",spinner_categorias.getSelectedItem().toString());
                }
                Spinner spinner_orden_busqueda = findViewById(R.id.spinner_orden_busqueda);
                if(spinner_orden_busqueda.getSelectedItem().toString().equals("Alfabetico")){
                    parametros.put("sql_orden"," ORDER BY nombre" );
                }else if(spinner_orden_busqueda.getSelectedItem().toString().equals("Puntuacion")){
                    parametros.put("sql_orden"," ORDER BY puntuacion DESC " );

                }

                return parametros;
            }
        };


        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
        mi_queue.add(php_request);


    }
}
