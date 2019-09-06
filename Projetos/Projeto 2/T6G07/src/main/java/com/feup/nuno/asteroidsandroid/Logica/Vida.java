package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Nuno on 06/06/2016.
 *
 * Classe referente ao objecto vida que é usado para reparar a nave
 */
public class Vida extends ObjectoJogo
{
    private final int DURACAO = 600;
    private int ativo_duracao;
    private boolean ativo;

    /**
     * Construtor base
     * @param imagem imagem do objecto
     * @param raio raio do objecto
     */
    public Vida(Bitmap imagem, int raio)
    {
        super(imagem, raio);
        coordenadas.x=-2000;
        coordenadas.y=-2000;
        setInativo();
    }

    @Override
    /**
     * Override, desenha apenas se estiver ativo
     */
    public void draw(Canvas canvas)
    {
        if(ativo)
            super.draw(canvas);
    }

    /**
     * Gere o ciclo de vida do objecto
     */
    public void update()
    {
        if(isAtivo())
            ativo_duracao--;
    }

    /**
     * Verifica se esta ativo, ou seja, se é possível apanhar a vida.
     * @return true se esta ativo, false se nao estao
     */
    public boolean isAtivo()
    {
        if(ativo_duracao<1)
        {
            ativo_duracao=0;
            ativo=false;
            return false;
        }
        return true;
    }

    /**
     * Coloca como ativo
     */
    public void setAtivo()
    {
        ativo_duracao=DURACAO;
        ativo=true;
    }

    /**
     * Coloca como inativo
     */
    public void setInativo()
    {
        ativo_duracao=0;
        ativo=false;
    }



}
