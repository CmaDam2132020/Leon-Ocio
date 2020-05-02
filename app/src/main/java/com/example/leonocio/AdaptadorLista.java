package com.example.leonocio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    public View getView(int index, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        final int i=index;
        final TextView titulo_elemento_lista = (TextView) vista.findViewById(R.id.titulo_elemento_lista);
        TextView direccion_elemento_lista = (TextView) vista.findViewById(R.id.direccion_elemento_lista);
        TextView categoria_elemento_lista = (TextView) vista.findViewById(R.id.categoria_elemento_lista);
        TextView puntuacion_elemento_lista = (TextView) vista.findViewById(R.id.puntuacion_elemento_lista);

        try {
            final JSONObject lugar = array_lugares.getJSONObject(i);
            titulo_elemento_lista.setText(lugar.getString("nombre"));
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

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject lugar = array_lugares.getJSONObject(i);
                    //Toast.makeText(ctx,"Listener Activado en: "+lugar.getString("nombre")  ,Toast.LENGTH_SHORT).show();
                    Intent intent_lugar_individual = new Intent(ctx,LugarIndividual.class);
                    intent_lugar_individual.putExtra("idLugar",lugar.getString("idLugar"));
                    intent_lugar_individual.putExtra("nombre",lugar.getString("nombre"));
                    intent_lugar_individual.putExtra("direccion",lugar.getString("direccion"));
                    intent_lugar_individual.putExtra("latitud",lugar.getString("latitud"));
                    intent_lugar_individual.putExtra("longitud",lugar.getString("longitud"));
                    intent_lugar_individual.putExtra("descripcion",lugar.getString("descripcion"));
                    intent_lugar_individual.putExtra("nombreCategoria",lugar.getString("nombreCategoria"));
                    intent_lugar_individual.putExtra("puntuacion",lugar.getString("puntuacion"));
                    ctx.startActivity(intent_lugar_individual);


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });





        return vista;
    }
}
