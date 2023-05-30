package com.example.todo_list.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo_list.R;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progress = 0;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++){
                    progress += 20;
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress);
                            if(progress == progressBar.getMax()){
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(SplashActivity.this , sign_in.class));
                            }
                        }
                    });
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();
    }
}
