package com.example.leonocio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdaptadorLista extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context ctx;
    JSONArray array_lugares;
    //String datos[][];

    public AdaptadorLista(Context argctx, JSONArray json_array){
        ctx=argctx;
        array_lugares=json_array;
        inflater = (LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return array_lugares.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);

        TextView titulo_elemento_lista = (TextView) vista.findViewById(R.id.titulo_elemento_lista);
        TextView direccion_elemento_lista = (TextView) vista.findViewById(R.id.direccion_elemento_lista);
        TextView categoria_elemento_lista = (TextView) vista.findViewById(R.id.categoria_elemento_lista);
        TextView puntuacion_elemento_lista = (TextView) vista.findViewById(R.id.puntuacion_elemento_lista);

        try {
            JSONObject lugar = array_lugares.getJSONObject(i);
            titulo_elemento_lista.setText("Nombre: "+lugar.getString("nombre"));
            direccion_elemento_lista.setText("Direccion: "+lugar.getString("direccion"));
            categoria_elemento_lista.setText("Categoria: "+lugar.getString("nombreCategoria"));
            Double puntuacion = lugar.getDouble("puntuacion");
            if(puntuacion==-1){
                puntuacion_elemento_lista.setText("Aun no hay puntuacion");
            }else{
                puntuacion_elemento_lista.setText("Puntuacion: "+lugar.getString("puntuacion")+"/5");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }






        return vista;
    }
}
