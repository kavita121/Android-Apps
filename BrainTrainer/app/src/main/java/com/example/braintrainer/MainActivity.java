package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int ansBlock;
    public int timeLeft = 25;
    static int totalCount = 0, correctCount = 0;

    @SuppressLint("SetTextI18n")
    public void checkAnswer(View view)
    {
        Button clicked = (Button) view;
        TextView Score = findViewById(R.id.score);
        TextView status = findViewById(R.id.status);
        int tag = Integer.parseInt(clicked.getTag().toString());
        if(tag == ansBlock)
        {
            status.setText("Correct!!");
            totalCount++;
            correctCount++;
        }
        else
        {
            status.setText("Wrong :(");
            totalCount++;
        }

        Score.setText(correctCount +"/"+ totalCount);
        setUpProblem();

    }

    public void resetGame(View view)
    {
        setUpProblem();
        final Button reset = findViewById(R.id.restart);
        reset.setVisibility(View.INVISIBLE);
        TextView status = findViewById(R.id.status);
        status.setText("");
        TextView score = findViewById(R.id.score);
        score.setText("0/0");
        totalCount = 0;
        correctCount = 0;
        startTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button reset = findViewById(R.id.restart);
        reset.setVisibility(View.INVISIBLE);

        setUpProblem();

        startTimer();

    }



    public void startTimer()
    {
        timeLeft = 25;
        final Button reset = findViewById(R.id.restart);
        new CountDownTimer(25100,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

                TextView timer = findViewById(R.id.Timer);
                timer.setText(String.valueOf(millisUntilFinished / 1000)+"s");
                timeLeft = (int ) millisUntilFinished / 1000;

            }

            @Override
            public void onFinish() {

                TextView status = findViewById(R.id.status);
                status.setText("Done!!");
                disableButtons();
                reset.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void disableButtons()
    {
        Button buttonFirst = findViewById(R.id.buttonFirst);
        Button buttonSecond = findViewById(R.id.buttonSecond);
        Button buttonThird = findViewById(R.id.buttonThird);
        Button buttonForth = findViewById(R.id.buttonForth);

        buttonFirst.setEnabled(false);
        buttonSecond.setEnabled(false);
        buttonThird.setEnabled(false);
        buttonForth.setEnabled(false);

    }

    public void setUpProblem()
    {

        ArrayList<Integer> arr = new ArrayList<>();

        arr.clear();

        TextView prob = findViewById(R.id.problem);

        Button buttonFirst = findViewById(R.id.buttonFirst);
        Button buttonSecond = findViewById(R.id.buttonSecond);
        Button buttonThird = findViewById(R.id.buttonThird);
        Button buttonForth = findViewById(R.id.buttonForth);

        int a, b, op;       //op :  0 = +       1 = -
        Random rand = new Random();
        op = rand.nextInt(2);
        a = rand.nextInt(35);
        b = rand.nextInt(35);
        int ans ;


        if( op == 0 )
            ans = a + b;
        else
            ans = a - b;
        String problem ="";
        problem += a;
        problem += (op == 0) ? " + " : " - ";
        problem += b;

        prob.setText(problem);

        ansBlock = rand.nextInt(4);     // value corresponding to the tags

        for( int i = 0 ; i < 4 ; i++)
        {
            int number = rand.nextInt(71);
            if(i == ansBlock)
                arr.add(ans);
            else
            {
                while( number == ans )
                {
                    number = rand.nextInt(71);
                }
                arr.add(number);
            }
        }
        int i=0;
        buttonFirst.setText(Integer.toString(arr.get(i)));
        i++;
        buttonSecond.setText(Integer.toString(arr.get(i)));
        i++;
        buttonThird.setText(Integer.toString(arr.get(i)));
        i++;
        buttonForth.setText(Integer.toString(arr.get(i)));
        arr.clear();

    }

}