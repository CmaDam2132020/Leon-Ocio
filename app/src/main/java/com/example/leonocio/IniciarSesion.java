package com.example.leonocio;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

        final EditText edit_text_nombre = findViewById(R.id.edit_text_nombre);
        final EditText edit_text_pass = findViewById(R.id.edit_text_pass);
        Button boton_iniciar_sesion_is = findViewById(R.id.boton_iniciar_sesion_is);
        View.OnClickListener listener_boton_iniciar_sesion_is = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView text_view_iniciar_sesion = findViewById(R.id.text_view_iniciar_sesion);
                StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/test.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            TextView text_view_inicar_sesion = findViewById(R.id.text_view_iniciar_sesion);

                        try {
                            JSONObject respuesta_JSON = new JSONObject(response);
                            Boolean encontrado = respuesta_JSON.getBoolean("encontrado");
                            if(encontrado){
                                text_view_inicar_sesion.setText("Encontrado");
                            }else{
                                text_view_inicar_sesion.setText("No encontrado");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                            //if(response.charAt(1)=='1'){
                            //    Toast.makeText(getApplicationContext(),"Sesion iniciada correctamente",Toast.LENGTH_SHORT).show();
                            //}else if(response.charAt(1)=='0'){
                            //Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                            //}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error al conectarse con el servidor php",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parametros = new HashMap<String,String>();
                        parametros.put("usuario",edit_text_nombre.getText().toString());
                        parametros.put("password",edit_text_pass.getText().toString());
                        return parametros;
                    }
                };


                RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                mi_queue.add(php_request);

            }
        };
        boton_iniciar_sesion_is.setOnClickListener(listener_boton_iniciar_sesion_is);
    }

}
