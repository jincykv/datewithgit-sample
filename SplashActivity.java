package com.business.supermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
   private Handler handler;
   ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.pb);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               progressBar.setVisibility(View.GONE);
               startActivity(new Intent(SplashActivity.this,LoginActivity.class));
               finish();
            }
        },1500);
    }
}