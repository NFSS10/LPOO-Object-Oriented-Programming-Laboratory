package com.feup.nuno.asteroidsandroid.Logica;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;


import com.feup.nuno.asteroidsandroid.Utilidades.Animacao;
import com.feup.nuno.asteroidsandroid.Utilidades.Data;
import com.feup.nuno.asteroidsandroid.Utilidades.Sons;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nuno on 01/05/2016.
 */
public class Jogo
{
    Data data;
    Sons som_jogo;
    boolean musicaon;

    private Nave nave;
    private ArrayList<Bala> balas_vec;
    private ArrayList<Asteroide> asteroides_vec;
    private Vida vida;

    Animacao explosao_anim;

    //Variaveis do Jogo ===================================================
    private int pontuacao;
    private int pontuacao_acum = 0; //Vai acumulando pontos ate chegar ao ponto de aumentar a dificuldade
    private int dificuldade = 0; //Mair dificuldade, maior probabilidade dos asteroides serem mais pequenos

    private int N_MAX_ASTEROIDES = 10; //>0
    private double VEL_MIN_ASTEROIDES = 5; //>0 / 20 max
    private double VEL_MAXIMA_ASTEROIDES = 15; //>0 / 25 ja é muito rapido, 30 maior dificuldade
    private int PROB_PARTIR_EM_PEQUENHOS = 10; //0-100 / quanto maior maior a probabilidade de o asteroide dividir em mais pequenos
    private int PROB_VIDA = 5; //0-100 / quanto maior maior a probabilidade de dropar vida

    private int DELAY_DISPARO = 15; //>0 / quanto maior, menos balas por segundo
    private int disparo_atual = 0;
    //=====================================================================


    private float x = -892; //background largura coord
    private float y = -532;  //background altura coord
    private float mx, my;
    double velocidade_fundo = 0;
    double angulo = 0;
    private final double ATRITO = 0.63;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //=======================================================================================================

    /**
     * Contrutor base do jogo
     * @param dados indo do dispositivo e do jogo (imagens, etc)
     * @param fundo imagem de fundo
     * @param dens densidade do ecra
     * @param lecra largura do ecra
     * @param aecra altura do ecra
     */
    public Jogo(Data dados, Bitmap fundo, int dens, int lecra, int aecra)
    {
        data = dados;
        data.setFundo_IMG(fundo);
        data.setDensidade_ecra(dens);
        data.setAltura_ecra(aecra);
        data.setLargura_ecra(lecra);

        data.calcula_ver_compatibilidade();

        pontuacao = 0;
        balas_vec = new ArrayList<Bala>();
        asteroides_vec = new ArrayList<Asteroide>();
    }

    /**
     * Faz aparecer pacote de vida
     * @param x coordenada x
     * @param y coordenada y
     */
    public void dropVida(float x, float y)
    {
        if (!vida.isAtivo())
        {
            vida.setCoordenadas(x, y);
            vida.setAtivo();
        }
    }

    /**
     * Destroi o asteroide e gere os acontecimentos que sucede a sua destruição
     * @param a asteroide a destruir
     * @param raio_ast raio do asteroide
     * @param ast_x coordenada x
     * @param ast_y coordenada y
     */
    public void destruirAsteroide(Asteroide a, int raio_ast, float ast_x, float ast_y)
    {
        a.setForalimite(true); //Elimina o asteroide
        pontuacao += (50 / (raio_ast - 5)) * a.getVelocidade();
        pontuacao_acum += (50 / (raio_ast - 5)) * a.getVelocidade();
        aumenteDificuldade();
        a.update(VEL_MIN_ASTEROIDES, VEL_MAXIMA_ASTEROIDES, dificuldade, data);

        explosao_anim.setCoordenadas(ast_x,ast_y);
        explosao_anim.resetAnimaco();
        explosao_anim.setAnimar(true);

        Random n_ale = new Random();
        int num;
        //Drop Vida
        num = n_ale.nextInt(100);
        if(num < PROB_VIDA)
            dropVida(ast_x, ast_y);


        //Partir em pedaços caso seja um grande
        if(raio_ast >25 ) //Se é um asteroide grande
        {
            num = n_ale.nextInt(100);
            if (num < PROB_PARTIR_EM_PEQUENHOS)
            {
                adicionarAsteroide(true,ast_x,ast_y);
                adicionarAsteroide(true,ast_x,ast_y);
                adicionarAsteroide(true,ast_x,ast_y);
                adicionarAsteroide(true,ast_x,ast_y);
            }
        }

        if(musicaon)
            som_jogo.getExplosao_som().startSom();

    }

    /**
     * Atinge a nave, coloca a nave invencivel por um tempo e destroi o asteroide
     * @param a asteroide que atinge a nave
     * @return true
     */
    public boolean atingirNave(Asteroide a)
    {
        a.setForalimite(true); //Elimina o asteroide
        a.update(VEL_MIN_ASTEROIDES, VEL_MAXIMA_ASTEROIDES, dificuldade, data);
        nave.setInvencivel();
        return true;
    }

    /**
     * Trata das colisoes dos objectos de jogo
     * Retorna true se algum asteroide atingiu a nave
     */
    public boolean colisoes()
    {
        Bala bala;
        Asteroide a;
        int raio_ast;

        boolean atingiuNave=false;

        for (int i = 0; i < asteroides_vec.size(); i++)
        {
            a = asteroides_vec.get(i);
            raio_ast = a.getRaio();
            float ast_x = a.getCoordenadas().x;
            float ast_y = a.getCoordenadas().y;

            //Ver se é atingido por alguma bala
            for (int j = 0; j < balas_vec.size(); j++)
            {
                bala = balas_vec.get(j);

                if ((bala.getCoordenadas().x < ast_x + raio_ast) && (bala.getCoordenadas().x > ast_x - raio_ast))  //Dentro em x
                    if ((bala.getCoordenadas().y < ast_y + raio_ast) && (bala.getCoordenadas().y > ast_y - raio_ast)) //Demtro em y
                    {
                        destruirAsteroide(a, raio_ast, ast_x, ast_y);
                        balas_vec.remove(j); //Apaga a bala
                        j--;
                    }
            }

            //Verifica se atingiu a nave
            if (!nave.isInvencivel())
            {
                if (((ast_x + raio_ast > nave.getHitbox_X0()) && (ast_x + raio_ast < nave.getHitbox_X1())) || ((ast_x - raio_ast > nave.getHitbox_X0()) && (ast_x - raio_ast < nave.getHitbox_X1())))  //Dentro em x
                    if (((ast_y + raio_ast > nave.getHitbox_Y0()) && (ast_y + raio_ast < nave.getHitbox_Y1())) || ((ast_y - raio_ast > nave.getHitbox_Y0()) && (ast_y - raio_ast < nave.getHitbox_Y1()))) //Dentro em y
                        atingiuNave = atingirNave(a);
            }

            //Verifica se apanhou vida
            if (((vida.getCoordenadas().x + vida.getRaio() > nave.getHitbox_X0()) && (vida.getCoordenadas().x + vida.getRaio() < nave.getHitbox_X1())) || ((vida.getCoordenadas().x + vida.getRaio() > nave.getHitbox_X0()) && (vida.getCoordenadas().x + vida.getRaio() < nave.getHitbox_X1())))  //Dentro em x
                if (((vida.getCoordenadas().y + vida.getRaio() > nave.getHitbox_Y0()) && (vida.getCoordenadas().y + vida.getRaio() < nave.getHitbox_Y1())) || ((vida.getCoordenadas().y + vida.getRaio() > nave.getHitbox_Y0()) && (vida.getCoordenadas().y + vida.getRaio() < nave.getHitbox_Y1()))) //Dentro em y
                {
                    if(vida.isAtivo())
                    {
                        if (nave.getVidas() < nave.getN_MAX_VIDAS())
                            nave.ganharVida();

                        if (musicaon)
                            som_jogo.getApanhar_vida_som().startSom();

                        vida.setInativo();
                    }
                }

        }


        return atingiuNave;
    }

    /**
     * Aumenta a dificuldade, com base a pontuacao, mudando as variaveis do jogo
     */
    public void aumenteDificuldade()
    {

        if (pontuacao < 250) //V_MIN 5 / V_MAX 15 / Numero_Ast 10 / dific 0 ---- Inicial
        {
            if (pontuacao_acum >= 55)
            {
                VEL_MIN_ASTEROIDES += 0.5;
                pontuacao_acum = 0;
            }

            return;
        } else if (pontuacao < 500) //V_MIN 7 / V_MAX 15 / Numero_Ast 10 / dific 0 --- Chega com
        {
            if (pontuacao_acum >= 50)
            {
                VEL_MIN_ASTEROIDES += 0.3;
                pontuacao_acum = 0;
            }

            return;
        } else if (pontuacao < 1000) //V_MIN 10 / V_MAX 20 / Numero_Ast 10 / dific 0
        {
            if (pontuacao_acum >= 200)
            {
                dificuldade += 5;
                pontuacao_acum = 0;
            }
            if (pontuacao > 1500)
                N_MAX_ASTEROIDES++;

            return;
        } else if (pontuacao < 2000)  //V_MIN 10 / V_MAX 20 / Numero_Ast 11 / dific 25
        {
            if (pontuacao_acum >= 150)
            {
                VEL_MIN_ASTEROIDES += 0.5;
                dificuldade += 1.5;
                pontuacao_acum = 0;
            }
            if (pontuacao > 3350)
                N_MAX_ASTEROIDES++;

            return;
        } else if (pontuacao < 3500) //V_MIN 15 / V_MAX 20 / Numero_Ast 12 / dific 40
        {
            if (pontuacao_acum >= 150)
            {
                VEL_MIN_ASTEROIDES += 0.5;
                VEL_MAXIMA_ASTEROIDES += 0.5;
                dificuldade += 2;
                pontuacao_acum = 0;
            }
            if (pontuacao > 3925 && pontuacao < 4100)
                N_MAX_ASTEROIDES++;
            if (pontuacao > 4250 && pontuacao < 4350)
                N_MAX_ASTEROIDES++;
            if (pontuacao > 4650)
                N_MAX_ASTEROIDES++;

            return;
        } else if (pontuacao < 5000) //V_MIN 20(max) / V_MAX 25 / Numero_Ast 15 / dific 60
        {
            if (pontuacao_acum >= 400)
            {
                N_MAX_ASTEROIDES++;
                VEL_MAXIMA_ASTEROIDES += 1;
                dificuldade += 4;
                pontuacao_acum = 0;
            }

            return;
        } else if (pontuacao > 7000) //V_MIN 20(max) / V_MAX 30(max) / Numero_Ast 20 / dific 80
        {
            if (pontuacao_acum >= 200)
            {
                N_MAX_ASTEROIDES++;
                dificuldade += 2;
                pontuacao_acum = 0;
            }

            return;
        } else if (pontuacao > 8000) //V_MIN 20(max) / V_MAX 30(max) / Numero_Ast 30(sempre a subir) / dific 90 (max)
        {
            if (pontuacao_acum >= 300)
            {
                N_MAX_ASTEROIDES += 1;
                pontuacao_acum = 0;
            }

            return;
        }

    }



    /**
     * Coloca pressionado a true se estiver com o dedo no ecra e verifica se carregou nos botões no ecra
     *
     * @param x coordenada x onde esta a tocar no ecra
     * @param y coordenada y onde esta a tocar no ecra
     */
    public void touch(float x, float y, boolean bool, int pointerID)
    {
        //true carregar, false, largar
        if (!bool) //Largar
        {
            largarBotao(pointerID);
            return;
        }

        PointF coord;
        int tam;
        float lx;
        float erro;
        erro = 20 * data.getDensidade_ecra();



        //Ve se é botao de pausa
        coord = data.get_b_Pausa().getCoordenadas();
        lx = coord.x - (data.get_b_Pausa().getImagem().getWidth() / 2);
        float ly = coord.y - (data.get_b_Pausa().getImagem().getHeight() / 2);
        tam = data.get_b_Pausa().getImagem().getWidth();
        if ((x > lx - erro) && (x < lx + tam + erro) && (y<65*data.getDensidade_ecra())&&(y>0))
        {
            largarBotao(pointerID);
            data.get_b_Pausa().setPressionado(true);
            data.get_b_Pausa().setPointer_id(pointerID);
            return;
        }

        //Ve se é botao de açao
        if (y > data.getAltura_ecra() - 60 * data.getDensidade_ecra()) //Se y esta dentro do sitio dos botoes
        {
            coord = data.get_b_rodarEsquerda().getCoordenadas();
            lx = coord.x - (data.get_b_rodarEsquerda().getImagem().getWidth() / 2);
            tam = data.get_b_rodarEsquerda().getImagem().getWidth();
            if ((x > lx - erro) && (x < lx + tam + erro))
            {

                largarBotao(pointerID);
                data.get_b_rodarEsquerda().setPressionado(true);
                data.get_b_rodarEsquerda().setPointer_id(pointerID);
                return;

            }

            coord = data.get_b_rodarDireita().getCoordenadas();
            lx = coord.x - (data.get_b_rodarDireita().getImagem().getWidth() / 2);
            tam = data.get_b_rodarDireita().getImagem().getWidth();
            if ((x > lx - erro) && (x < lx + tam + erro))
            {

                largarBotao(pointerID);
                data.get_b_rodarDireita().setPressionado(true);
                data.get_b_rodarDireita().setPointer_id(pointerID);
                return;
            }

            coord = data.get_b_Acelerar().getCoordenadas();
            lx = coord.x - (data.get_b_Acelerar().getImagem().getWidth() / 2);
            tam = data.get_b_Acelerar().getImagem().getWidth();
            if ((x > lx - erro) && (x < lx + tam + erro))
            {
                largarBotao(pointerID);
                data.get_b_Acelerar().setPressionado(true);
                data.get_b_Acelerar().setPointer_id(pointerID);
                if(musicaon)
                    som_jogo.getAcelerar_som().startSom();
                return;

            }

            coord = data.get_b_Disparar().getCoordenadas();
            lx = coord.x - (data.get_b_Disparar().getImagem().getWidth() / 2);
            tam = data.get_b_Disparar().getImagem().getWidth();
            if ((x > lx - erro) && (x < lx + tam + erro))
            {

                largarBotao(pointerID);
                data.get_b_Disparar().setPressionado(true);
                data.get_b_Disparar().setPointer_id(pointerID);
                return;

            }
            //Se chegou aqui nao era nenhum botao, logo larga
            largarBotao(pointerID);
        }
        else //Nao no y dos botoes
            largarBotao(pointerID);
    }

    /**
     * Muda o estado dos botoes
     * @param pointerID id do pointer referente ao touch
     */
    public void largarBotao(int pointerID)
    {
        if (pointerID == data.get_b_Acelerar().getPointer_id())
        {
            data.get_b_Acelerar().setPressionado(false);
            data.get_b_Acelerar().unsetPointer_id();
            if(musicaon)
                som_jogo.getAcelerar_som().pauseSom();
        } else if (pointerID == data.get_b_Disparar().getPointer_id())
        {
            data.get_b_Disparar().setPressionado(false);
            data.get_b_Disparar().unsetPointer_id();
        } else if (pointerID == data.get_b_rodarDireita().getPointer_id())
        {
            data.get_b_rodarDireita().setPressionado(false);
            data.get_b_rodarDireita().unsetPointer_id();
        } else if (pointerID == data.get_b_rodarEsquerda().getPointer_id())
        {
            data.get_b_rodarEsquerda().setPressionado(false);
            data.get_b_rodarEsquerda().unsetPointer_id();
        }
        else if (pointerID == data.get_b_Pausa().getPointer_id())
        {
            data.get_b_Pausa().setPressionado(false);
            data.get_b_Pausa().unsetPointer_id();
        }
    }


    /**
     * Dispara uma bala
     * @param angulo angulo que diz a direção da bala
     */
    public void disparar(float angulo)
    {
        Bala bala = new Bala(data.getBala_IMG(), 2, angulo, nave.getCoordenadas().x, nave.getCoordenadas().y);
        balas_vec.add(bala);
        disparo_atual = DELAY_DISPARO;
        if(musicaon)
            som_jogo.getDisparar_som().startSom();
    }

    /**
     * Adiciona um asteroide
     * @param pequeno_destr diz se é um destroço
     * @param x coordenada x onde o destroço ira surgir
     * @param y coordenada y onde o destroço ira surgir
     */
    public void adicionarAsteroide(boolean pequeno_destr, float x,float y)
    {
        //"boolean pequeno": para forçar adicionar pequeno caso true
        Asteroide ast;
        Random n_ale = new Random();

        int n;
        n = n_ale.nextInt() % (100 - dificuldade); //Dificuldade aumenta, maior probabilidade de ser pequeno
        if (n < 0)
            n = -n;


        Bitmap img;
        img = data.getAsteroide_G1_IMG();
        int raio = 55;
        double vel = VEL_MIN_ASTEROIDES;
        if(pequeno_destr)
            n=1;
        if (n < 5) //gerar pequeno
        {
            raio = 30;
            n = n_ale.nextInt() % 3;
            if (n < 0)
                n = -n;
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
            n = n_ale.nextInt() % 4;
            if (n < 0)
                n = -n;
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
        vel_dif = (int) (VEL_MAXIMA_ASTEROIDES+0.5 - VEL_MIN_ASTEROIDES);
        if(vel_dif<1)
            vel=VEL_MIN_ASTEROIDES;
        else
        {
            vel = VEL_MIN_ASTEROIDES + n_ale.nextInt(vel_dif);
        }

        ast = new Asteroide(img, raio, vel, pequeno_destr, x, y);
        asteroides_vec.add(ast);
    }

    /**
     * Gere o ciclo do jogo assim como as ações referentes ao mesmo
     * @return true se perdeu
     */
    public boolean update()
    {
        if (musicaon)
            som_jogo.updateMusica();

        //Asteroides
        if (asteroides_vec.size() < N_MAX_ASTEROIDES)
            adicionarAsteroide(false,0,0);
        Asteroide a;
        for (int i = 0; i < asteroides_vec.size(); i++)
        {
            a = asteroides_vec.get(i);
            a.update(VEL_MIN_ASTEROIDES, VEL_MAXIMA_ASTEROIDES, dificuldade, data);
        }

        //Acoes referentes a nave
        nave.update();
        moveFundo(nave.getCoordenadas().x, nave.getCoordenadas().y);

        //Bala
        Bala bala;
        for (int i = 0; i < balas_vec.size(); i++)
        {
            bala = balas_vec.get(i);
            bala.update();

            if (bala.getForalimite())
            {
                balas_vec.remove(i);
                i--;
            }
        }

        //Vida
        vida.update();


        if (colisoes())
            nave.perderVida();


        explosao_anim.update();


        //Acoes botoes
        if (data.get_b_rodarDireita().getPressionado())
            nave.rodarDireita();

        if (data.get_b_rodarEsquerda().getPressionado())
            nave.rodarEsquerda();

        if (data.get_b_Disparar().getPressionado())
        {
            if (disparo_atual == 0)
                disparar(nave.getAngulo());
        }
        disparo_atual--;
        if (disparo_atual < 0)
            disparo_atual = 0;

        if (data.get_b_Acelerar().getPressionado())
        {
            nave.acelerar();
            velocidade_fundo = nave.getVelocidade();
            angulo = nave.getAngulo();
        } else
            nave.parar_acelerarar_img();


        return nave.perdeu();
    }


    /**
     * Inicia os botoes do jogo, musica e os outros objectos do jogo
     */
    public void iniciar(Sons musica, boolean musica_status)
    {

        data.iniciarObjectosEstaticos();


        //Inicia a nave
        nave = new Nave(data.getNave_IMG(), data.getNave_acel_IMG(),data.getNave_inv_IMG(),data.getNave_acel_inv_IMG(), 0);
        nave.setCoordenadas(data.getLargura_ecra() / 2, data.getAltura_ecra() / 2);

        vida = new Vida(data.getVida_IMG(), 25);


        som_jogo = musica;
        musicaon = musica_status;




        explosao_anim = new Animacao(1, 0, 0);
        for (int i = 0; i < data.getExplosao_frames().size(); i++)
            explosao_anim.adicionarFrame(data.getExplosao_frames().get(i));
        explosao_anim.setAnimar(false);


        for (int j=0;j<N_MAX_ASTEROIDES;j++)
        {

        }

    }


    /**
     * Coloca a musica como ativada/desativade
     * @param b true ativa, caso seja false desativa os sons
     */
    public void setMusicaON(boolean b)
    {
        musicaon = b;
    }

    /**
     * Retorna a pontuação do jogo
     * @return pontos
     */
    public int getPontuacao()
    {
        return pontuacao;
    }


    /**
     * Mexe a camera do jogo e mantem a nave no centro ate ficar perto do limite do mapa
     */
    public void moveFundo(float nX, float nY)
    {
        float posX, posY;
        posX = nX + data.getErro_nave_x();
        posY = nY + data.getErro_nave_y();

        if (posX > 50 && posX < 1650 + data.getErroX())
            mx = mx + (float) (Math.cos(Math.toRadians(angulo - 90)) * velocidade_fundo);
        if (posY > 50 && posY < 1650 + data.getErroY())
            my = my + (float) (Math.sin(Math.toRadians(angulo - 90)) * velocidade_fundo);

        if (velocidade_fundo > 0)
            velocidade_fundo = velocidade_fundo - ATRITO;
        if (velocidade_fundo < 0)
            velocidade_fundo = 0;
    }

    /**
     * Desenha a frame do jogo
     * @param canvas canvas onde desenha
     */
    public void draw(Canvas canvas)
    {
        canvas.translate(-mx, -my);
        //______Desenho Objectos Jogo______________________
        //Fundo
        canvas.drawBitmap(data.getFundo_IMG(), x, y, null);

        //Balas
        for (int i = 0; i < balas_vec.size(); i++)
            balas_vec.get(i).draw(canvas);

        //Vida
        vida.draw(canvas);

        //Nave
        nave.draw(canvas);

        //Asteroides
        for (int i = 0; i < asteroides_vec.size(); i++)
            asteroides_vec.get(i).draw(canvas);

        if(explosao_anim.continuar_Animar())
            explosao_anim.draw(canvas);
        //_________________________________________________________________

        canvas.translate(+mx, +my);

        if (nave.getVidas() < 3)
            canvas.drawBitmap(data.getCrack1_IMG(), 0, 0, null);
        if (nave.getVidas() < 2)
            canvas.drawBitmap(data.getCrack2_IMG(), data.getLargura_ecra() - 140 * data.getDensidade_ecra(), 0, null);


        //Botoes
        data.get_b_rodarEsquerda().draw(canvas);
        data.get_b_rodarDireita().draw(canvas);
        data.get_b_Disparar().draw(canvas);
        data.get_b_Acelerar().draw(canvas);
        data.get_b_Pausa().draw(canvas);


        //Score (canto superior esquerdo)
        paint.setColor(Color.WHITE);
        paint.setTextSize(15 * data.getDensidade_ecra());
        canvas.drawText("Score: " + pontuacao, 10 * data.getDensidade_ecra(), 15 * data.getDensidade_ecra(), paint);

    }

    /**
     * Verifica se o utilizador carregou no botao pausa
     * @return true se carregou pausa
     */
    public boolean isPaused()
    {
        if(data.get_b_Pausa().getPressionado())
        {
            data.get_b_Pausa().setPressionado(false);
            return true;
        }
        return false;
    }

    /**
     * Limpa as variaveis, para libertar memória
     */
    public void clean()
    {
        nave.clean();
        nave=null;

        balas_vec=null;
        asteroides_vec=null;

        vida.clean();
        vida=null;

        explosao_anim.clean();
        explosao_anim=null;

        paint = null;
    }




}
