package dev.bamideleoke.gadsleaderboard.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import dev.bamideleoke.gadsleaderboard.R;
import dev.bamideleoke.gadsleaderboard.userhome.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        ImageView gadsBanner = findViewById(R.id.banner);
        gadsBanner.setAnimation(slide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }, 2000);
    }
}