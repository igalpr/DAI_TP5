package com.example.dai_tp5;


import java.util.ArrayList;

public class PeliculaCompleta {
    private String Titulo;

    public void setUrlPoster(String urlPoster) {
        UrlPoster = urlPoster;
    }

    public String getUrlPoster() {
        return UrlPoster;
    }

    private String UrlPoster;
    private String ID_Pelicula;
    private String FechaSalida;
    private String Director;
    private String Nominaciones;
    private String Reparto;
    private String Duracion;
    private String Rated;
    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String duracion) {
        Duracion = duracion;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public PeliculaCompleta() {
        Titulo = "";
        UrlPoster = "";
        this.ID_Pelicula = "";
        FechaSalida = "";
        Director = "";
        Nominaciones = "";
        Reparto = null;
        Duracion="";
        Rated="";
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }



    public String getID_Pelicula() {
        return ID_Pelicula;
    }

    public void setID_Pelicula(String ID_Pelicula) {
        this.ID_Pelicula = ID_Pelicula;
    }

    public String getFechaSalida() {
        return FechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        FechaSalida = fechaSalida;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getNominaciones() {
        return Nominaciones;
    }

    public void setNominaciones(String nominaciones) {
        Nominaciones = nominaciones;
    }

    public String getReparto() {
        return Reparto;
    }

    public void setReparto(String reparto) {
        Reparto = reparto;
    }
}
