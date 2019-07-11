package com.example.dai_tp5;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Buscar extends Fragment implements SearchView.OnQueryTextListener {
    View VistaDevolver;
    SearchView searchView;
    ArrayList<Pelicula> ListPeliculas=new ArrayList<>();
    AdapterPersonalizado adaptador;
    ListView listViewACargar;
    String QueryElegida="";
    Context context;
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos)
    {
        VistaDevolver=inflador.inflate(R.layout.buscar_pelicula,parent,false);
        searchView=VistaDevolver.findViewById(R.id.SearchBuscar);
        listViewACargar=VistaDevolver.findViewById(R.id.ListaACargar);
        adaptador=new AdapterPersonalizado(this.getActivity(),ListPeliculas);
        searchView.setOnQueryTextListener(this);
        context=this.getActivity();
        return VistaDevolver;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        QueryElegida=query;
        TareaAsincronica miTarea=new TareaAsincronica();
        miTarea.execute();

        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
    private class TareaAsincronica extends AsyncTask<Void,Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                 URL rutaURL=new URL("http://www.omdbapi.com/?apikey=611f4378&s="+QueryElegida);
                HttpURLConnection MiConexion=(HttpURLConnection) rutaURL.openConnection();

                if(MiConexion.getResponseCode()==200)
                {
                    Log.d("Conexion", "Exitosa");
                    InputStream cuerpoRespuesta=MiConexion.getInputStream();
                    InputStreamReader lectorRespuesta= new InputStreamReader(cuerpoRespuesta, "UTf-8");
                    procesarJSONLeido(lectorRespuesta);
                }
                else
                {
                    Log.d("Conexion", "Error en la conexion");
                }
                MiConexion.disconnect();
            }catch(Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("onPostExecute",""+ListPeliculas.size());
            //adaptador.ActualizarLista(ListPeliculas);
            adaptador=new AdapterPersonalizado(context,ListPeliculas);
            Log.d("onPostExecute",""+adaptador.getCount());
            listViewACargar.setAdapter(adaptador);

        }
        private void procesarJSONLeido(InputStreamReader JsonCrudo)
        {
            JsonParser parseador;
            parseador=new JsonParser();
            JsonObject objetoJsonCrudito;
            objetoJsonCrudito=parseador.parse(JsonCrudo).getAsJsonObject();
            JsonArray arrSearch;
            arrSearch=objetoJsonCrudito.get("Search").getAsJsonArray();
            for (JsonElement jsonActual:arrSearch) {

                JsonObject objetoActual = jsonActual.getAsJsonObject();
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

                Log.d("procesarJSONLeido","se agrego");
            }
            Log.d("procesarJSONLeido",""+ListPeliculas.size());
        }
    }
}
