package com.feup.nuno.asteroidsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Ajuda_pop_Activity extends AppCompatActivity
{
    @Override
    /**
     * Construtor da actividade
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_ajuda_pop);

        DisplayMetrics display_metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display_metrics);

        int largura = display_metrics.widthPixels;
        int altura = display_metrics.widthPixels;

        getWindow().setLayout((int)(largura*0.65),(int)(altura*0.65));
    }


}
