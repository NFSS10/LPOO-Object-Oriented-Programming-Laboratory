package com.feup.nuno.asteroidsandroid.Utilidades;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Nuno on 04/06/2016.
 *
 * Classe que contem todos os sons do jogo
 */
public class Sons
{
    private float VOLUME_MUSICA = 0.4f;
    private float VOLUME_SONS = 1f;

    //Musica
    private MediaPlayer musica_inicial;
    private MediaPlayer musica_loop;
    private boolean musica_inicial_acabou;


    //Sons
    private SomEfeito acelerar_som;
    private SomEfeito apanhar_vida_som;
    private SomEfeito disparar_som;
    private SomEfeito explosao_som;

    /**
     * Contrutor base
     * @param m_i musica usada no inicio antes de começar o loop
     * @param m_l musica do loop
     */
    public Sons(MediaPlayer m_i, MediaPlayer m_l)
    {
        musica_inicial = m_i;
        try {
            musica_inicial.prepare();
        } catch (IOException e) {

        }
        catch (IllegalStateException e) {

        }
        musica_inicial.setOnCompletionListener((new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                updateMusica();
            }
        }
        ));

        musica_inicial.start();
        musica_inicial.setVolume(VOLUME_MUSICA,VOLUME_MUSICA);
        musica_inicial.pause();
        musica_inicial_acabou=false;

        musica_loop = m_l;
        try {
            musica_loop.prepare();
        } catch (IOException e) {

        }
        catch (IllegalStateException e) {

        }
        musica_loop.start();
        musica_loop.setVolume(VOLUME_MUSICA,VOLUME_MUSICA);
        musica_loop.pause();
    }


    /**
     * Começa a musica
     */
    public void startMusica()
    {
        if(!musica_inicial_acabou)
        {
        musica_inicial.start();
            return;
        }
        else
        {
            musica_inicial_acabou = true;
            musica_loop.setLooping(true);
            musica_loop.start();
        }
    }

    /**
     * Verifica o estado da musica, (usada para passar da musica inicial para o loop)
     */
    public void updateMusica()
    {
        if(musica_inicial.isPlaying())
            return;
        else
        {
            musica_inicial_acabou = true;
            musica_loop.setLooping(true);
            musica_loop.start();
        }

    }

    /**
     * Pausa a musica
     */
    public void pauseMusica()
    {
        musica_inicial.pause();
        musica_loop.pause();
    }

    /**
     * Resume a musica
     */
    public void resumeMusica()
    {
        if(!musica_inicial_acabou)
        {
            musica_inicial.start();
            return;
        }
        else
        {
            musica_loop.setLooping(true);
            musica_loop.start();
        }

    }

    /**
     * Começa de novo a musica
     */
    public void restartMusica()
    {
        pauseMusica();
        musica_inicial.seekTo(0);
        musica_inicial_acabou = false;
        musica_inicial.start();
    }

    /**
     * Para a musica, usada para deixar de tocar a musica
     */
    public void stopMusica()
    {
        musica_inicial.stop();
        musica_loop.stop();
    }

    /**
     * Modifica o volume da musica
     * @param vol novo volume da musica
     */
    public void setVOLUME_MUSICA(float vol)
    {
        VOLUME_MUSICA =vol;
        musica_inicial.setVolume(VOLUME_MUSICA,VOLUME_MUSICA);
        musica_loop.setVolume(VOLUME_MUSICA,VOLUME_MUSICA);
    }

    /**
     * Adiciona a referencia à variavel pretendida
     * @param som referencia do som
     */
    public void setAcelerar_som(MediaPlayer som)
    {
        acelerar_som=new SomEfeitoLoop(VOLUME_SONS, som);
    }
    /**
     * Adiciona a referencia à variavel pretendida
     * @param som referencia do som
     */
    public void setApanhar_vida_som(MediaPlayer som)
    {
        apanhar_vida_som=new SomEfeitoSimples(VOLUME_SONS, som);
    }
    /**
     * Adiciona a referencia à variavel pretendida
     * @param som referencia do som
     */
    public void setDisparar_som(MediaPlayer som)
    {
        disparar_som=new SomEfeitoSimples(VOLUME_SONS, som);
    }
    /**
     * Adiciona a referencia à variavel pretendida
     * @param som referencia do som
     */
    public void setExplosao_som(MediaPlayer som)
    {
        explosao_som=new SomEfeitoSimples(VOLUME_SONS, som);
    }

    /**
     * Retorna a referencia pretendida
     * @return referencia ao som pretendido
     */
    public SomEfeito getAcelerar_som()
    {
        return acelerar_som;
    }
    /**
     * Retorna a referencia pretendida
     * @return referencia ao som pretendido
     */
    public SomEfeito getDisparar_som()
    {
        return disparar_som;
    }
    /**
     * Retorna a referencia pretendida
     * @return referencia ao som pretendido
     */
    public SomEfeito getApanhar_vida_som()
    {
        return apanhar_vida_som;
    }
    /**
     * Retorna a referencia pretendida
     * @return referencia ao som pretendido
     */
    public SomEfeito getExplosao_som()
    {
        return explosao_som;
    }

    /**
     * Limpa as variaveis para libertar memoria
     */
    public void clean()
    {
        musica_inicial.release();
        musica_inicial=null;
        musica_loop.release();
        musica_loop=null;


        acelerar_som=null;
        apanhar_vida_som=null;
        disparar_som=null;
        explosao_som=null;
    }


}
