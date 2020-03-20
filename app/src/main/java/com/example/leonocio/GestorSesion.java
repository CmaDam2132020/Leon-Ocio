package com.example.leonocio;

import android.content.Context;
import android.content.SharedPreferences;

public class GestorSesion {
    public void  iniciar_sesion(Context ctx,Integer idUsuario,String nombre,String pass,String email,Boolean responsable){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sesion",true);
        editor.putInt("idUsuario",idUsuario);
        editor.putString("nombre",nombre);
        editor.putString("pass",pass);
        editor.putString("email",email);
        editor.putBoolean("responsable",responsable);
        editor.commit();
    }

    public  boolean comprobar_sesion(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        boolean bool_sesion = preferences.getBoolean("sesion",false);
        return  bool_sesion;
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

    public String sacar_nombre(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombre","Error al sacar nombre de preferencias");
        return  nombre;
    }

    public  boolean comprobar_responsable(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        boolean bool_responsable = preferences.getBoolean("responsable",false);
        return  bool_responsable;

    }
}
