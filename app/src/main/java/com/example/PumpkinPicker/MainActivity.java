package com.example.PumpkinPicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.PumpkinPicker.model.HighScoreManager;
import com.example.PumpkinPicker.model.Options;

/*
    Main activity serves as a launch point for three activities, GameActivity, HelpActivity, and OptionsActivity.
    Each activity launches from here and returns here. This activity is launched from the welcome activity.
    Moving back from this activity will close the app
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSavedData();

        initBtns();

    }

    public static Intent makeLaunchIntent(Context context) {
        return (new Intent(context, MainActivity.class));
    }


    private void initBtns() {
        Button btnMoveToGame = findViewById(R.id.btn_move_to_game);
        Button btnMoveToOptions = findViewById(R.id.btn_move_to_options);
        Button btnMoveToHelp = findViewById(R.id.btn_move_to_help);

        btnMoveToGame.setOnClickListener(v -> {
            Intent intent = GameActivity.makeLaunchIntent(MainActivity.this);
            startActivity(intent);
        });

        btnMoveToOptions.setOnClickListener(v -> {
            Intent intent = OptionsActivity.makeLaunchIntent(MainActivity.this);
            startActivity(intent);
        });

        btnMoveToHelp.setOnClickListener(v -> {
            Intent intent = HelpActivity.makeLaunchIntent(MainActivity.this);
            startActivity(intent);
        });

    }

    // These are values that need to be retrieved right away
    private void getSavedData() {

        Options options = Options.getInstance();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE);
        int row = prefs.getInt(getString(R.string.KEY_ROW_SIZE), 4);
        int col = prefs.getInt(getString(R.string.KEY_COL_SIZE), 6);
        int numPumpkins = prefs.getInt(getString(R.string.KEY_NUM_PUMPKINS), 6);
        int numPlays = prefs.getInt(getString(R.string.KEY_NUM_PLAYS), 0);

        options.setNumRows(row);
        options.setNumCols(col);
        options.setNumPumpkins(numPumpkins);
        HighScoreManager.getInstance().setNumGamesStarted(numPlays);
    }

}
