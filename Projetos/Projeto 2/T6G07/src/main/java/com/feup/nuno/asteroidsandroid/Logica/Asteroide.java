package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;

import com.feup.nuno.asteroidsandroid.Utilidades.Data;

import java.util.PropertyPermission;
import java.util.Random;

/**
 * Created by Nuno on 01/05/2016.
 *
 * Classe referente aos asteroides que são objectos nos quais o utilizador tem de destruir e não deixar ser atingido pelos mesmos
 */
public class Asteroide extends ObjectoJogo
{
    private boolean foraLimite=false;
    private double vel_minima;
    private double vel_max;
    private int dificuldade;


    /**
     * Construtor base
     * @param imagem imagem do asteroide
     * @param raio raio do asteroide
     * @param vel velocidade do asteroide
     * @param pequeno_destr se true, é o resultado dos destroços
     * @param destr_x posição x do destroço
     * @param destr_y posição y do destroço
     */
    public Asteroide(Bitmap imagem, int raio, double vel, boolean pequeno_destr, float destr_x, float destr_y)
    {
        super(imagem, raio);
        velocidade =vel;
        aleatoriamente_setCoord(pequeno_destr, destr_x, destr_y);
    }

    /**
     * Posiciona aleatoriamente fora do mapa e coloca o asteroide numa direçao aleatoria para dentro do mapa.
     * Caso pequeno_destr == true, da apenas um angulo novo e coloca-o na mesma posiçao do asteroide destruido
     *
     * @param pequeno_destr se true é um destroço
     * @param destr_x coordenada x do destroço
     * @param destr_y coordenada y do destroço
     */
    void aleatoriamente_setCoord(boolean pequeno_destr,float destr_x, float destr_y)
    {
        Random n_ale_ger = new Random();
        if(pequeno_destr) //Se o resto de um asteroide grande
        {
            int n_ale;
            n_ale = n_ale_ger.nextInt(360);
            coordenadas.x = destr_x;
            coordenadas.y = destr_y;
            angulo = n_ale;
            angulo_aceleracao = n_ale;
            raio = 25;
            return;
        }

        //Coordenadas
        int lim_ale;
        float x=-50, y=-50;

        lim_ale = n_ale_ger.nextInt() % 4;
        if(lim_ale<0)
            lim_ale=-lim_ale;

        switch (lim_ale)
        {
            case 0: //cima
                x = (float)n_ale_ger.nextInt() % 2600;
                while(x<-900)
                    x = (float)n_ale_ger.nextInt() % 2600;
                y = -550;
                break;
            case 1: //baixo
                x = (float)n_ale_ger.nextInt() % 2600;
                while(x<-900)
                    x = (float)n_ale_ger.nextInt() % 2600;
                y = 2245;
                break;
            case 2: //esquerda
                y = (float)n_ale_ger.nextInt() % 2245;
                while(y<-550)
                    y = (float)n_ale_ger.nextInt() % 2245;
                x = -900;
                break;
            case 3: //direita
                y = (float)n_ale_ger.nextInt() % 2245;
                while(y<-550)
                    y = (float)n_ale_ger.nextInt() % 2245;
                x = 2600;
                break;
            default:
                break;
        }
        coordenadas.x = x;
        coordenadas.y = y;


        //Angulo
        float n_ale;
        n_ale = n_ale_ger.nextInt() % 181;

        if (x < -400)
        {
            angulo = n_ale;
            angulo_aceleracao = n_ale;
            return;
        }
        if (x > 1785)
            n_ale = n_ale + 180;
        else if (y < -250)
            n_ale = n_ale + 90;
        else if (y > 1065)
            n_ale = n_ale + 270;

        angulo = n_ale;
        angulo_aceleracao = n_ale;

        //Velocidades
        int vel_dif;
        vel_dif = (int) (vel_max+0.5 - vel_minima);
        if(vel_dif<1)
            velocidade=vel_minima;
        else
        {
            velocidade = vel_minima + n_ale_ger.nextInt(vel_dif);
        }


    }

    /**
     * Modifica aleatoriamente o asteroide
     * @param data contem imagens a usar pelo jogo
     */
    void modificarAsteroide(Data data)
    {
        Asteroide ast;
        Random n_ale = new Random();

        int n;
        n=n_ale.nextInt()% (100-dificuldade); //Dificuldade aumenta, maior probabilidade de ser pequeno
        if(n<0)
            n=-n;


        Bitmap img;
        img = data.getAsteroide_G1_IMG();
        int raio=55;
        double vel=vel_minima;
        if(n<5) //gerar pequeno
        {
            raio = 30;
            n=n_ale.nextInt()%3;
            if(n<0)
                n=-n;
            switch (n)
            {
                case 0:
                    img = data.getAsteroide_P1_IMG();
                    break;
                case 1:
                    img = data.getAsteroide_P2_IMG();
                    break;
                case 2:
                    img = data.getAsteroide_P3_IMG();
                    break;
                default:
                    break;
            }
        }
        else //gerar grande
        {
            raio = 55;
            n=n_ale.nextInt()%4;
            if(n<0)
                n=-n;
            switch (n)
            {
                case 0:
                    img = data.getAsteroide_G1_IMG();
                    break;
                case 1:
                    img = data.getAsteroide_G2_IMG();
                    break;
                case 2:
                    img = data.getAsteroide_G3_IMG();
                    break;
                case 3:
                    img = data.getAsteroide_G4_IMG();
                    break;
                default:
                    break;
            }
        }

        //Velocidades
        int vel_dif;
        vel_dif = (int) (vel_max+0.5 - vel_minima);
        if(vel_dif<1)
            vel=vel_minima;
        else
        {
            vel = vel_minima + n_ale.nextInt(vel_dif);
        }
        ast = new Asteroide(img,raio,vel,false,0,0);

    }

    /**
     * Update do ciclo de vida do asteroide e atualza as variaveis da dificuldade
     * @param vMin velocidade minima da dificuldade
     * @param vMax velocidade maxima da dificuldade
     * @param dif variavel dificuldade
     * @param dados contem dados do jogo (imagens) e dispositivo
     */
    public void update(double vMin, double vMax, int dif, Data dados)
    {
        vel_minima = vMin;
        vel_max = vMax;
        dificuldade = dif;

        if(velocidade==0) //Garante a velocidade minima
            velocidade=vel_minima;

        if(foraLimite)
        {
            aleatoriamente_setCoord(false,0,0);
            modificarAsteroide(dados);
            foraLimite= false;
        }

        atualizaMovimento();
        if(coordenadas.x > 2650 || coordenadas.y > 2300 || coordenadas.x < -1000 || coordenadas.y < -600)
            foraLimite = true;
    }

    /**
     * Diz se esta fora do limite do mapa
     * @return true se esta fora de limite, false caso contrario
     */
    public boolean getForalimite()
    {
        return foraLimite;
    }

    /**
     * Coloca o asteroide como estar ou não fora do limite
     * @param bool booleano referente a estar ou nao fora do limite
     */
    public void setForalimite(boolean bool)
    {
        foraLimite=bool;
    }





}
