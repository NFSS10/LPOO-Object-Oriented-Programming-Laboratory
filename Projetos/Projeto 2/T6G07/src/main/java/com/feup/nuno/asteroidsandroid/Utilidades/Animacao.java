package com.feup.nuno.asteroidsandroid.Utilidades;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by Nuno on 07/06/2016.
 * Classe que fiz para animar o fumo na destuicao do asteroide (já que não encontrei como fazer com sprites)
 */
public class Animacao
{
    private final int FPS = 60;
    private int tempo_animacao;
    private int num_update; //num de updates entre cada imagem
    private PointF coordenadas; //coordenadas onde desenhar
    private int tempo_agr = 0; //contador de updates
    private ArrayList<Bitmap> frames;
    private int frame_agr = -1; //id da frame a desenhar

    private boolean continuar_anim;

    /**
     * Construtor base
     * @param tempo_animacao tempo de duração da animação
     */
    public Animacao(int tempo_animacao, float x, float y)
    {
        this.tempo_animacao = tempo_animacao;
        coordenadas = new PointF(x,y);
        frames = new ArrayList<Bitmap>();
        num_update = -1;
        continuar_anim=false;
    }

    /**
     * Adiciona uma frame ao array de frames
     * @param img imagem a adicionar
     */
    public void adicionarFrame(Bitmap img)
    {
        frames.add(img);
        num_update=(FPS*tempo_animacao)/frames.size();
    }

    /**
     * Gere o ciclo de vida da animação
     */
    public void update()
    {
        if(continuar_anim)
        {
            if (num_update == -1) //Array invalido
                return;


            tempo_agr++;
            if (tempo_agr % num_update == 0)
                frame_agr++;

            if (frame_agr > frames.size()-1)
                frame_agr = frames.size()-1;
        }
    }

    public void draw(Canvas canvas)
    {
        if (frames.isEmpty())
            return;

        canvas.drawBitmap(frames.get(frame_agr),  coordenadas.x - (frames.get(frame_agr).getWidth() / 2), coordenadas.y - (frames.get(frame_agr).getHeight() / 2), null);
    }

    public void resetAnimaco()
    {
        tempo_agr = 0;
        frame_agr = 0;
    }

    /**
     * Verifica se a animação terminou
     * @return true se terminou
     */
    public boolean terminou()
    {
        if(tempo_agr>(FPS*tempo_animacao))
        {
            continuar_anim = false;
            return true;
        }
        return false;
    }

    /**
     * Diz se é para animar ou nao
     * @param b true para animar, nao para não animar
     */
    public void setAnimar(boolean b)
    {
        continuar_anim = b;
    }


    /**
     * Diz se é para animar ou não
     */
    public boolean continuar_Animar()
    {
        return continuar_anim;
    }

    /**
     * Atualiza as coordenadas
     * @param x
     * @param y
     */
    public void setCoordenadas(float x, float y)
    {
        coordenadas.x=x;
        coordenadas.y=y;
    }

    /**
     * Limpa as variaveis para libertar memória
     */
    public void clean()
    {
        frames=null;
        coordenadas=null;
    }

    /**
     * Retorna as frames
     * @return frames
     */
    public ArrayList<Bitmap> getFrames()
    {
        return frames;
    }

}
