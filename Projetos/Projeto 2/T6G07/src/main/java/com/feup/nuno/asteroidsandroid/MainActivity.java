package com.feup.nuno.asteroidsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.feup.nuno.asteroidsandroid.Utilidades.Sons;

public class MainActivity extends AppCompatActivity
{
    private static Button Jogar_btn;
    private static Button Ajuda_btn;
    private static Button Som_btn;
    private static Sons musica;


    @Override
    /**
     * Construtor da actividade
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        controlador_som();

        onClickButtonListener();

    }

    /**
     * Ativa ou desativa o som, tendo em conta o estado que tinha na ultima vez que utilizou a aplicação
     */
    private void controlador_som()
    {
        //Vai buscar o estado da opçao som
        SharedPreferences prefs = getSharedPreferences("Prefs_som", Context.MODE_PRIVATE);
        int som_val = prefs.getInt("som_key", 0);


        musica = new Sons(MediaPlayer.create(this,R.raw.inicio),MediaPlayer.create(this,R.raw.loop));

        //Define a imagem conforme o estado da opçao som
        Som_btn = (Button)findViewById(R.id.Som_btn);
        if(som_val==0)
        {
            Som_btn.setBackgroundResource(R.drawable.som_on);
            musica.startMusica();
        }
        else
        {
            Som_btn.setBackgroundResource(R.drawable.som_off);
            musica.pauseMusica();
        }
    }

    /**
     * Listener dos botoes
     */
    public void onClickButtonListener()
    {
        Jogar_btn = (Button)findViewById(R.id.Jogar_btn);
        Ajuda_btn =  (Button)findViewById(R.id.Ajuda_btn);

        Jogar_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent("com.feup.nuno.asteroidsandroid.JogoView");
                        musica.stopMusica();
                        startActivity(intent);
                        finish();
                    }

                }
        );

        Som_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        SharedPreferences prefs = getSharedPreferences("Prefs_som", Context.MODE_PRIVATE);
                        int som_val = prefs.getInt("som_key", 0); //Vai buscar o valor do estado

                        if(som_val == 1)
                        {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("som_key", 0); //Mete a on
                            editor.commit();
                            Som_btn.setBackgroundResource(R.drawable.som_on);
                               musica.restartMusica();
                        }
                        else
                        {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("som_key", 1); //Mete a off
                            editor.commit();
                            Som_btn.setBackgroundResource(R.drawable.som_off);
                             musica.pauseMusica();
                        }
                    }

                }
        );

        Ajuda_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent("com.feup.nuno.asteroidsandroid.Ajuda_pop_Activity");
                        startActivity(intent);
                    }

                }
        );


    }

    @Override
    protected void onPause()
    {
        musica.pauseMusica();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("Prefs_som", Context.MODE_PRIVATE);
        int som_val = prefs.getInt("som_key",0); //Vai buscar o valor do estado
        if(som_val==0)
            musica.restartMusica();
    }




}
