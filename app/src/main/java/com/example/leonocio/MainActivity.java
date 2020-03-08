package com.example.leonocio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Este es nuestro comentario inicial vamos a
        // borrarlo y recuperarlo desde el repositorio
        // online usando pull
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
