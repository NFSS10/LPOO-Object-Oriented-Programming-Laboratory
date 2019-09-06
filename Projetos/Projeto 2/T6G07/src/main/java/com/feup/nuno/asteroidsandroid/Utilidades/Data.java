package com.feup.nuno.asteroidsandroid.Utilidades;

import android.graphics.Bitmap;

import com.feup.nuno.asteroidsandroid.Logica.ObjectoEstatico;

import java.util.ArrayList;

/**
 * Created by Nuno on 31/05/2016.
 * Singleton Data, que tem dados usados pelo jogo. Usada para simplidicar o codigo (Design Pattern: Private Class Data)
 */
public class Data
{
    private static Data ourInstance = new Data();

    //Info sobre dispositivo ++++++++++++++++++++++++++++++++++++++++++++++
    private float densidade_ecra;
    private int altura_ecra;
    private int largura_ecra;

    //Variaveis uteis para compatibilidade com outros ecras
    private float erro_nave_x;
    private float erro_nave_y;
    private int erroX;
    private int erroY;


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //Bitmaps ==============================================================
    private Bitmap fundo;
    private Bitmap crack1;
    private Bitmap crack2;

    private Bitmap rodarEsquerda_img;
    private Bitmap rodarDireita_img;
    private Bitmap disparar_img;
    private Bitmap acelerar_img;
    private Bitmap pausa_img;

    private Bitmap nave_img;
    private Bitmap nave_img_aelerar;
    private Bitmap nave_img_inv;
    private Bitmap nave_img_aelerar_inv;

    private Bitmap vida_img;

    private Bitmap bala_img;


    private Bitmap asteroide_G1_img;
    private Bitmap asteroide_G2_img;
    private Bitmap asteroide_G3_img;
    private Bitmap asteroide_G4_img;
    private Bitmap asteroide_P1_img;
    private Bitmap asteroide_P2_img;
    private Bitmap asteroide_P3_img;


    public ArrayList<Bitmap> explosao_frames;
    //====================================================================

    //Botoes +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private ObjectoEstatico b_rodarEsquerda;
    private ObjectoEstatico b_rodarDireita;
    private ObjectoEstatico b_Disparar;
    private ObjectoEstatico b_Acelerar;
    private ObjectoEstatico b_Pausa;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    /**
     * Retorna Data
     *
     * @return a unica instancia de Data
     */
    public static Data getInstance()
    {
        return ourInstance;
    }

    /**
     * Construtor
     */
    private Data()
    {
    }

    /**
     * Calcula os valores das variaveis encarregues de tratar da compatibilidade com ecras de diferentes dimensões
     */
    public void calcula_ver_compatibilidade()
    {
        erro_nave_x = 905-(largura_ecra/2);
        erro_nave_y = 540 - (altura_ecra/2);

        erroX = 1810-largura_ecra;
        erroY = 1080-altura_ecra;
    }


    /**
     * Inicia os botoes colocando-os nos sitios pretendidos
     */
    public void iniciarObjectosEstaticos()
    {
        //Inicia os Botoes ================================================================================
        b_rodarEsquerda = new ObjectoEstatico(rodarEsquerda_img, rodarEsquerda_img.getHeight());
        b_rodarDireita = new ObjectoEstatico(rodarDireita_img, rodarDireita_img.getHeight());
        b_Disparar = new ObjectoEstatico(disparar_img, disparar_img.getHeight());
        b_Acelerar = new ObjectoEstatico(acelerar_img, acelerar_img.getHeight());
        b_Pausa = new ObjectoEstatico(pausa_img, pausa_img.getHeight());



        //Coordenadas especificas
        b_rodarEsquerda.setCoordenadas((40 * densidade_ecra) + (b_rodarEsquerda.getImagem().getWidth() / 2),
                (altura_ecra - 50 * densidade_ecra) + (b_rodarEsquerda.getImagem().getHeight() / 2));

        b_rodarDireita.setCoordenadas((130 * densidade_ecra) + (b_rodarDireita.getImagem().getWidth() / 2),
                (altura_ecra - 50 * densidade_ecra) + (b_rodarDireita.getImagem().getHeight() / 2));

        b_Acelerar.setCoordenadas((largura_ecra - (40 * densidade_ecra) - b_Acelerar.getImagem().getWidth()) + (b_Acelerar.getImagem().getWidth() / 2),
                (altura_ecra - 50 * densidade_ecra) + (b_Acelerar.getImagem().getHeight() / 2));

        b_Disparar.setCoordenadas((largura_ecra - (130 * densidade_ecra) - b_Disparar.getImagem().getWidth()) + (b_Disparar.getImagem().getWidth() / 2),
                (altura_ecra - 50 * densidade_ecra) + (b_Disparar.getImagem().getHeight() / 2));

        b_Pausa.setCoordenadas((largura_ecra - (40 * densidade_ecra) - b_Disparar.getImagem().getWidth()) + (b_Pausa.getImagem().getWidth() / 2),
                (10 * densidade_ecra) + (b_Pausa.getImagem().getHeight() / 2));
    }


    /**
     * Atribui um valor à variável referente à densidade do ecra do dispositivo
     * @param d o valor pretendido da densidade
     */
    public void setDensidade_ecra(float d)
    {
        densidade_ecra = d;
    }
    /**
     * Atribui um valor à variável referente à altura do ecra do dispositivo
     * @param a o valor pretendido da altura
     */
    public void setAltura_ecra(int a)
    {
        altura_ecra = a;
    }
    /**
     * Atribui um valor à variável referente à largura do ecra do dispositivo
     * @param l o valor pretendido da largura
     */
    public void setLargura_ecra(int l)
    {
        largura_ecra = l;
    }


    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setFundo_IMG(Bitmap img)
    {
        fundo = img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setCrack1_IMG(Bitmap img)
    {
        crack1 = img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setCrack2_IMG(Bitmap img)
    {
        crack2 = img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_G1(Bitmap img)
    {
        asteroide_G1_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_G2(Bitmap img)
    {
        asteroide_G2_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_G3(Bitmap img)
    {
        asteroide_G3_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_G4(Bitmap img)
    {
        asteroide_G4_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_P1(Bitmap img)
    {
        asteroide_P1_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_P2(Bitmap img)
    {
        asteroide_P2_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAsteroide_IMG_P3(Bitmap img)
    {
        asteroide_P3_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setBala_IMG(Bitmap img)
    {
        bala_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setNave_IMG(Bitmap img)
    {
        nave_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setVida_IMG(Bitmap img)
    {
        vida_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setNave_acel_IMG(Bitmap img)
    {
        nave_img_aelerar=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setNave_inv_IMG(Bitmap img)
    {
        nave_img_inv=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setNave_acel_inv_IMG(Bitmap img)
    {
        nave_img_aelerar_inv=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setRodarEsquerda_IMG(Bitmap img)
    {
        rodarEsquerda_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setRodarDireita_IMG(Bitmap img)
    {
        rodarDireita_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setDisparar_IMG(Bitmap img)
    {
        disparar_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setAcelerar_IMG(Bitmap img)
    {
        acelerar_img=img;
    }
    /**
     * Atribui a imagem à variável
     * @param img imagem pretendida
     */
    public void setPausa_img(Bitmap img)
    {
        pausa_img=img;
    }


    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public float getDensidade_ecra()
    {
        return densidade_ecra;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public int getAltura_ecra()
    {
        return altura_ecra;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public int getLargura_ecra()
    {
        return largura_ecra;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public float getErro_nave_x() {return erro_nave_x;}
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public float getErro_nave_y(){return erro_nave_y;}
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public float getErroX(){return erroX;}
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public float getErroY(){return erroY;}


    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getCrack1_IMG()
    {
        return crack1;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getCrack2_IMG()
    {
        return crack2;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getFundo_IMG()
    {
        return fundo;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_G1_IMG()
    {
        return asteroide_G1_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_G2_IMG()
    {
        return asteroide_G2_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_G3_IMG()
    {
        return asteroide_G3_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_G4_IMG()
    {
        return asteroide_G4_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_P1_IMG()
    {
        return asteroide_P1_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_P2_IMG()
    {
        return asteroide_P2_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getAsteroide_P3_IMG()
    {
        return asteroide_P3_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getBala_IMG()
    {
        return bala_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getNave_IMG()
    {
        return nave_img;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getNave_acel_IMG()
    {
        return nave_img_aelerar;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getNave_inv_IMG()
    {
        return nave_img_inv;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getNave_acel_inv_IMG()
    {
        return nave_img_aelerar_inv;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public Bitmap getVida_IMG()
    {
        return vida_img;
    }


    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public ObjectoEstatico get_b_rodarEsquerda()
    {
        return b_rodarEsquerda;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public ObjectoEstatico get_b_rodarDireita()
    {
        return b_rodarDireita;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public ObjectoEstatico get_b_Disparar()
    {
        return b_Disparar;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public ObjectoEstatico get_b_Acelerar()
    {
        return b_Acelerar;
    }
    /**
     * Retorna a referecia à variàvel pretendida
     * @return variável pretendida
     */
    public ObjectoEstatico get_b_Pausa()
    {
        return b_Pausa;
    }


    /**
     * Adiciona image a animaçao
     */
    public void addExplosao_frames(Bitmap img)
    {
        explosao_frames.add(img);
    }

    /**
     * Retorn a animaçao
     */
    public ArrayList<Bitmap> getExplosao_frames()
    {
       return explosao_frames;
    }


    /**
     * Limpa as variaveis, para libertar memória
     */
    public void clean()
    {
        fundo = null;
        crack1 = null;
        crack2 = null;

        rodarEsquerda_img = null;
        rodarDireita_img = null;
        disparar_img = null;
        acelerar_img = null;

        nave_img = null;
        nave_img_aelerar = null;
        nave_img_inv = null;
        nave_img_aelerar_inv = null;

        vida_img = null;

        bala_img = null;


        asteroide_G1_img = null;
        asteroide_G2_img = null;
        asteroide_G3_img = null;
        asteroide_G4_img = null;
        asteroide_P1_img = null;
        asteroide_P2_img = null;
        asteroide_P3_img = null;


        b_rodarEsquerda.clean();
        b_rodarDireita.clean();
        b_Disparar.clean();
        b_Acelerar.clean();
        b_Pausa.clean();

        b_rodarEsquerda=null;
        b_rodarDireita=null;
        b_Disparar=null;
        b_Acelerar=null;
        b_Pausa = null;

        explosao_frames=null;

    }



}
