package com.feup.nuno.asteroidsandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.feup.nuno.asteroidsandroid.Utilidades.Data;
import com.feup.nuno.asteroidsandroid.Logica.Jogo;
import com.feup.nuno.asteroidsandroid.Utilidades.Sons;

import java.util.ArrayList;

/**
 * Created by Nuno on 30/04/2016.
 */
public class JogoView extends Activity implements View.OnTouchListener
{
    private OurView v;
    private Data data = Data.getInstance();
    private Jogo jogo;
    private float touch_x, touch_y;
    private Sons musica;


    private final int FPS = 60;
    private final long FPS_MS = 1000 / FPS;
    //_   _    _    _    _    _    _    _    _    _    _


   @Override
   /**
    * Construtor da actividade
    */
    protected void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       v=new OurView(this);

       touch_x = touch_y = 0;

       v.setOnTouchListener(this);


       requestWindowFeature (Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

       setContentView(v);
   }

    /**
     * Pausa e mostra o menu de pausa do jogo, assim como o score feito ate ao momento
     */
    public void menu_pausa()
    {
        //Guarda a pontuação
        SharedPreferences prefs = getSharedPreferences("Prefs_Pontuacao", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("pontuacao_key", jogo.getPontuacao());
        while (!editor.commit())
        {}
        startActivity(new Intent("com.feup.nuno.asteroidsandroid.pause_pop"));
    }

    @Override
    public void onBackPressed()
    {
        menu_pausa();
    }

    @Override
    protected void onPause()
    {
        musica.pauseMusica();
        jogo.setMusicaON(false);
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        v.resume();

        SharedPreferences prefs = getSharedPreferences("Prefs_som", Context.MODE_PRIVATE);
        int som_val = prefs.getInt("som_key",0); //Vai buscar o valor do estado
        if(som_val==0)
        {
            musica.restartMusica();
            jogo.setMusicaON(true);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent me)
    {
        int pointerIndex = me.getActionIndex();
        int pointerID = me.getPointerId(pointerIndex);

        int motionaction = me.getActionMasked();

        if (motionaction == MotionEvent.ACTION_DOWN || motionaction == MotionEvent.ACTION_POINTER_DOWN)
        {
            touch_x = me.getX(pointerIndex);
            touch_y = me.getY(pointerIndex);
            jogo.touch(touch_x, touch_y, true, pointerID);
        }

        if (motionaction == MotionEvent.ACTION_MOVE)
        {
            touch_x = me.getX(pointerIndex);
            touch_y = me.getY(pointerIndex);
            jogo.touch(touch_x, touch_y, true, pointerID);
        }

        if (motionaction == MotionEvent.ACTION_UP || motionaction == MotionEvent.ACTION_POINTER_UP || motionaction == MotionEvent.ACTION_CANCEL)
        {
            touch_x = me.getX(pointerIndex);
            touch_y = me.getY(pointerIndex);
            jogo.touch(touch_x, touch_y, false, pointerID);
        }


        return true;
    }

    @Override
    protected void onStop()
    {
        finish();
        musica.clean();
        musica=null;
        jogo.clean();
        jogo = null;
        data.clean();
        data = null;
        Runtime.getRuntime().gc();
        System.gc();
        super.onStop();
    }

    /**
     * Carrega as imagens do jogo
     */
    public void iniciarImagens()
    {
        data.setNave_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.nave_1));
        data.setNave_acel_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.nave_2));
        data.setNave_inv_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.nave_1_inv));
        data.setNave_acel_inv_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.nave_2_inv));

        data.setRodarEsquerda_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.esquerda));
        data.setRodarDireita_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.direita));
        data.setDisparar_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.disparar));
        data.setAcelerar_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.acelerar));
        data.setPausa_img(BitmapFactory.decodeResource(getResources(), R.drawable.pausa));

        data.setBala_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.bala));

        data.setVida_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.vida));

        data.setAsteroide_IMG_G1(BitmapFactory.decodeResource(getResources(), R.drawable.ast_g1));
        data.setAsteroide_IMG_G2(BitmapFactory.decodeResource(getResources(), R.drawable.ast_g2));
        data.setAsteroide_IMG_G3(BitmapFactory.decodeResource(getResources(), R.drawable.ast_g3));
        data.setAsteroide_IMG_G4(BitmapFactory.decodeResource(getResources(), R.drawable.ast_g4));
        data.setAsteroide_IMG_P1(BitmapFactory.decodeResource(getResources(), R.drawable.ast_p1));
        data.setAsteroide_IMG_P2(BitmapFactory.decodeResource(getResources(), R.drawable.ast_p2));
        data.setAsteroide_IMG_P3(BitmapFactory.decodeResource(getResources(), R.drawable.ast_p3));

        data.setCrack1_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.crack1));
        data.setCrack2_IMG(BitmapFactory.decodeResource(getResources(), R.drawable.crack2));


        //Animaçao, tem de ser por ordem
        data.explosao_frames = new ArrayList<Bitmap>();
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame1));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame2));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame3));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame4));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame5));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame6));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame7));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame8));
        data.addExplosao_frames(BitmapFactory.decodeResource(getResources(), R.drawable.frame9));



    }

    /**
     * Carrega os sons de efeitos especiais do jogo
     */
    public void iniciarSonsEfeitos(Context context)
    {
        musica.setAcelerar_som(MediaPlayer.create(context,R.raw.acelerar_som));
        musica.setApanhar_vida_som(MediaPlayer.create(context,R.raw.vida_som));
        musica.setDisparar_som(MediaPlayer.create(context,R.raw.bala_som));
        musica.setExplosao_som(MediaPlayer.create(context,R.raw.explosao_som));
    }

//======================================================================================================================================
//======================================================================================================================================
//======================================================================================================================================

    /**
     * Vista personalizada para correr o jogo
     */
    public class OurView extends SurfaceView implements Runnable
    {
        Thread t=null;
        SurfaceHolder surf_holder;
        boolean estaOK=false;


        public OurView(Context context)
        {
            super(context);
            surf_holder=getHolder();

            //Obter informacao sobre o dispositivo ============================
            //Densidade
            float densidade = Resources.getSystem().getDisplayMetrics().density;

            //Tamanho do ecra
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            //===============================================================

            //Imagem de fundo
            Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);

            jogo = new Jogo(data,background, (int) densidade, size.x, size.y);

            iniciarImagens();


            boolean musicaON;
            musicaON= controlador_som(context);

            if(musicaON) //Otimizar uso de memoria
                iniciarSonsEfeitos(context);

            //Inicia o jogo
            jogo.iniciar(musica,musicaON);

        }


        /**
         * Ciclo onde corre, verifica os estados e desenha o jogo
         */
        public void run()
        {
            while(estaOK)
            {
                //Controla os FPS
                try
                {
                    t.sleep(FPS_MS,0);
                } catch (InterruptedException e)
                {}


                if (!surf_holder.getSurface().isValid())
                    continue;

                //Update
                if(jogo.update())
                    perderJogo();

                if(jogo.isPaused())
                    menu_pausa();


                //Desenhar
                Canvas canvas = surf_holder.lockCanvas(); //Bloqueia canvas
                canvas.drawARGB(255, 0, 0, 0); //fundo preto

                jogo.draw(canvas);

                surf_holder.unlockCanvasAndPost(canvas); //Desbloqueia canvas

            }
        }


        /**
         * Guarda a pontuacao que fez e muda para a actividade de GAMEOVE0R (Score_Activity)
         */
        private void perderJogo()
        {
            //Guarda a pontuação
            SharedPreferences prefs = getSharedPreferences("Prefs_Pontuacao", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("pontuacao_key", jogo.getPontuacao());
            while (!editor.commit())
            {}

            Intent intent = new Intent("com.feup.nuno.asteroidsandroid.Score_Activity");
            startActivity(intent);
            finish();
        }

        /**
         * Verifica se o som esta ligado e carrega a musica em caso afirmativo
         *
         * @param context contexto da actividade
         * @return true se o som estiver ligado, false caso contrario
         */
        private boolean controlador_som(Context context)
        {
            //Vai buscar o estado da opçao som
            SharedPreferences prefs = getSharedPreferences("Prefs_som", Context.MODE_PRIVATE);
            int som_val = prefs.getInt("som_key", 0);
            musica = new Sons(MediaPlayer.create(context, R.raw.inicio), MediaPlayer.create(context, R.raw.loop));
            boolean musicaON;
            if (som_val == 0)
            {
                musica.startMusica();
                musicaON = true;
            } else
            {
                musica.pauseMusica();
                musicaON = false;
            }
            //__________________________________________________

            return musicaON;
        }


        /**
         *  Pausa o thread
         */
        public void pause()
        {
            estaOK=false;
            while(true)
            {
                try
                {
                    t.join();
                    break;
                }catch (InterruptedException e)
                {e.printStackTrace();}
            }
            t=null;
        }

        /**
         * Resume o thread
         */
        public void resume()
        {
            estaOK=true;
            t=new Thread(this);
            t.start();
        }

    }











}
