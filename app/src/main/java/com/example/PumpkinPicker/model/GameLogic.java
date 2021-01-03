package com.example.PumpkinPicker.model;

import java.util.Random;

/**
 * Create the basic 2D array, by getting number of row and col from the Option screen, and fill numPumpkins of its random positions by 1(pumpkin)
 * 2D array store information mentioned on the BoardInfo.
 * Some information of the BoardInfo object will be updated after clicking a dynamic button.
 */
public class GameLogic {

    private int numPumpkinsFound = 0;

    private BoardInfo[][] allInfoArray;

    private int row;
    private int col;

    private int scansUsed = 0;

    // After user selection, build the game (Build an 2D array and fill it randomly with specific number of pumpkins)
    public GameLogic() {

        row = Options.getInstance().getNumRows();
        col = Options.getInstance().getNumCols();
        int numPumpkins = Options.getInstance().getNumPumpkins();

        allInfoArray = new BoardInfo[row][col];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                allInfoArray[i][j] = new BoardInfo();   // It means that we have not clicked it,and an gray button should show up
            }
        }

        //https://stackoverflow.com/questions/35119330/putting-a-number-at-random-spots-in-2d-array
        int filledSpots = 0;
        Random random = new Random();

        while(filledSpots < numPumpkins){
            int i = random.nextInt(row);
            int j = random.nextInt(col);
            if (allInfoArray[i][j].getPumpkin() == BoardInfo.NO_PUMPKIN){
                allInfoArray[i][j].setPumpkin(BoardInfo.YES_PUMPKIN);
                filledSpots++;

                // Update number of hidden pumpkins in the array
                // Loop over cols
                for (int k = 0; k < col; k++){
                    if (k != j){
                        allInfoArray[i][k].incrementHiddenPumpkin();
                    }
                }
                // Loop over rows
                for (int l = 0; l < row; l++){
                    if (l != i){
                        allInfoArray[l][j].incrementHiddenPumpkin();
                    }
                }
            }
        }
    }

    // AFTER USER CLICK ON ANY OF THE DYNAMIC BUTTONS
    public BoardInfo[][] afterClick(int selectedRow, int selectedCol){

        // If the button user clicked is a pumpkin (for the first time)
        if (allInfoArray[selectedRow][selectedCol].getPumpkin() == BoardInfo.YES_PUMPKIN){
            numPumpkinsFound++;
            allInfoArray[selectedRow][selectedCol].setShow(BoardInfo.SHOW_PUMPKIN);    // Show pumpkin!

            // Flag to show we seen this pumpkin
            allInfoArray[selectedRow][selectedCol].setPumpkin(BoardInfo.PUMPKIN_SHOWN_ALREADY);
            // Update number of hidden pumpkins in the array
            // Loop over cols
            for (int k = 0; k < col; k++){
                if (k != selectedCol){
                    // Since this value is returned with allInfoArray,
                    // no need to put the value in show var anymore - use hidden val in UI
                    allInfoArray[selectedRow][k].decrementtHiddenPumpkin();
                }
            }
            // Loop over rows
            for (int l = 0; l < row; l++){
                if (l != selectedRow){
                    // See comment above
                    allInfoArray[l][selectedCol].decrementtHiddenPumpkin();
                }
            }

        }

        // If the button user clicked is not a pumpkin or If the button user clicked is a pumpkin (for the second time)
        else{
            if (!allInfoArray[selectedRow][selectedCol].isScanned()) {
                scansUsed++;
            }
            allInfoArray[selectedRow][selectedCol].setIsScanned(true);
        }

        return allInfoArray;
    }

    public boolean isAllPumpkinsFound() {
        return numPumpkinsFound == Options.getInstance().getNumPumpkins();
    }

    public int getNumPumpkinsFound() {
        return numPumpkinsFound;
    }

    public int getNumScansUsed() {
        return scansUsed;
    }

}

