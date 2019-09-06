package com.feup.nuno.asteroidsandroid.Utilidades;

import android.media.MediaPlayer;

/**
 * Created by Nuno on 06/06/2016.
 *
 * Classe usada para sons em loop
 */
public class SomEfeitoLoop extends SomEfeito
{
    /**
     * Construtor Base
     * @param volume volume do som
     * @param som som usado
     */
    public SomEfeitoLoop(float volume, MediaPlayer som)
    {
        super(volume, som);
        som.start();
        som.pause();
    }


    @Override
    /**
     * Começa o som
     */
    public void startSom()
    {
        som.pause();
        som.seekTo(0);
        som.setLooping(true);
        som.start();
    }


}
