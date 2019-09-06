package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Nuno on 01/05/2016.
 *
 * Classe referente a objectos estáticos, como por exemplo botões onde contém métodos que permitem manipular toda a informação referente aos mesmos
 */
public class ObjectoEstatico
{
    protected Bitmap imagem;
    protected PointF coordenadas;
    protected int raio;
    protected boolean pressionado;
    private int pointer_id;


    /**
     * Construtor base
     * @param imagem imagem do objecto
     * @param raio raio do objecto
     */
    public ObjectoEstatico(Bitmap imagem, int raio)
    {
        this.imagem = imagem;
        coordenadas = new PointF(0, 0);
        this.raio = raio;
        pressionado=false;
        pointer_id = -1;
    }

    /**
     * Retorna referencia à imagem
     * @return referencia ao valor
     */
    public Bitmap getImagem()
    {
        return imagem;
    }

    /**
     * Retorna as coordenadas do objecto
     * @return coordenadas do objecto
     */
    public PointF getCoordenadas()
    {
        return coordenadas;
    }

    /**
     * Desenha o objecto com centro nas coordenadas do objecto
     */
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(imagem, coordenadas.x - (imagem.getWidth() / 2), coordenadas.y - (imagem.getHeight() / 2), null);
    }

    /**
     * Substitui as coordenadas
     * @param x coordenada x nova
     * @param y coordenada y nova
     */
    public void setCoordenadas(float x, float y)
    {
        coordenadas.x = x;
        coordenadas.y = y;
    }

    /**
     * Coloca o objecto como a ser pressionado pelo utilizador
     * @param b booleano que dita estar ou nao a ser pressionado
     */
    public void setPressionado(boolean b)
    {
        pressionado = b;
    }

    /**
     * Retorna o valor de pressionada
     * @return valor da variavel
     */
    public boolean getPressionado()
    {
        return pressionado;
    }

    /**
     * Retorna o raio do objecto
     * @return raio do objecto
     */
    public int getRaio()
    {
        return raio;
    }

    /**
     * Cria referencia ao id do pointer
     * @param pointer_id numero do id
     */
    public void setPointer_id(int pointer_id)
    {
        this.pointer_id = pointer_id;
    }

    /**
     * Coloca o pointer_id a -1 para representar que não tem nenhum pointer associado
     */
    public void unsetPointer_id()
    {
        pointer_id = -1;
    }

    /**
     * Retorna o ponter_id
     * @return pointer_id
     */
    public int getPointer_id()
    {
        return pointer_id;
    }

    /**
     * Limpa as variaveis para libertar memória
     */
    public void clean()
    {
        imagem = null;
        coordenadas = null;
    }

}
