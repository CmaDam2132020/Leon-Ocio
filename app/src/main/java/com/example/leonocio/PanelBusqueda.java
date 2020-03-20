package com.example.leonocio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        }
    }
}
