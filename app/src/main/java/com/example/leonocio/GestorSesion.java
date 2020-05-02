package com.example.leonocio;

import android.content.Context;
import android.content.SharedPreferences;

public class GestorSesion {
    public void  iniciar_sesion(Context ctx,String idUsuario,String nombre,String pass,String email,Boolean responsable){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sesion",true);
        editor.putString("idUsuario",idUsuario);
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
    public String sacar_email(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        String email = preferences.getString("email","Error al sacar email de preferencias");
        return  email;
    }

    public  boolean comprobar_responsable(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        boolean bool_responsable = preferences.getBoolean("responsable",false);
        return  bool_responsable;

    }

    public String sacar_idUsuario(Context ctx){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        String idUsuario = preferences.getString("idUsuario","");
        return  idUsuario;
    }

    public void cambiar_nombre(Context ctx,String nombre_nuevo){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombre",nombre_nuevo);
        editor.commit();
    }

    public void cambiar_pass(Context ctx,String pass_nueva){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pass",pass_nueva);
        editor.commit();
    }

    public void cambiar_email(Context ctx,String email){
        SharedPreferences preferences = ctx.getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.commit();
    }
}
