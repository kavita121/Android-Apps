package com.example.caketimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import static com.example.caketimer.R.raw.aipplane;
import static com.example.caketimer.R.raw.elevator;

public class MainActivity extends AppCompatActivity {


    MediaPlayer play ;

    Boolean counterActive = false;

    CountDownTimer countDown;

    SeekBar seek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView time = findViewById(R.id.timer);

        play = MediaPlayer.create( this , elevator);

        seek = findViewById(R.id.seekBar);

        final int[] duration = new int[1];

        seek.setMax(5400);
        seek.setProgress(30);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String min, sec;
                sec = Integer.toString(progress % 60);
                min = Integer.toString(progress / 60);
                if(Integer.parseInt(sec) < 10) {
                    String s= sec;
                    sec="0"+s ;
                }
                time.setText(min+":"+sec);

                duration[0] = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button go = findViewById(R.id.button);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer(duration[0],seek);
            }
        });

    }

    public void StartTimer(int duration, final SeekBar seek)
    {
        final TextView time = findViewById(R.id.timer);

        final Button go = findViewById(R.id.button);

        duration*=1000;

        if(counterActive)
        {
            resetTimer();
        }
        else
        {
            counterActive = true;
            seek.setEnabled(false);
            go.setText("Stop");
            countDown = new CountDownTimer(duration,1000)
            {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long timeLeft)
                {
                    String min, sec;
                    sec = Integer.toString((int) ((timeLeft /1000) % 60));
                    min = Integer.toString((int) ((timeLeft / 1000)) / 60);
                    if(Integer.parseInt(sec) < 10) {
                        String s= sec;
                        sec="0"+s ;
                    }
                    time.setText(min+":"+sec);
                }

                @Override
                public void onFinish() {

                    play.start();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        popUpNotify();
                    }
                    resetTimer();

                }
            }.start();
        }

    }

    public void resetTimer()
    {
        final TextView time = findViewById(R.id.timer);

        final Button go = findViewById(R.id.button);

        time.setText("0:30");
        seek.setProgress(30);
        seek.setEnabled(true);
        countDown.cancel();
        go.setText("Go");
        counterActive = false;
    }



    //To generate the notification  modified on 09/08/2020
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void popUpNotify()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
        {
            NotificationChannel channel = new NotificationChannel("notify","notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notify");
        builder.setContentText("Notification generated!!");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setContentTitle("Timer Over");
        builder.setContentText("Time to remove cake from oven!!");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(99,builder.build());

    }

}