package com.example.leonocio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PanelBusqueda extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_busqueda);
        TextView text_view_panel_busqueda = findViewById(R.id.text_view_panel_busqueda);
        GestorSesion gestor_login = new GestorSesion();
        if(gestor_login.comprobar_sesion(getApplicationContext())){
            text_view_panel_busqueda.setText("Sesion detectada");
            Button boton_ver_favoritos = findViewById(R.id.boton_ver_favoritos);
            boton_ver_favoritos.setVisibility(View.VISIBLE);



        }else{
            text_view_panel_busqueda.setText("No se detecto sesion");
        }
    }
}
