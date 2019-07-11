package com.example.dai_tp5;
import android.util.Log;

import java.net.URL;
public class Pelicula {
    private int AñoSalida;
    private String Titulo;
    private URL UrlPoster;
    private String ID_Pelicula;

    public void setAñoSalida(int añoSalida) {
        AñoSalida = añoSalida;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setUrlPoster(URL urlPoster) {
        UrlPoster = urlPoster;
    }

    public void setID_Pelicula(String ID_Pelicula) { this.ID_Pelicula = ID_Pelicula;}

    public URL getUrlPoster() {
        return UrlPoster;
    }

    public String getID_Pelicula() {
        return ID_Pelicula;
    }

    public int  GetAñoSalida()
    {
        return AñoSalida;
    }
    public String GetTitulo(){return Titulo;}

    public Pelicula(int añoSalida, String titulo, URL url,String idPelicula) {
        AñoSalida = añoSalida;
        Titulo = titulo;
        UrlPoster=url;
        ID_Pelicula=idPelicula;
        Log.d("constructor","agregar");
    }
}
