package com.example.PumpkinPicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.PumpkinPicker.model.BoardInfo;
import com.example.PumpkinPicker.model.GameLogic;
import com.example.PumpkinPicker.model.HighScoreManager;
import com.example.PumpkinPicker.model.Options;

/*
    This activity shows the game board and handles all visual representations of the game play.
    Game activity handles the changing of button appearance, displaying information related to
    high scores and number of pumpkins found, and plays a sound when a square is clicked.
 */

public class GameActivity extends AppCompatActivity {

    private Options options;
    private HighScoreManager highScoreManager;

    private Button buttons[][];
    private GameLogic gameLogic;

    private TextView tvNumPumpkinsFound;
    private TextView tvScansUsed;

    private MediaPlayer btnSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        highScoreManager = HighScoreManager.getInstance();
        options = Options.getInstance();
        buttons = new Button[options.getNumRows()][options.getNumCols()];
        btnSound = MediaPlayer.create(this, R.raw.crunch);

        gameLogic = new GameLogic();

        tvNumPumpkinsFound = findViewById(R.id.tv_pumpkins_found);
        tvScansUsed = findViewById(R.id.tv_scans_used);

        highScoreManager.incrGamesStarted();
        saveNumGamesStarted();

        showHighScores();

        initTableLayout();

        updateClickCounts(0, 0);

    }

    public static Intent makeLaunchIntent(Context context) {
        return (new Intent(context, GameActivity.class));
    }

    private void showHighScores() {
        TextView tvGamesPlayed = findViewById(R.id.tv_games_played);
        TextView tvHighScore = findViewById(R.id.tv_high_score);
        getSavedHighScores();

        String strHighScore;
        if (highScoreManager.getHighScore(options.getNumRows(), options.getNumPumpkins()) < 0) {
            strHighScore = getString(R.string.lbl_high_score) + getString(R.string.no_games);
        } else {
            strHighScore = getString(R.string.lbl_high_score) +
                    highScoreManager.getHighScore(options.getNumRows(), options.getNumPumpkins());
        }

        tvGamesPlayed.setText(getString(R.string.lbl_num_games) + highScoreManager.getNumGamesStarted());
        tvHighScore.setText(strHighScore);
    }

    private void updateClickCounts(int pumpkinsFound, int scansUsed) {
        String strPumpkinsFound = getString((R.string.editable_num_pumpkins_found), pumpkinsFound, options.getNumPumpkins());
        String strScansUsed = getString(R.string.lbl_scans) + scansUsed;

        tvNumPumpkinsFound.setText(strPumpkinsFound);
        tvScansUsed.setText(strScansUsed);
    }

    private void initTableLayout() {
        TableLayout pumpkinPatch = findViewById(R.id.table_layout);

        // Get rows/columns from options singleton
        int numRows = options.getNumRows();
        int numCols = options.getNumCols();

        for (int row = 0; row < numRows; row++) {
            TableRow tableRow = new TableRow(GameActivity.this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            pumpkinPatch.addView(tableRow);

            for (int col = 0; col < numCols; col++) {
                final int currRow = row;
                final int currCol = col;

                Button button = new Button(GameActivity.this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Remove padding from buttons so there are no boarders
                button.setPadding(0, 0, 0, 0);

                button.setOnClickListener(v -> {
                    onButtonClicked(currRow, currCol);
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < options.getNumRows(); row++) {
            for (int col = 0; col < options.getNumCols(); col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void onButtonClicked(int row, int col) {
        lockButtonSizes();

        btnSound.start();

        // get logic from gameLogic
        updateAllBtns(gameLogic.afterClick(row, col));
        updateClickCounts(gameLogic.getNumPumpkinsFound(), gameLogic.getNumScansUsed());

        if (gameLogic.isAllPumpkinsFound()) {
            gameOver();
        }

    }

    private void updateAllBtns(BoardInfo[][] updateShow) {

        for (int row = 0; row < options.getNumRows(); row++) {
            for (int col = 0; col < options.getNumCols(); col++) {

                // if pumpkin exists
                if (updateShow[row][col].getShow() == BoardInfo.SHOW_PUMPKIN) {
                    showPumpkin(row, col);
                }

                if (updateShow[row][col].isScanned()) {
                    // Only show value in show IF btn scanned before
                    Button btnClicked = buttons[row][col];
                    btnClicked.setText(String.valueOf(updateShow[row][col].getHiddenPumpkin()));
                }

            }
        }


    }

    private void showPumpkin(int row, int col) {

        Button btnClicked = buttons[row][col];

        // Change image on button
        int newWidth = btnClicked.getWidth();
        int newHeight = btnClicked.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pumpkin);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        btnClicked.setBackground(new BitmapDrawable(resource, scaledBitmap));

    }

    private void gameOver() {

        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.two_leaves);

        alertDialog.setCancelable(false);
        alertDialog.setTitle(getString(R.string.game_won));
        alertDialog.setMessage(getString(R.string.you_won_msg));
        alertDialog.setView(image, 0,0,0,0);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), (dialog, which) -> {
            highScoreManager.setHighScore(options.getNumRows(), options.getNumPumpkins(), gameLogic.getNumScansUsed());
            saveHighScore();
            dialog.dismiss();
            finish();
        });
        alertDialog.show();
    }

    // only need to get high scores when specific board configuration is selected
    private void getSavedHighScores() {

        // Create specific key for current board configuration
        String key = getString((R.string.editable_key), options.getNumRows(), options.getNumPumpkins());

        SharedPreferences prefs = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE);
        int highScore = prefs.getInt(key, -1);

        highScoreManager.setHighScore(options.getNumRows(), options.getNumPumpkins(), highScore);
    }

    private void saveHighScore() {

        // Create key from row number and number of pumpkins
        String key = getString((R.string.editable_key), options.getNumRows(), options.getNumPumpkins());

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE).edit();
        editor.putInt(key, highScoreManager.getHighScore(options.getNumRows(), options.getNumPumpkins()));
        editor.apply();
    }


    private void saveNumGamesStarted() {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE).edit();
        editor.putInt(getString(R.string.KEY_NUM_PLAYS), highScoreManager.getNumGamesStarted());
        editor.apply();
    }

}
