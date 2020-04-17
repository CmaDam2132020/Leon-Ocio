package com.example.leonocio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LugarIndividual extends Activity {
    static Boolean review_publicada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lugar_individual);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            final Intent intent_lugar_individual = getIntent();
            final TextView text_view_lugar_individual = findViewById(R.id.text_view_lugar_individual);
            final GestorSesion gestor_sesion = new GestorSesion();
            final Button boton_cerrar_sesion_lugar_individual = findViewById(R.id.boton_cerrar_sesion_lugar_individual);
            final TextView text_view_nombre_li = findViewById(R.id.text_view_nombre_li);
            final TextView text_view_direccion_li = findViewById(R.id.text_view_direccion_li);
            final TextView text_view_latitud_li = findViewById(R.id.text_view_latitud_li);
            final TextView text_view_longitud_li = findViewById(R.id.text_view_longitud_li);
            final TextView text_view_descripcion_li = findViewById(R.id.text_view_descripcion_li);
            final TextView text_view_nombreCategoria_li = findViewById(R.id.text_view_nombreCategoria_li);
            final TextView text_view_puntuacion_li = findViewById(R.id.text_view_puntuacion_li);
            final TextView text_view_8_li = findViewById(R.id.textView8_li);
            final RatingBar rating_bar_lugar_individual = findViewById(R.id.rating_bar_lugar_individual);
            final EditText edit_text_review_usuario = findViewById(R.id.edit_text_review_usuario);
            final Button boton_enviar_review = findViewById(R.id.boton_enviar_review);

            review_publicada = true;

            if (gestor_sesion.comprobar_sesion(getApplicationContext())) {
                text_view_lugar_individual.setText("Hola: " + gestor_sesion.sacar_nombre(getApplicationContext()));

                boton_cerrar_sesion_lugar_individual.setVisibility(View.VISIBLE);
                text_view_8_li.setVisibility(View.VISIBLE);

                rating_bar_lugar_individual.setVisibility(View.VISIBLE);
                edit_text_review_usuario.setVisibility(View.VISIBLE);
                boton_enviar_review.setVisibility(View.VISIBLE);
            } else {
                text_view_lugar_individual.setText("Estas en modo invitado");
            }



            //Toast.makeText(getApplicationContext(), intent_lugar_individual.getStringExtra("idLugar"),Toast.LENGTH_SHORT).show();
            text_view_nombre_li.setText(intent_lugar_individual.getStringExtra("nombre"));
            text_view_direccion_li.setText(intent_lugar_individual.getStringExtra("direccion"));
            text_view_latitud_li.setText(intent_lugar_individual.getStringExtra("latitud"));
            text_view_longitud_li.setText(intent_lugar_individual.getStringExtra("longitud"));
            text_view_descripcion_li.setText(intent_lugar_individual.getStringExtra("descripcion"));
            text_view_nombreCategoria_li.setText(intent_lugar_individual.getStringExtra("nombreCategoria"));
            String puntuacion = intent_lugar_individual.getStringExtra("puntuacion");
            if (puntuacion.equals("-1")) {
                text_view_puntuacion_li.setText("Aun no hay puntuacion \n");
            } else {
                text_view_puntuacion_li.setText(puntuacion + "/5 \n");
            }

            /*text_view_lugar_individual.setText(intent_lugar_individual.getStringExtra("nombre")
                            +intent_lugar_individual.getStringExtra("direccion")
                            +intent_lugar_individual.getStringExtra("latitud")
                            +intent_lugar_individual.getStringExtra("longitud")
                            +intent_lugar_individual.getStringExtra("descripcion")
                            +intent_lugar_individual.getStringExtra("nombreCategoria")
                            +intent_lugar_individual.getStringExtra("puntuacion")
                    );*/


            View.OnClickListener listener_boton_cerrar_sesion_inicio_sesion = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gestor_sesion.cerrar_sesion(getApplicationContext());
                    text_view_lugar_individual.setText("Estas en modo invitado");
                    boton_cerrar_sesion_lugar_individual.setVisibility(View.GONE);
                    text_view_8_li.setVisibility(View.GONE);
                    rating_bar_lugar_individual.setVisibility(View.GONE);
                    edit_text_review_usuario.setVisibility(View.GONE);
                    boton_enviar_review.setVisibility(View.GONE);
                }
            };
            boton_cerrar_sesion_lugar_individual.setOnClickListener(listener_boton_cerrar_sesion_inicio_sesion);

            View.OnClickListener listener_enviar_review = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest php_request_comprobar_review = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/comprobar_review.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                            //ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                            try {
                                JSONObject respuesta_JSON = new JSONObject(response);

                                Boolean cambio = respuesta_JSON.getBoolean("encontrado");
                                //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                if (cambio) {
                                    review_publicada = true;
                                    //Toast.makeText(getApplicationContext(), "Encontrada review anterior", Toast.LENGTH_SHORT).show();


                                } else {
                                    review_publicada = false;
                                    //Toast.makeText(getApplicationContext(), "No se ha encontrada review anterior", Toast.LENGTH_SHORT).show();
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

                            parametros.put("idLugar", intent_lugar_individual.getStringExtra("idLugar"));


                            return parametros;
                        }
                    };



                    RequestQueue mi_queue_comprobar_reiews = Volley.newRequestQueue(getApplicationContext());
                    RequestQueue.RequestFinishedListener lisetner_comprobar_review = new RequestQueue.RequestFinishedListener() {
                        @Override
                        public void onRequestFinished(Request request) {
                            if (review_publicada) {
                                Toast.makeText(getApplicationContext(), "Ya has hecho una review", Toast.LENGTH_SHORT).show();
                            } else {
                                StringRequest php_request2 = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/enviar_review.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        //text_view_lugar_individual.setText(response);
                                        //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                                        //ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                                        try {
                                            JSONObject respuesta_JSON = new JSONObject(response);
                                            Boolean cambio = respuesta_JSON.getBoolean("cambio");
                                            //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                                            if (cambio) {


                                                Toast.makeText(getApplicationContext(), "Review enviada", Toast.LENGTH_SHORT).show();


                                            } else {
                                                ListView list_view_reviews = findViewById(R.id.list_view_reviews);
                                                Toast.makeText(getApplicationContext(), "Review no enviada", Toast.LENGTH_SHORT).show();
                                                list_view_reviews.setAdapter(new AdaptadorVacio());
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

                                        String fecha_actual = "";
                                        fecha_actual += Calendar.getInstance().get(Calendar.YEAR);
                                        fecha_actual += "-";
                                        fecha_actual += Calendar.getInstance().get(Calendar.MONTH) + 1;
                                        fecha_actual += "-";
                                        fecha_actual += Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                                        parametros.put("fecha", "'" + fecha_actual + "'");
                                        RatingBar rating_bar_lugar_individual = findViewById(R.id.rating_bar_lugar_individual);
                                        parametros.put("puntuacion", String.valueOf(rating_bar_lugar_individual.getRating()));

                                        parametros.put("comentario", edit_text_review_usuario.getText().toString());

                                        parametros.put("idUsuario", gestor_sesion.sacar_idUsuario(getApplicationContext()));
                                        parametros.put("idLugar", intent_lugar_individual.getStringExtra("idLugar"));


                                        return parametros;
                                    }
                                };


                                RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                                mi_queue.add(php_request2);
                                review_publicada = true;
                                RequestQueue.RequestFinishedListener listener = new RequestQueue.RequestFinishedListener() {
                                    @Override
                                    public void onRequestFinished(Request request) {
                                        sacar_reviews(intent_lugar_individual);

                                    }
                                };
                                mi_queue.addRequestFinishedListener(listener);


                            }
                        }
                    };
                    mi_queue_comprobar_reiews.addRequestFinishedListener(lisetner_comprobar_review);
                    mi_queue_comprobar_reiews.add(php_request_comprobar_review);


                    //Toast.makeText(getApplicationContext(),"Boton enviar review pulsado",Toast.LENGTH_SHORT).show();



                }
            };
            boton_enviar_review.setOnClickListener(listener_enviar_review);


            sacar_reviews(intent_lugar_individual);


        }
    }

    public void sacar_reviews(final Intent intent_lugar_individual){
        StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/sacar_reviews.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //text_view_lugar_individual.setText(response);
                //Toast.makeText(getApplicationContext(),edit_text_panel_busqueda.getText().toString(),Toast.LENGTH_SHORT).show();
                //ListView list_view_busqueda = findViewById(R.id.list_view_busqueda);
                try {
                    JSONObject respuesta_JSON = new JSONObject(response);
                    Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                    //Toast.makeText(getApplicationContext(),encontrado+"",Toast.LENGTH_SHORT).show();
                    if (encontrado) {

                        JSONArray array_reviews = respuesta_JSON.getJSONArray("reviews");
                        Toast.makeText(getApplicationContext(), "Hay " + array_reviews.length() + " reviews encontradas", Toast.LENGTH_SHORT).show();


                                    /*for(int i=0;i<array_reviews.length();i++){
                                        JSONObject review = array_reviews.getJSONObject(i);

                                        text_view_lugar_individual.append("Lugar indice: "+i+'\n');
                                        Integer idReview = review.getInt("idReview");
                                        text_view_lugar_individual.append("Id : "+idReview+'\n');
                                        String fecha = review.getString("fecha");
                                        text_view_lugar_individual.append("Fecha : "+fecha+'\n');

                                    }*/
                        ListView list_view_reviews = findViewById(R.id.list_view_reviews);
                        list_view_reviews.setAdapter(new AdaptadorReviews(getApplicationContext(), array_reviews));


                    } else {
                        ListView list_view_reviews = findViewById(R.id.list_view_reviews);
                        Toast.makeText(getApplicationContext(), "No se han encontrado reviews", Toast.LENGTH_SHORT).show();
                        list_view_reviews.setAdapter(new AdaptadorVacio());
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
                parametros.put("idLugar", intent_lugar_individual.getStringExtra("idLugar"));
                //parametros.put("idLugar", "2" );


                return parametros;
            }
        };


        RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
        mi_queue.add(php_request);


    }
}
