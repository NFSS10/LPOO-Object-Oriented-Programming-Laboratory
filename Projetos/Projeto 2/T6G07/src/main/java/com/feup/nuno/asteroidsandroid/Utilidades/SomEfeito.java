package com.feup.nuno.asteroidsandroid.Utilidades;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Nuno on 06/06/2016.
 *
 * Classe abstract usada para usar sons
 */
public abstract class SomEfeito
{
    protected float volume;
    protected MediaPlayer som;

    /**
     * Contrutor base da classe
     *
     * @param volume volume do som
     * @param som som usado
     */
    public SomEfeito(float volume, MediaPlayer som)
    {
        this.volume = volume;
        this.som = som;


    }

    /**
     * Começa o som
     */
    public void startSom()
    {
        som.start();
    }

    /**
     * Pausa o som
     */
    public void pauseSom()
    {
        som.pause();
    }


}
