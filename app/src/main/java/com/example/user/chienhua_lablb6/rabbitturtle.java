package com.example.user.chienhua_lablb6;

import android.net.wifi.aware.DiscoverySession;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class rabbitturtle extends AppCompatActivity {

    protected Button start;
    protected SeekBar rabbit_seekBar,turtoise_seekBar;
    int rabbit_count=0,turtoise_count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbitturtle);

        start=(Button) findViewById(R.id.start);
        rabbit_seekBar=(SeekBar)findViewById(R.id.r1);
        turtoise_seekBar=(SeekBar)findViewById(R.id.t1);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rabbitturtle.this,"開始",Toast.LENGTH_LONG).show();
                runThread();
                runAsyncTask();
            }
        });
    }

    private void runThread(){
        rabbit_count=0;
        new Thread(){
            public void run(){
                do {
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    rabbit_count+=(int)(Math.random()*3);
                    Message msg=new Message();
                    msg.what=1;
                    mHandler.sendMessage(msg);
                }while(rabbit_count<=100);
            }
        }.start();
    }
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    rabbit_seekBar.setProgress(rabbit_count);
                    break;
            }
            if (rabbit_count >= 100)
                if (turtoise_count < 100)
                    Toast.makeText(rabbitturtle.this, "兔子完成", Toast.LENGTH_LONG).show();

        }
    };

    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            protected void  onPreExecute(){
                super.onPreExecute();
                turtoise_count=0;
            }

            protected  Boolean doInBackground(Void... voids){
                do {
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    turtoise_count+=(int)(Math.random()*3);
                    publishProgress(turtoise_count);
                }while(turtoise_count<=100);
                return true;
            }
            protected void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                turtoise_seekBar.setProgress(values[0]);
            }

            protected void onPostExecute(Boolean status){
                if(rabbit_count<100)
                    Toast.makeText(rabbitturtle.this,"烏龜獲勝",Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
