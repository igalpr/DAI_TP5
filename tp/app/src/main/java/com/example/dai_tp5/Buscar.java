package com.example.dai_tp5;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class Buscar extends Fragment implements SearchView.OnQueryTextListener {
    View VistaDevolver;
    SearchView searchView;
    ArrayList<Pelicula> ListPeliculas=new ArrayList<>();
    AdapterPersonalizado adaptador;
    ListView listViewACargar;
    String QueryElegida="";
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos)
    {
        VistaDevolver=inflador.inflate(R.layout.buscar_pelicula,parent,false);
        searchView=VistaDevolver.findViewById(R.id.SearchBuscar);
        listViewACargar=VistaDevolver.findViewById(R.id.ListaACargar);
        adaptador=new AdapterPersonalizado(this.getActivity(),ListPeliculas);
        listViewACargar.setAdapter(adaptador);
        searchView.setOnQueryTextListener(this);
        return VistaDevolver;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        QueryElegida=query;
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
            }catch(Exception e);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
