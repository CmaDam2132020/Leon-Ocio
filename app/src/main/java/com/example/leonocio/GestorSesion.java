package com.example.leonocio;

import android.content.Context;
import android.content.SharedPreferences;

public class GestorSesion {
    public void  iniciar_sesion(Context ctx,String idUsuario,String nombre,String pass,String email,String responsable){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sesion",true);
        editor.putString("idUsuario",idUsuario);
        editor.putString("nombre",nombre);
        editor.putString("pass",pass);
        editor.putString("email",email);
        editor.putString("responsable",responsable);
        editor.commit();
    }

    public  boolean comprobar_sesion(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        boolean sw_sesion = preferences.getBoolean("sesion",false);
        return  sw_sesion;
    }

    public void cerrar_sesion(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sesion",false);
        editor.putString("idUsuario","");
        editor.putString("nombre","");
        editor.putString("pass","");
        editor.putString("email","");
        editor.putString("responsable","");
        editor.commit();
    }
}
