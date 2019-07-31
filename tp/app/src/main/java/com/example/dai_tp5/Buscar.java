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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Buscar extends Fragment implements View.OnClickListener {
    View VistaDevolver;
    Button boton;
    EditText textoedit;
    ArrayList<Pelicula> ListPeliculas;
    AdapterPersonalizado adaptador;
    GridView listViewACargar;
    Context context;
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos)
    {
        Log.d("onCreateView","Entro");
        VistaDevolver=inflador.inflate(R.layout.buscar_pelicula,parent,false);
        boton=VistaDevolver.findViewById(R.id.ButBoton);
        textoedit=VistaDevolver.findViewById(R.id.etIngreso);
        listViewACargar=VistaDevolver.findViewById(R.id.ListaACargar);
        //adaptador.notifyDataSetChanged();
        boton.setOnClickListener(this);
        context=this.getActivity();

        Log.d("onCreateView","Termine");
        listViewACargar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity Principal=(MainActivity)getActivity();
                Log.d("doInBackground",""+ListPeliculas.get(position).getID_Pelicula());
                Principal.setIdPeli(ListPeliculas.get(position).getID_Pelicula());
                Principal.setUrlPosterPeliculaElegida(ListPeliculas.get(position).getUrlPoster());
                Principal.CambiarAlClick();
            }
        });
        return VistaDevolver;

    }

    public void onClick(View v) {
        String busqueda=textoedit.getText().toString();
        ListPeliculas=new ArrayList<>();
        TareaAsincronica miTarea=new TareaAsincronica();
        miTarea.execute(busqueda);
    }
    /*
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("onQueryTextSubmit","entro");
        Log.d("onQueryTextSubmit",query);
        //ListPeliculas.clear();
        Log.d("onQueryTextSubmit",""+ListPeliculas.size());
        TareaAsincronica miTarea=new TareaAsincronica();
        miTarea.execute(query);

        Log.d("onQueryTextSubmit","Iniciado el Async Task");
        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }*/
    private class TareaAsincronica extends AsyncTask<String,Void, Void>
    {

        @Override
        protected Void doInBackground(String... TextosABuscar) {
            try {

                URL rutaURL=new URL("http://www.omdbapi.com/?apikey=611f4378&s="+TextosABuscar[0]);
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
            Log.d("onPostExecute",""+ListPeliculas.size());
            if(ListPeliculas!=null)
            {
                //adaptador.ActualizarLista(ListPeliculas);
                //adaptador=new AdapterPersonalizado(context,ListPeliculas);
                Log.d("onPostExecute","Tengo ahora "+ListPeliculas.size()+" peliculas");
                adaptador=new AdapterPersonalizado(getActivity(),ListPeliculas);
                listViewACargar.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
                Log.d("onPostExecute","Seteado el adaptador");
            }
            else{
                Toast.makeText(getActivity(),"no se han encontrado resultados", Toast.LENGTH_LONG).show();
            }

        }
        private void procesarJSONLeido(InputStreamReader JsonCrudo)
        {
            Log.d("procesarJSONLeido","Entro a procesar el JSON");
            JsonParser parseador;
            parseador=new JsonParser();
            JsonObject objetoJsonCrudito;
            objetoJsonCrudito=parseador.parse(JsonCrudo).getAsJsonObject();
            JsonArray arrSearch;
            arrSearch=objetoJsonCrudito.get("Search").getAsJsonArray();
            Log.d("procesarJSONLeido","Antes del for");
            for (int i = 0; i < arrSearch.size(); i++) {

                JsonObject objetoActual = arrSearch.get(i).getAsJsonObject();
                int AñoSalida = objetoActual.get("Year").getAsInt();
                Log.d("Año",""+AñoSalida);
                String Titulo = objetoActual.get("Title").getAsString();
                String url = objetoActual.get("Poster").getAsString();
                String ID_Pelicula=objetoActual.get("imdbID").getAsString();
                URL UrlPoster;
                try {
                    UrlPoster = new URL(url);
                }catch (Exception e)
                {
                    Log.d("procesarJSONLeido",""+e.getLocalizedMessage());
                    UrlPoster=null;

                }
                Pelicula peli=new Pelicula(AñoSalida,Titulo,UrlPoster,ID_Pelicula);
                ListPeliculas.add(peli);

                Log.d("procesarJSONLeido","se agrego" + peli.GetTitulo());
            }
            Log.d("procesarJSONLeido",""+ListPeliculas.size());
        }
    }

}
