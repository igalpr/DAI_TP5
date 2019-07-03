package com.example.dai_tp5;

public class Pelicula {
    private int AñoSalida;
    private String Titulo;

    public Pelicula(int añoSalida, String titulo) {
        AñoSalida = añoSalida;
        Titulo = titulo;
    }
    public int  GetAñoSalida()
    {
        return AñoSalida;
    }
    public String GetTitulo(){return Titulo;}
}
