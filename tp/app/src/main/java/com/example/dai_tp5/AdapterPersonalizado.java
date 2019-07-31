package com.example.dai_tp5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AdapterPersonalizado extends BaseAdapter {
    private Context context;
    private ArrayList<Pelicula> peliculas;
    ArrayList<Bitmap>Imagenes;

    public AdapterPersonalizado(Context context, ArrayList<Pelicula> peliculas) {
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
        LayoutInflater inflador=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View VistaDevolver= inflador.inflate(R.layout.disenio_grid,parent,false);
        final ImageView imagen=VistaDevolver.findViewById(R.id.PosterPelicula);
        TextView titulo=VistaDevolver.findViewById(R.id.TituloPelicula);
        TextView año=VistaDevolver.findViewById(R.id.AñoDeSalida);
        titulo.setText(getItem(position).GetTitulo());
        año.setText(""+getItem(position).GetAñoSalida());

        //DesCargar(getItem(position).getUrlPoster());
        class DescargarFoto extends AsyncTask<String,Void, Bitmap>
        {
            @Override
            protected Bitmap doInBackground(String... Ruta) {
                Bitmap ImagenConvertida=null;
                try {
                    for (String ruta:Ruta) {
                        URL Rutas=new URL(ruta);
                        HttpURLConnection httpURLConnection=(HttpURLConnection)Rutas.openConnection();
                        if(httpURLConnection.getResponseCode()==200)
                        {
                            InputStream stream=httpURLConnection.getInputStream();
                            BufferedInputStream lectorEntrada=new BufferedInputStream(stream);
                            ImagenConvertida=BitmapFactory.decodeStream(lectorEntrada);
                            httpURLConnection.disconnect();
                        }
                    }

                }catch (Exception error)
                {
                    Log.d("Error","Codigo:"+error.getLocalizedMessage());
                }


                return ImagenConvertida;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);

                if(bitmap!=null)
                {
                    imagen.setImageBitmap(bitmap);
                }
                else
                {
                    imagen.setImageResource(R.drawable.sinimagen);
                }
            }
        }
        DescargarFoto descargarFoto=new DescargarFoto();
        descargarFoto.execute(getItem(position).getUrlPoster().toString());

        return VistaDevolver;
    }
    /*public void DesCargar(URL UrlPoster)
    {
        String UrlImagen=new String();
        UrlImagen=UrlPoster.toString();
        DescargarFoto descargarFoto=new DescargarFoto();
        descargarFoto.execute(UrlImagen);
    }*/
    public void ActualizarLista(ArrayList<Pelicula> listaPeliculas)
    {
        Log.d("Actualizar",""+listaPeliculas.size());
        peliculas.removeAll(peliculas);
        Log.d("Actualizar",""+listaPeliculas.size());
        peliculas.addAll(listaPeliculas);
        Log.d("Actualizar",""+listaPeliculas.size());
    }
    /*private class DescargarFoto extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... Ruta) {
            Bitmap ImagenConvertida=null;
            try {
                for (String ruta:Ruta) {
                    URL Rutas=new URL(ruta);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)Rutas.openConnection();
                    if(httpURLConnection.getResponseCode()==200)
                    {
                        InputStream stream=httpURLConnection.getInputStream();
                        BufferedInputStream lectorEntrada=new BufferedInputStream(stream);
                        ImagenConvertida=BitmapFactory.decodeStream(lectorEntrada);
                        httpURLConnection.disconnect();
                    }
                }

            }catch (Exception error)
            {
                Log.d("Error","Codigo:"+error.getLocalizedMessage());
            }


            return ImagenConvertida;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null)
            {
                imagen.setImageBitmap(bitmap);
            }
            else
            {
                imagen.setImageResource(R.drawable.sinimagen);
            }
        }
    }*/
}
