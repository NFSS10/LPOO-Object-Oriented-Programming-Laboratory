package com.feup.nuno.asteroidsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class pause_pop extends AppCompatActivity
{
    private static Button menu_btn;
    private static Button jogar_btn;

    @Override
    /**
     * Construtor da atividade
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pause_pop);

        DisplayMetrics display_metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display_metrics);

        int largura = display_metrics.widthPixels;
        int altura = display_metrics.widthPixels;

        getWindow().setLayout((int)(largura*0.65),(int)(altura*0.65));

        //Score
        SharedPreferences prefs_pontuacao = getSharedPreferences("Prefs_Pontuacao", Context.MODE_PRIVATE);
        int score_atual = prefs_pontuacao.getInt("pontuacao_key", 0);

        //Score textview
        TextView score=new TextView(this);
        score=(TextView)findViewById(R.id.Score_textView);
        score.setText(""+score_atual);



        onClickButtonListener();
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                       finish();
                    }

                }
        );

    }

}
