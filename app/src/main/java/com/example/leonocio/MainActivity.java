package com.example.leonocio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button boton_iniciar_sesion = findViewById(R.id.boton_iniciar_sesion);
        Button boton_acceso_invitado = findViewById(R.id.boton_acceso_invitado);
        View.OnClickListener listener_boton_iniciar_sesion = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_view_main = findViewById(R.id.text_view_main);
                text_view_main.setText("Boton inciar sesion pulsado");
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
