package com.example.dai_tp5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.net.URL;

public class MainActivity extends Activity {

    private String IdPeli;
    private URL urlPosterPeliculaElegida;
    private int OpcionSeleccionada=-1;
    static MediaPlayer mediaPlayer;
    static boolean isPlayingAudio=false;
    static Context context;
    public void setUrlPosterPeliculaElegida(URL urlPosterPeliculaElegida) {
        this.urlPosterPeliculaElegida = urlPosterPeliculaElegida;
    }

    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IdPeli="";
        Buscar fragmentbuscar=new Buscar();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentARemplazar,fragmentbuscar);
        fragmentTransaction.commit();
        context=this;
        AlertDialog.Builder mensaje=new AlertDialog.Builder(this);
        mensaje.setTitle("Noches de Arabia en Ingles o Castellano");
        String[] opciones={"Ingles por que son mejores","Castellano que viva la infancia"};
        mensaje.setSingleChoiceItems(opciones,-1,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               OpcionSeleccionada=item;
            }
        }
        );
        mensaje.setPositiveButton("Continuar",Escucha);
        mensaje.setIcon(R.drawable.emoji);
        mensaje.create();
        mensaje.show();
    }
    DialogInterface.OnClickListener Escucha=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int CualSera) {
            if(OpcionSeleccionada==0)
            {
                PlayAudio(context,R.raw.ingles);
            }
            else
            {
                PlayAudio(context,R.raw.castellano);
            }
        }
    };
    public static void PlayAudio(Context c, int id){
        mediaPlayer = MediaPlayer.create(c, id);
        SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,50);
        if (!mediaPlayer.isPlaying())
        {
            isPlayingAudio= true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public String getIdPeli() {
        return IdPeli;
    }
    public void setIdPeli(String idPeli) {
        IdPeli = idPeli;
    }

    public URL getUrlPosterPeliculaElegida() {
        return urlPosterPeliculaElegida;
    }
    public void CambiarAlClick()
    {
        Log.d("CambiarAlClick",""+IdPeli);
        PeliculaEspecial FragmentPeliculaClick=new PeliculaEspecial();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentARemplazar,FragmentPeliculaClick);
        fragmentTransaction.commit();
    }
}
