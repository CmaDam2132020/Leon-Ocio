package com.example.leonocio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdaptadorReviews extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context ctx;
    JSONArray array_reviews;

    public AdaptadorReviews(Context argctx, JSONArray json_array){
        ctx=argctx;
        array_reviews=json_array;
        inflater = (LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array_reviews.length();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_review, null);
        final int i=position;
        final TextView text_view_nombre_review = (TextView) vista.findViewById(R.id.text_view_nombre_review);
        TextView text_view_fecha_review = (TextView) vista.findViewById(R.id.text_view_fecha_review);
        TextView text_view_puntuacion_review = (TextView) vista.findViewById(R.id.text_view_puntuacion_review);
        TextView text_view_comentario_review = (TextView) vista.findViewById(R.id.text_view_comentario_review);

        try {
            final JSONObject review = array_reviews.getJSONObject(i);
            text_view_nombre_review.setText("Nombre: "+review.getString("nombre"));
            text_view_fecha_review.setText("Fecha: "+review.getString("fecha"));
            Double puntuacion = review.getDouble("puntuacion");
            if(puntuacion==-1){
                text_view_puntuacion_review.setText("Aun no hay puntuacion");
            }else{
                text_view_puntuacion_review.setText("Puntuacion: "+review.getString("puntuacion")+"/5");
            }
            text_view_comentario_review.setText("Comentario: "+review.getString("comentario"));




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vista;

    }
}
