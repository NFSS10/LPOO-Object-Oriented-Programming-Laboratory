package com.feup.nuno.asteroidsandroid.Logica;

import android.content.pm.PathPermission;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;


/**
 * Created by Nuno on 01/05/2016.
 *
 * Classe referente à nave na qual o utilizador controla e depende para ganhar/perder o jogo. Caso a nave seja atingida, fica invencivel por uns segundos.
 */
public class Nave extends ObjectoJogo
{
    private final float V_ROTACAO = 10;
    private final double V_ACELERACAO = 20;
    private final double ACELERACAO = 2;
    private final double ATRITO=0.63;
    private final int TEMPO_INVENCIVEL = 120;
    private final int INTERVALO_PISCAR = 10;
    private final int N_MAX_VIDAS = 3;

    private Bitmap nave_acelerar_img;
    private Bitmap nave_img;
    private Bitmap nave_img_invisivel;
    private Bitmap nave_acelerar_img_invisivel;

    private int vidas;
    private int invencivel = 0;
    private boolean invisivel;

    RectF hitbox;




    /**
     * Construtor de Nave
     * @param imagem imagem da nave
     * @param raio indiferente, inciado sempre a zero
     */
    public Nave(Bitmap imagem,Bitmap imagem_acelerar,Bitmap imagem_inv,Bitmap imagem_ace_inv, int raio)
    {
        super(imagem, 0);
        vidas = N_MAX_VIDAS;
        velocidade = 0;
        nave_acelerar_img = imagem_acelerar;
        nave_img_invisivel = imagem_inv;
        nave_acelerar_img_invisivel = imagem_ace_inv;
        nave_img = imagem;

        invisivel=false;

        hitbox=new RectF(0,0,22,40);
    }

    @Override
    /**
     * Override onde muda a posição da hitbox da nave e verifica os limites do mapa do jogo
     */
    public void atualizaMovimento()
    {
        super.atualizaMovimento();


        hitbox.set(coordenadas.x - 22, coordenadas.y - 40, coordenadas.x + 22, coordenadas.y + 40);
        Matrix m = new Matrix();
        m.setRotate(angulo, coordenadas.x, coordenadas.y);
        m.mapRect(hitbox);



        //Limites mapa
        if (coordenadas.x < -835)
            coordenadas.x = -835;
        if (coordenadas.x > 2510)
            coordenadas.x = 2510;
        if (coordenadas.y < -465)
            coordenadas.y = -465;
        if (coordenadas.y > 2175)
            coordenadas.y = 2175;
    }

    /**
     * Gere o ciclo de vida da nave
     */
    public void update()
    {
        if(invencivel > 0)
        {
            perderInvencivel();
            if(invencivel%INTERVALO_PISCAR==0)
            {
                if(invisivel)
                    invisivel=false;
                else invisivel=true;
            }

            if(invencivel<1)
                invisivel=false;
        }

        atualizaMovimento();

        if (velocidade > 0)
            velocidade=velocidade-ATRITO;
        if (velocidade < 0)
            velocidade=0;

    }


    /**
     * Verifica se a nave ainda tem vidas
     *
     * @return true se nao tiver mais vidas, false caso contrario
     */
    public boolean perdeu()
    {
        if (vidas < 1)
            return true;

        return false;
    }

    /**
     * Roda a nave para a direita
     */
    public void rodarDireita()
    {
        angulo = angulo + V_ROTACAO;
    }
    /**
     * Roda a nave para a esquerda
     */
    public void rodarEsquerda()
    {
        angulo = angulo - V_ROTACAO;
    }
    /**
     * Acelera a nave gradualmente ate chegar à velocidade maxima da nave
     */
    public void acelerar()
    {
        angulo_aceleracao=angulo;
        if(velocidade < V_ACELERACAO)
            velocidade+=ACELERACAO;
        if(velocidade > V_ACELERACAO)
            velocidade = V_ACELERACAO;

        if(invisivel)
            imagem = nave_acelerar_img_invisivel;
        else
            imagem = nave_acelerar_img;
    }

    /**
     * Muda a imagem da nave para o estado de deixar de acelerar
     */
    public void parar_acelerarar_img()
    {
        if(invisivel)
            imagem=nave_img_invisivel;
        else
            imagem=nave_img;
    }

    /**
     * Verifica se a nave esta invencivel
     * @return true se esta invencivel
     */
    public boolean isInvencivel()
    {
        if(invencivel<1) //Verifica se esta invencivel e tbm se esta correto o valor
        {
            invencivel=0;
            return false;
        }
        return true;
    }

    /**
     * Torna a nave invencivel por um tempo limitado
     */
    public void setInvencivel()
    {
        invencivel=TEMPO_INVENCIVEL;
    }

    /**
     * Decrementa o tempo de invencibilidade
     */
    public void perderInvencivel()
    {
        invencivel--;
    }

    /**
     * Retira uma vida
     */
    public void perderVida()
    {
        vidas--;
    }

    /**
     * Ganha uma vida
     */
    public void ganharVida()
    {
        vidas++;
    }

    /**
     * Canto superior esquerdo da hitbox referente ao inicio da largura
     * @return valor referente
     */
    public float getHitbox_X0()
    {
        return hitbox.left;
    }
    /**
     * Canto superior direito da hitbox referente ao inicio da altura
     * @return valor referente
     */
    public float getHitbox_Y0()
    {
        return hitbox.top;
    }
    /**
     * Canto inferior direito da hitbox referente ao fim da largura
     * @return valor referente
     */
    public float getHitbox_X1()
    {
        return hitbox.right;
    }
    /**
     * Canto inferior direito da hitbox referente ao fim da altura
     * @return valor referente
     */
    public float getHitbox_Y1()
    {
        return hitbox.bottom;
    }

    /**
     * Retorna o numero de vidas atual da nave
     * @return numero de vidas
     */
    public int getVidas()
    {
        return vidas;
    }

    /**
     * Retorna o numero maximo de vidas que a nave pode ter
     * @return numero maximo de vidas
     */
    public int getN_MAX_VIDAS()
    {
        return N_MAX_VIDAS;
    }


    @Override
    /**
     * Limpa as variáveis para libertar memória
     */
    public void clean()
    {
        super.clean();
        nave_acelerar_img=null;
        nave_img=null;
        nave_img_invisivel=null;
        nave_acelerar_img_invisivel=null;

        hitbox=null;
    }



}
