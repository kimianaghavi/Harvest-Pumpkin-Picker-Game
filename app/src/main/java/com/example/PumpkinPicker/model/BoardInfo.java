package com.example.PumpkinPicker.model;

/**
 * Object to store values and information about the game screen.
 * Information on whether it has a pumpkin(1), and whether we saw the pumpkin for the second time(2), or it does not have a pumpkin(0)
 * Information on whether it has pumpkin in it to show(-1) or no pumpkin and should show the grey button(-2)
 * Information on whether it is scanned(true) or not(false)
 * Information on the number of hidden pumpkins
 */
public class BoardInfo {

    public static int BLANK = -2;
    public static int SHOW_PUMPKIN = -1;
    public static int PUMPKIN_SHOWN_ALREADY = 2;
    public static int NO_PUMPKIN = 0;
    public static int YES_PUMPKIN = 1;

    private int pumpkin;        // potential values: 0: NO pumpkin , 1: PUMPKIN (we see for the first time), 2: PUMPKIN (we see for the second time)
    private int hiddenPumpkin;  // potential values: number of pumpkins
    private int show;           // potential values: -1: PUMPKIN_EXISTS, -2: BLANK
    private boolean isScanned;

    public BoardInfo(){
        pumpkin = 0;        // default value: 0: No Pumpkin
        hiddenPumpkin = 0;  // default value: 0         // ****** MAKE SURE ABOUT IT TOO
        show = BLANK;          // default value: -2: blank
        isScanned = false;
    }

    public int incrementHiddenPumpkin() {
        hiddenPumpkin++;
        return hiddenPumpkin;
    }

    public void decrementtHiddenPumpkin() {
        if (hiddenPumpkin > 0) {
            hiddenPumpkin--;
        }
    }

    public int getPumpkin() {
        return pumpkin;
    }

    public void setPumpkin(int pumpkin) {
        this.pumpkin = pumpkin;
    }

    public int getHiddenPumpkin() {
        return hiddenPumpkin;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setIsScanned(boolean scanned) {
        isScanned = scanned;
    }
}
