package com.example.PumpkinPicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.PumpkinPicker.model.HighScoreManager;
import com.example.PumpkinPicker.model.Options;

import java.util.ArrayList;

/*
    This activity allows user to select the size of the board they want to play on and how many
    pumpkins will appear. The activity displays two drop down menus which hold potential values
    for each configuration. This class also saves the configuration to shared preferences so the
    options will stay consistent between launches.
 */
public class OptionsActivity extends AppCompatActivity {

    private Options options = Options.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        initSpinners();

        Button btnResetScores = findViewById(R.id.btn_reset_scores);
        btnResetScores.setOnClickListener(v -> {
            HighScoreManager.getInstance().reset(OptionsActivity.this);
        });


    }

    public static Intent makeLaunchIntent(Context context) {
        return (new Intent(context, OptionsActivity.class));
    }

    private void initSpinners() {
        Spinner spBoardSize = findViewById(R.id.sp_board_sizes);
        Spinner spNumPumpkins = findViewById(R.id.sp_num_pumpkins);

        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<>(OptionsActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.boardSizes));
        spBoardSize.setAdapter(boardSizeAdapter);
        spBoardSize.setSelection(getSelBoardSizePosition());

        ArrayAdapter<String> numPumpkinsAdapter = new ArrayAdapter<>(OptionsActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.numPumpkinOptions));
        spNumPumpkins.setAdapter(numPumpkinsAdapter);
        spNumPumpkins.setSelection(getSelNumPumpkinPosition());

        spBoardSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveBoardSize(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNumPumpkins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveNumPumpkins(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getSelNumPumpkinPosition() {
        switch (options.getNumPumpkins()) {
            case(6):
                return 0;
            case(10):
                return 1;
            case(15):
                return 2;
            case(20):
                return 3;
        }
        return 0;
    }

    private int getSelBoardSizePosition() {
        switch (options.getNumRows()) {
            case(4):
                return 0;
            case(5):
                return 1;
            case(6):
                return 2;
        }
        return 0;
    }

    private void saveBoardSize(int position) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE).edit();

        int row;
        int col;

        switch (position) {
            case(2):
                row = 6;
                col = 15;
                break;
            case(1):
                row = 5;
                col = 10;
                break;
            default:
                row = 4;
                col = 6;
                break;
        }

        options.setNumRows(row);
        options.setNumCols(col);
        editor.putInt(getString(R.string.KEY_ROW_SIZE), row);
        editor.putInt(getString(R.string.KEY_COL_SIZE), col);
        editor.apply();
    }

    private void saveNumPumpkins(int position) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.KEY_MY_PREFS), MODE_PRIVATE).edit();

        int numPumpkins;
        switch (position) {
            case(3):
                numPumpkins = 20;
                break;
            case(2):
                numPumpkins = 15;
                break;
            case(1):
                numPumpkins = 10;
                break;
            default:
                numPumpkins = 6;
                break;
        }

        editor.putInt(getString(R.string.KEY_NUM_PUMPKINS), numPumpkins);
        options.setNumPumpkins(numPumpkins);

        editor.apply();
    }


}
