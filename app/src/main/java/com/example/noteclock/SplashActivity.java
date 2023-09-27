package com.example.noteclock;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        loadingProgressBar = findViewById(R.id.loading_progress_bar);

        // Tạo một ColorStateList với màu đen
        ColorStateList colorStateList = ColorStateList.valueOf(Color.BLACK);

        // Đặt màu cho ProgressBar (ProgressBar có tính chất xác định)
        loadingProgressBar.setProgressTintList(colorStateList);

        loadingProgressBar.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
