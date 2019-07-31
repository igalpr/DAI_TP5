package com.example.dai_tp5;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PeliculaEspecial extends Fragment {

    TextView Titulo,Rated,Detalles,Reparto;
    ImageView Poster;
    View VistaAUsar;
    URL UrlPoster;
    String IDPel;
   String Elenco;
    ArrayAdapter adapterAños;
    Context context=this.getContext();
    PeliculaCompleta InformacionPeliculaCompleta;
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos)
    {
        VistaAUsar=inflador.inflate(R.layout.pantalla_click,parent,false);
        InicializarVariablesYProcesos();
        return VistaAUsar;
    }
    private void InicializarVariablesYProcesos(){
        Titulo=VistaAUsar.findViewById(R.id.TituloPeliculaEspecial);
        Rated=VistaAUsar.findViewById(R.id.Rated);
        Detalles=VistaAUsar.findViewById(R.id.DetallePeliculaEspecial);
        Poster=VistaAUsar.findViewById(R.id.PosterPeliculaEspecial);
        MainActivity Principal=(MainActivity)getActivity();
        Reparto=VistaAUsar.findViewById(R.id.RepartoPeliculaEspecial);
        IDPel=Principal.getIdPeli();
        InformacionPeliculaCompleta=new PeliculaCompleta();
        UrlPoster=Principal.getUrlPosterPeliculaElegida();
        Log.d("InicializarVariablesYProcesos",""+IDPel);
        TareaAsincronica tareaAsincronica=new TareaAsincronica();
        tareaAsincronica.execute(IDPel);

    }
    private class TareaAsincronica extends AsyncTask<String,Void, Void>
    {

        @Override
        protected Void doInBackground(String... IdPelicula) {
            try {
                Log.d("doInBackground",""+IdPelicula);
                URL rutaURL=new URL("http://www.omdbapi.com/?apikey=611f4378&i="+IDPel);
                HttpURLConnection MiConexion=(HttpURLConnection) rutaURL.openConnection();

                Log.d("Conexion", "URL: "+rutaURL);

                if(MiConexion.getResponseCode()==200)
                {
                    Log.d("Conexion", "Exitosa");
                    InputStream cuerpoRespuesta=MiConexion.getInputStream();
                    InputStreamReader lectorRespuesta= new InputStreamReader(cuerpoRespuesta, "UTf-8");
                    procesarJSONLeido(lectorRespuesta);
                    Log.d("Conexion", "Termine de procesar el JSON");
                }
                else
                {
                    Log.d("Conexion", "Error en la conexion");
                }
                MiConexion.disconnect();
            }catch(Exception e)
            {
                Log.d("Conexion", "Error en conexion: "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("procesarJSONLeido",""+InformacionPeliculaCompleta.getTitulo());
            Titulo.setText(InformacionPeliculaCompleta.getTitulo());
            Rated.setText(InformacionPeliculaCompleta.getRated());
            Reparto.setText("Su elenco esta conformado por "+InformacionPeliculaCompleta.getReparto());
            Detalles.setText("Estrenada el "+InformacionPeliculaCompleta.getFechaSalida()+", con una duracion de "+InformacionPeliculaCompleta.getDuracion()+" ,fue dirigida por "+InformacionPeliculaCompleta.getDirector()+" and  "+InformacionPeliculaCompleta.getNominaciones());
            DescargarFoto descargarFoto=new DescargarFoto();
            descargarFoto.execute();

        }

    }
    private void procesarJSONLeido(InputStreamReader JsonCrudo) {
        Log.d("procesarJSONLeido", "Here we go again");
        JsonParser parseador;
        parseador = new JsonParser();
        JsonObject objetoJsonCrudito;
        objetoJsonCrudito = parseador.parse(JsonCrudo).getAsJsonObject();
        Log.d("procesarJSONLeido", ""+objetoJsonCrudito.get("Director").getAsString());
        InformacionPeliculaCompleta.setTitulo(objetoJsonCrudito.get("Title").getAsString());
        InformacionPeliculaCompleta.setRated(objetoJsonCrudito.get("Rated").getAsString());
        InformacionPeliculaCompleta.setFechaSalida(objetoJsonCrudito.get("Released").getAsString());
        InformacionPeliculaCompleta.setDuracion(objetoJsonCrudito.get("Runtime").getAsString());
        InformacionPeliculaCompleta.setDirector(objetoJsonCrudito.get("Director").getAsString());
        InformacionPeliculaCompleta.setNominaciones(objetoJsonCrudito.get("Awards").getAsString());
        InformacionPeliculaCompleta.setReparto(objetoJsonCrudito.get("Actors").getAsString());
        InformacionPeliculaCompleta.setUrlPoster(objetoJsonCrudito.get("Poster").getAsString());


    }
    class DescargarFoto extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... Ruta) {
            Bitmap ImagenConvertida=null;
            try {
                    Log.d("TryCatch","Paso la primera linea");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)UrlPoster.openConnection();
                    Log.d("TryCatch","2");
                    if(httpURLConnection.getResponseCode()==200)
                    {
                        InputStream stream=httpURLConnection.getInputStream();
                        Log.d("TryCatch","3");
                        BufferedInputStream lectorEntrada=new BufferedInputStream(stream);
                        Log.d("TryCatch","4");
                        ImagenConvertida= BitmapFactory.decodeStream(lectorEntrada);
                        Log.d("TryCatch","5");
                        httpURLConnection.disconnect();
                    }
                    else
                    {
                        Log.d("Error","No se logro la coneccion");
                    }




            }catch (Exception error)
            {
                Log.d("Error TryCatch",""+error.getLocalizedMessage());
            }


            return ImagenConvertida;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null)
            {
                Poster.setImageBitmap(bitmap);
            }
            else
            {
                Poster.setImageResource(R.drawable.sinimagen);
            }
        }
    }
}
