package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;
import android.graphics.Canvas;



/**
 * Created by Nuno on 01/05/2016.
 *
 * Classe abstract que usa o que tem em comum com "ObjectoEstatico" e acrescenta variaveis e métodos para os objectos do jogo que se movimentam
 */
public abstract class ObjectoJogo extends ObjectoEstatico
{
    protected double velocidade;
    protected float angulo;
    protected double angulo_aceleracao;

    /**
     * Construtor base
     * @param imagem imagem do objecto
     * @param raio raio do objecto
     */
    public ObjectoJogo(Bitmap imagem, int raio)
    {
        super(imagem, raio);
        angulo=0;
        angulo_aceleracao=0;
        velocidade=5;
    }

    /**
     * Atualiza o movimento do objecto, com base no angulo e na velocidade
     */
    public void atualizaMovimento()
    {
        //angulo -90 por causa da orientaçao da nave (imagem)
        coordenadas.x = coordenadas.x + (float)( Math.cos(Math.toRadians(angulo_aceleracao-90)) * velocidade);
        coordenadas.y = coordenadas.y + (float)(Math.sin(Math.toRadians(angulo_aceleracao-90)) * velocidade);

    }

    @Override
    /**
     * Override onde também desenha o objecto em angulos diferentes
     */
    public void draw(Canvas canvas)
    {
        canvas.rotate(angulo,coordenadas.x,coordenadas.y);
        canvas.drawBitmap(imagem, coordenadas.x - (imagem.getWidth() / 2), coordenadas.y - (imagem.getHeight() / 2), null);
        canvas.rotate(-angulo,coordenadas.x,coordenadas.y);
    }

    /**
     * Retorna o angulo do objecto no momento
     * @return angulo
     */
    public float getAngulo()
    {
        return angulo;
    }

    /**
     * Retorna a velocidade do objecto no momento
     * @return velocidade
     */
    public double getVelocidade(){return velocidade;}


    @Override
    /**
     * Limpa as variáveis para libertar memória
     */
    public void clean()
    {
        super.clean();
    }
}
