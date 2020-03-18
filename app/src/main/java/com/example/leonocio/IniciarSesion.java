package com.example.leonocio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class IniciarSesion extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        Button boton_iniciar_sesion_is = findViewById(R.id.boton_iniciar_sesion_is);
        View.OnClickListener listener_boton_iniciar_sesion_is = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView text_view_iniciar_sesion = findViewById(R.id.text_view_iniciar_sesion);
                StringRequest php_request = new StringRequest(Request.Method.POST, "http://192.168.56.1/leon_ocio/test.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        text_view_iniciar_sesion.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        text_view_iniciar_sesion.setText("Error al conectarse al fichero php");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return super.getParams();
                    }
                };


                RequestQueue mi_queue = Volley.newRequestQueue(getApplicationContext());
                mi_queue.add(php_request);

            }
        };
        boton_iniciar_sesion_is.setOnClickListener(listener_boton_iniciar_sesion_is);
    }

}
