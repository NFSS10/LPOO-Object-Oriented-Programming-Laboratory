package com.feup.nuno.asteroidsandroid.Utilidades;

import android.media.MediaPlayer;

/**
 * Created by Nuno on 06/06/2016.
 *
 * Classe usada para sons simples
 */
public class SomEfeitoSimples extends SomEfeito
{
    /**
     * Construtor base
     * @param volume volume do som
     * @param som som usado
     */
    public SomEfeitoSimples(float volume, MediaPlayer som)
    {
        super(volume, som);
    }
}
