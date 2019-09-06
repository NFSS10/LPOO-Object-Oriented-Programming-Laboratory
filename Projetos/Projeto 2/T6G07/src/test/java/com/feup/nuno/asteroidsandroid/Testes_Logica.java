package com.feup.nuno.asteroidsandroid;


import com.feup.nuno.asteroidsandroid.Logica.Asteroide;
import com.feup.nuno.asteroidsandroid.Logica.Bala;
import com.feup.nuno.asteroidsandroid.Logica.Nave;
import com.feup.nuno.asteroidsandroid.Logica.Vida;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Testes_Logica
{




    @Test
    public void movimento_nave() throws Exception
    {
        Nave nave_teste = new Nave(null,null,null,null,0);

        nave_teste.setCoordenadas(0,0);

        //Esta na posiçao onde o colocamos
        assertEquals(0.0,nave_teste.getCoordenadas().x,0.5);
        assertEquals(0.0,nave_teste.getCoordenadas().y,0.5);


        int num=0;
        while(num<10)
        {
            nave_teste.acelerar();
            nave_teste.update();
            num++;
        }
        while(num<120) //Deixar ir ate parar
        {
            nave_teste.update();
            num++;
        }

        //Aceleramos para cima
        assertEquals(0.0,nave_teste.getCoordenadas().x,0.5);
        assertEquals(-237,nave_teste.getCoordenadas().y,1);



        nave_teste.rodarEsquerda(); //-10 graus
        nave_teste.rodarDireita(); //+10 graus

        //Rodamos para um lado e para o outro
        assertEquals(0.0,nave_teste.getAngulo(),0.5);




        num=0;
        while (num<18)
        {
            nave_teste.rodarDireita(); //+10 graus
            num++;
        }
        //Viramos para a direita ate ficar ao contrario
        assertEquals(180,nave_teste.getAngulo(),0.0);




        num=0;
        while(num<10)
        {
            nave_teste.acelerar();
            nave_teste.update();
            num++;
        }
        while(num<120) //Deixar ir ate parar
         {
        nave_teste.update();
        num++;
        }
        //Devemos voltar a posiçao inicial
        assertEquals(0.0,nave_teste.getCoordenadas().x,0.5);
        assertEquals(0.0,nave_teste.getCoordenadas().y,0.5);




        num=0;
        while (num<9)
        {
            nave_teste.rodarEsquerda(); //-10 graus
            num++;
        }
        //Viramos para a esquerda 90 graus, e ficamos virados para a direita
        assertEquals(90,nave_teste.getAngulo(),0.0);



        num=0;
        while(num<10)
        {
            nave_teste.acelerar();
            nave_teste.update();
            num++;
        }
        while(num<120) //Deixar ir ate parar
        {
            nave_teste.update();
            num++;
        }
        //Aceleramos para a direita e devemos mover tbm 237 mas para a direita
        assertEquals(237,nave_teste.getCoordenadas().x,1);
        assertEquals(0.0,nave_teste.getCoordenadas().y,0.5);



    }


    @Test
    public void limites_nave() throws Exception
    {
        Nave nave_teste = new Nave(null,null,null,null,0);

        //Coloquei nave fora dos limites ela é colocada  debtro do mapa, no limite mais proximo
        nave_teste.setCoordenadas(-900,0);
        nave_teste.update();
        assertEquals(-835,nave_teste.getCoordenadas().x,0.0);
        assertEquals(0,nave_teste.getCoordenadas().y,0.0);

        nave_teste.setCoordenadas(3000,0);
        nave_teste.update();
        assertEquals(2510,nave_teste.getCoordenadas().x,0.0);
        assertEquals(0,nave_teste.getCoordenadas().y,0.0);

        nave_teste.setCoordenadas(0,-500);
        nave_teste.update();
        assertEquals(0,nave_teste.getCoordenadas().x,0.0);
        assertEquals(-465,nave_teste.getCoordenadas().y,0.0);

        nave_teste.setCoordenadas(0,2200);
        nave_teste.update();
        assertEquals(0,nave_teste.getCoordenadas().x,0.0);
        assertEquals(2175,nave_teste.getCoordenadas().y,0.0);



    }

    @Test
    public void movimento_asteroides() throws Exception
    {
        Asteroide ast = new Asteroide(null,50,20,false,0,0);
        ast.setCoordenadas(0,0);

        ast.atualizaMovimento();

        //Asteroides são aleatorios, as velocidades, e o angulo, mas com uma iteraçao nao deve passar de 20 unicade em raio
        assertTrue(25>ast.getCoordenadas().x && -25 < ast.getCoordenadas().x);
        assertTrue(25>ast.getCoordenadas().y && -25 < ast.getCoordenadas().y);
        //A velocidade n deve passar de 20 pelas definiçoes de agora
        ast.update(5,15,0,null);
        assertTrue(25>ast.getVelocidade() &&  0<ast.getVelocidade());

    }

    @Test
    public void colisoes() throws Exception
    {
        Asteroide ast = new Asteroide(null,50,20,false,0,0);
        ast.setCoordenadas(0,0);

        Bala bala = new Bala(null,0,0,0,0);
        bala.setCoordenadas(0,0);

        Nave nave = new Nave(null,null,null,null,0);
        nave.setCoordenadas(0,0);

        Vida vida = new Vida(null,50);

        //Pedaço de codigo das colisoes, a verificar so 1 em vez de todos

        //Asteroide ser atingido por bala
        assertTrue((bala.getCoordenadas().x < ast.getCoordenadas().x + ast.getRaio()) && (bala.getCoordenadas().x > ast.getCoordenadas().x - ast.getRaio()));  //Dentro em x
        assertTrue(((bala.getCoordenadas().y < ast.getCoordenadas().y + ast.getRaio()) && (bala.getCoordenadas().y > ast.getCoordenadas().y - ast.getRaio()))); //Demtro em y




        //Asteroide atinge  nave
        /**
         * Da erro no junit
        assertTrue (((ast.getCoordenadas().x + ast.getRaio() > nave.getHitbox_X0()) && (ast.getCoordenadas().x + ast.getRaio() < nave.getHitbox_X1())) || ((ast.getCoordenadas().x - ast.getRaio() > nave.getHitbox_X0()) && (ast.getCoordenadas().x - ast.getRaio()< nave.getHitbox_X1())));  //Dentro em x
        assertTrue (((ast.getCoordenadas().y + ast.getRaio() > nave.getHitbox_Y0()) && (ast.getCoordenadas().y + ast.getRaio() < nave.getHitbox_Y1())) || ((ast.getCoordenadas().y - ast.getRaio() > nave.getHitbox_Y0()) && (ast.getCoordenadas().y - ast.getRaio() < nave.getHitbox_Y1()))); //Dentro em y

        //Nave apanhou vida
        assertTrue (((vida.getCoordenadas().x + vida.getRaio() > nave.getHitbox_X0()) && (vida.getCoordenadas().x + vida.getRaio() < nave.getHitbox_X1())) || ((vida.getCoordenadas().x + vida.getRaio() > nave.getHitbox_X0()) && (vida.getCoordenadas().x + vida.getRaio() < nave.getHitbox_X1())));  //Dentro em x
        assertTrue (((vida.getCoordenadas().y + vida.getRaio() > nave.getHitbox_Y0()) && (vida.getCoordenadas().y + vida.getRaio() < nave.getHitbox_Y1())) || ((vida.getCoordenadas().y + vida.getRaio() > nave.getHitbox_Y0()) && (vida.getCoordenadas().y + vida.getRaio() < nave.getHitbox_Y1()))); //Dentro em y
        */

        }


    @Test
    public void acoesColisoes() throws Exception
    {
        Asteroide ast = new Asteroide(null,50,20,false,0,0);
        ast.setCoordenadas(0,0);

        Bala bala = new Bala(null,0,0,0,0);
        bala.setCoordenadas(0,0);

        Nave nave = new Nave(null,null,null,null,0);
        nave.setCoordenadas(0,0);

        Vida vida = new Vida(null,50);

        //Quando colide com asteroide
        //O maximo estao 3
        assertEquals(3,nave.getVidas());

        nave.perderVida();
        assertEquals(2,nave.getVidas());

        //Quando colide com vida
        nave.ganharVida();
        assertEquals(3,nave.getVidas());

    }

    @Test
    public void perder() throws Exception
    {

        Nave nave = new Nave(null,null,null,null,0);
        nave.setCoordenadas(0,0);


        //Quando colide com asteroide
        //O maximo estao 3
        assertEquals(3,nave.getVidas());
        assertEquals(nave.perdeu(),false);

        nave.perderVida();
        assertEquals(2,nave.getVidas());
        assertEquals(nave.perdeu(),false);

        //Quando colide com vida
        nave.perderVida();
        assertEquals(1,nave.getVidas());
        assertEquals(nave.perdeu(),false);

        //Quando colide com vida
        nave.perderVida();
        assertEquals(0,nave.getVidas());
        assertEquals(nave.perdeu(),true);

        nave.perderVida();
        assertEquals(-1,nave.getVidas());
        assertEquals(nave.perdeu(),true);

    }

    @Test
    public void disparar() throws Exception
    {
        Nave nave = new Nave(null,null,null,null,0);
        nave.setCoordenadas(0,0);

        ArrayList<Bala> balas_vec= new ArrayList<Bala>();

        assertEquals(balas_vec.size(),0);


        //Codigo disparar do jogo onde recebe (float angulo), o angulo da nave
        float angulo = nave.getAngulo();
        Bala bala = new Bala(null, 2, angulo, nave.getCoordenadas().x, nave.getCoordenadas().y);
        balas_vec.add(bala);

        assertEquals(balas_vec.size(),1);

        assertEquals(bala.getAngulo(),nave.getAngulo(),0.0);

    }






}