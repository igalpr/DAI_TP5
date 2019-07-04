package com.example.dai_tp5;
import java.net.URL;
public class Pelicula {
    private int AñoSalida;
    private String Titulo;
    private URL UrlPoster;
    private int ID_Pelicula;

    public void setAñoSalida(int añoSalida) {
        AñoSalida = añoSalida;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setUrlPoster(URL urlPoster) {
        UrlPoster = urlPoster;
    }

    public void setID_Pelicula(int ID_Pelicula) {
        this.ID_Pelicula = ID_Pelicula;
    }

    public URL getUrlPoster() {
        return UrlPoster;
    }

    public int getID_Pelicula() {
        return ID_Pelicula;
    }

    public Pelicula(int añoSalida, String titulo, URL url,int idPelicula) {
        AñoSalida = añoSalida;
        Titulo = titulo;
        UrlPoster=url;
        ID_Pelicula=idPelicula;
    }
    public int  GetAñoSalida()
    {
        return AñoSalida;
    }
    public String GetTitulo(){return Titulo;}
}
