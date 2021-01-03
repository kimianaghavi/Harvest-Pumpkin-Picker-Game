package com.example.PumpkinPicker.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.PumpkinPicker.R;

/*
    This class is to store the high scores for every possible board configuration
    It saves each high score in a 2D array which stores the high score of each configuration
    in each cell.
    The class is a singleton to maintain consistency throughout the app
*/
public class HighScoreManager {

    private static int NUM_ROW_OPTIONS = 3;
    private static int NUM_PUMPKIN_OPTIONS = 4;

    private static int[] rows = {4, 5, 6};
    private static int[] pumpkins = {6, 10, 15, 20};

    private int[][] highScores;
    private int numGamesStarted;

    private static HighScoreManager instance;


    //Singleton creation of HighScoreManager
    private HighScoreManager() {
        // private constructor so no one can access
        reset(null);
    }

    public static HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    public void setHighScore(int row, int numPumpkins, int scoreVal) {
        highScores[getRowIndex(row)][getPumpkinIndex(numPumpkins)] =
                min(highScores[getRowIndex(row)][getPumpkinIndex(numPumpkins)], scoreVal);
    }

    public int getHighScore(int row, int numPumpkins) {
        return highScores[getRowIndex(row)][getPumpkinIndex(numPumpkins)];
    }

    public void reset(Context context) {

        highScores = new int[NUM_ROW_OPTIONS][NUM_PUMPKIN_OPTIONS];
        numGamesStarted = 0;

        // initialize all high scores as -1
        for (int row = 0; row < NUM_ROW_OPTIONS; row++) {
            for (int pumpkin = 0; pumpkin < NUM_PUMPKIN_OPTIONS; pumpkin++) {
                highScores[row][pumpkin] = -1;
            }
        }

        if (context != null) {
            resetPreferences(context);
        }
    }

    public int getNumGamesStarted() {
        return numGamesStarted;
    }

    public void setNumGamesStarted(int numPlays) {
        numGamesStarted = numPlays;
    }

    public void incrGamesStarted() {
        numGamesStarted++;
    }

    private int min(int oldScore, int newScore) {
        if (oldScore > 0 && oldScore < newScore) {
            return oldScore;
        }
        return newScore;
    }

    private void resetPreferences(Context context) {

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.KEY_MY_PREFS), context.MODE_PRIVATE).edit();
        editor.putInt(context.getString(R.string.KEY_NUM_PLAYS), 0);

        for (int row : rows) {
            for (int pumpkin : pumpkins) {
                String key = context.getString((R.string.editable_key), row, pumpkin);
                editor.putInt(key, -1);
                editor.apply();
            }
        }
    }

    private int getRowIndex(int row) {
        for (int i = 0; i < NUM_ROW_OPTIONS; i++) {
            if (rows[i] == row) {
                return i;
            }
        }
        return 0;
    }

    private int getPumpkinIndex(int pumpkin) {
        for (int i = 0; i < NUM_PUMPKIN_OPTIONS; i++) {
            if (pumpkins[i] == pumpkin) {
                return i;
            }
        }
        return 0;
    }

}
