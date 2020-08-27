package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int activePlayer = 0; // 0 = O and 1 = X

    boolean gameActive = true;

    private int[] gameState = { 2,2,2,2,2,2,2,2,2 };    // 2 = null     1 = X   0 = 0

    private int [][] winPos = { {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };

    public void tap(View view)
    {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if(!gameActive)
        {
            gameReset(view);
            return;
        }
        if(gameState[tappedImage] == 2 ) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if (activePlayer == 0) {
                TextView status = findViewById(R.id.status);
                img.setImageResource(R.drawable.oh);
                activePlayer = 1;
                status.setText("X's Turn - Tap to play");
            } else {
                TextView status = findViewById(R.id.status);
                img.setImageResource(R.drawable.ex);
                activePlayer = 0;
                status.setText("0's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);

        }
        //Check if any player has won the game

        int win=0;

        for(int[] winPosition: winPos)
        {
            if(gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[2]] == gameState[winPosition[1]] && gameState[winPosition[0]] != 2)
            {
                //someone won!!
                String winner;
                gameActive = false;
                win=1;
                if(gameState[winPosition[0]] == 0 )
                {
                    winner="0 has won!!";
                }
                else
                {
                    winner="X has won!!";
                }
                //update status bar

                TextView status = findViewById(R.id.status);
                status.setText(winner);
            }
        }

        //Check if the game is draw

        int draw=1;

        for(int i=0;i<gameState.length;i++)
        {
            if(gameState[i] ==2 )
            {
                draw=0;
                break;
            }
        }
        if( draw ==1 && win == 0)
        {
            gameActive = false;
            TextView status = findViewById(R.id.status);
            status.setText("DRAW GAME!!!");
        }


    }



    private void gameReset(View view)
    {
        gameActive=true;
        activePlayer=0;
        for(int i=0;i<gameState.length;i++)
            gameState[i]=2;
        ((ImageView) findViewById(R.id.image0)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage1)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage2)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage3)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage4)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage5)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage6)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage7)).setImageResource(0);
        ((ImageView) findViewById(R.id.Oimage8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("0's Turn - Tap to play");

        return ;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameReset(v);
            }
        });
    }
}
