package com.example.dai_tp5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterPersonalizado extends BaseAdapter {
    private Context context;
    private List<Pelicula> peliculas;

    public AdapterPersonalizado(Context context, List<Pelicula> peliculas) {
        this.context = context;
        this.peliculas = peliculas;
    }

    @Override
    public int getCount() {
        return peliculas.size();
    }

    @Override
    public Pelicula getItem(int position) {
        return peliculas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View VistaDevolver= LayoutInflater.from(context).inflate(R.layout.disenio_cada_celda,parent,false);
        ImageView imageView=VistaDevolver.findViewById(R.id.PosterPelicula);
        TextView titulo=VistaDevolver.findViewById(R.id.TituloPelicula);
        TextView año=VistaDevolver.findViewById(R.id.AñoDeSalida);
        titulo.setText(getItem(position).GetTitulo());
        año.setText(getItem(position).GetAñoSalida());
                
        return VistaDevolver;
    }
}
