package com.example.PumpkinPicker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Display authors name, 4 animations, and skip button (to skip the animation and go to the main activity)
 */
public class WelcomeActivity extends AppCompatActivity {

    // https://www.youtube.com/watch?v=JLIFqqnSNmg
    // Automatically advance to Main Menu after animations have finished, plus 10 extra seconds
    private  static  int SPLASH_SCREEN = 10000;

    // Variables
    Animation topAnim;
    Animation bottomAnim;
    Animation rotateAnim;
    Handler handler;

    ImageView image;
    TextView gameName;
    ImageView image2LeavesFirst;
    ImageView image2LeavesSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        initBtns();

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);

        // Hooks
        image = findViewById(R.id.imageView);
        gameName = findViewById(R.id.gameName);
        image2LeavesFirst = findViewById(R.id.img_2leaves_1);
        image2LeavesSecond = findViewById(R.id.img_2leaves_2);

        gameName.setAnimation(topAnim);
        image.setAnimation(bottomAnim);
        image2LeavesFirst.setAnimation(rotateAnim);
        image2LeavesSecond.setAnimation(rotateAnim);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // To go from Welcome Activity to Main Activity
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                // To never go back to Welcome Activity
                finish();
            }
        },SPLASH_SCREEN);

    }

    private void initBtns() {
        Button moveOnBtn = findViewById(R.id.btn_move_to_main);

        moveOnBtn.setOnClickListener(v -> {
            // Stop the handler if user skip the animation
            handler.removeCallbacksAndMessages(null);
            Intent intent = MainActivity.makeLaunchIntent(WelcomeActivity.this);
            startActivity(intent);
            // To never go back to Welcome Activity
            finish();
        });
    }
}
