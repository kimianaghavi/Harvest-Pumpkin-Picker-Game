package com.example.PumpkinPicker.model;

/*
    This class is a singleton class to maintain the board size and number of pumpkins chosen by the user
    By default the values are the min values for board size and number of pumpkins.
    This is a singleton to maintain consistency throughout the app
 */

public class Options {

    private static Options instance;

    // Options variables
    private int numRows;
    private int numCols;
    private int numPumpkins;

    //Singleton creation of Options
    private Options() {
        // private constructor so no one but function getInstance() can access
        numRows = 4;
        numCols = 6;
        numPumpkins = 6;
    }

    public static Options getInstance() {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumPumpkins() {
        return numPumpkins;
    }

    public void setNumPumpkins(int numPumpkins) {
        this.numPumpkins = numPumpkins;
    }
}
