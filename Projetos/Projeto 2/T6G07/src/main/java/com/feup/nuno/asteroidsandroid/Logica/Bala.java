package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;

/**
 * Created by Nuno on 10/05/2016.
 *
 * Classe referente ás balas que são usadas para destruir os asteroides
 */
public class Bala extends ObjectoJogo
{
    private final float VELOCIDADE = 40; //Constante
    private boolean foraLimite=false;

    /**
     * Construtor base
     * @param imagem imagem da bala
     * @param raio raio da bala
     * @param angulo_dir angulo de direçao da bala, para o movimento
     * @param navex coordenada x
     * @param navey coordenada y
     */
    public Bala(Bitmap imagem, int raio, float angulo_dir, float navex, float navey)
    {
        super(imagem, raio);
        velocidade=VELOCIDADE;
        angulo=angulo_dir;
        angulo_aceleracao=angulo_dir;
        coordenadas.set(navex,navey);
    }

    /**
     * Gere o ciclo de vida da bala
     */
    public void update()
    {
        atualizaMovimento();
        if(coordenadas.x > 2650 || coordenadas.y > 2300 || coordenadas.x < -1000 || coordenadas.y < -600)
            foraLimite = true;
    }

    /**
     * Retorna o estado da bala referente a estar dentro dos limites
     * @return true se esta fora, false se esta dentro dos limites do jogo
     */
   public boolean getForalimite()
   {
       return foraLimite;
   }

}
