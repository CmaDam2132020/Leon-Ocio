package com.example.leonocio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OpcionesUsuario extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_usuarios);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final GestorSesion gestor_sesion = new GestorSesion();
        final TextView text_view_opciones_usuarios = findViewById(R.id.text_view_opciones_usuarios);
        if (hasFocus) {
            if (gestor_sesion.comprobar_sesion(getApplicationContext())) {
                text_view_opciones_usuarios.setText("Hola: " + gestor_sesion.sacar_nombre(getApplicationContext()));
            } else {
                text_view_opciones_usuarios.setText("Estas en modo invitado");
            }

        }
    }

}
