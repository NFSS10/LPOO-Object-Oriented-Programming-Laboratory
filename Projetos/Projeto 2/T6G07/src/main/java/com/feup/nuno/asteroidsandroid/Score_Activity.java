package com.feup.nuno.asteroidsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Score_Activity extends AppCompatActivity
{
    private static Button menu_btn;
    private static Button jogar_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature (Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_score);


        gereScore();

        onClickButtonListener();
    }

    @Override
    /**
     * Overrride de onBackPressed
     *
     * Volta para o menu inicial se pressionar o back button
     */
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Listener dos botoes
     */
    public void onClickButtonListener()
    {
        menu_btn = (Button)findViewById(R.id.Menu_btn);
        menu_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }
        );

        jogar_btn = (Button)findViewById(R.id.btn_resumir);
        jogar_btn.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent("com.feup.nuno.asteroidsandroid.JogoView");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }
        );

    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    /**
     * Mostra o score e highscore e atualiza o highscore
     */
    private void gereScore()
    {
        //HighScore textview
        TextView highscore=new TextView(this);
        highscore=(TextView)findViewById(R.id.HighScore_textview);
        //Score textview
        TextView score=new TextView(this);
        score=(TextView)findViewById(R.id.Score_textView);

        //HighScore
        SharedPreferences prefs = getSharedPreferences("Prefs_HighScore", Context.MODE_PRIVATE);
        int highscore_num = prefs.getInt("highscore_key", 0);
        //Score
        SharedPreferences prefs_pontuacao = getSharedPreferences("Prefs_Pontuacao", Context.MODE_PRIVATE);
        int score_atual = prefs_pontuacao.getInt("pontuacao_key", 0);

        if(score_atual > highscore_num)
        {
            highscore_num=score_atual;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore_key", highscore_num);
            while(!editor.commit());
            {}
        }
        highscore.setText(""+highscore_num);
        score.setText(""+score_atual);

    }



}
