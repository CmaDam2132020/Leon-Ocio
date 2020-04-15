package com.example.leonocio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
            TextView text_view_main = findViewById(R.id.text_view_main);
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
        }
    }
}
