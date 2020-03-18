package com.example.leonocio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx_main = getApplicationContext();
        Button boton_iniciar_sesion = findViewById(R.id.boton_iniciar_sesion_main);
        Button boton_acceso_invitado = findViewById(R.id.boton_acceso_invitado);
        View.OnClickListener listener_boton_iniciar_sesion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_view_main = findViewById(R.id.text_view_main);
                text_view_main.setText("Boton inciar sesion pulsado");
                Intent intent_iniciar_sesion = new Intent(ctx_main,IniciarSesion.class);
                startActivityForResult(intent_iniciar_sesion,0);
            }
        };
        boton_iniciar_sesion.setOnClickListener(listener_boton_iniciar_sesion);

        View.OnClickListener listener_boton_acceso_invitado = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_view_main = findViewById(R.id.text_view_main);
                text_view_main.setText("Boton acceso invitado pulsado");
            }
        };
        boton_acceso_invitado.setOnClickListener(listener_boton_acceso_invitado);
    }
}
