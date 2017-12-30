package xenoframium.genetics.gui;

/**
 * Created by chrisjung on 26/12/17.
 */
public class SelectionMode {
    public static enum Mode {PURCHASE, CHOOSE, PLACE}

    static Mode currentMode = Mode.PURCHASE;

    public static Mode getCurrentMode() {
        return currentMode;
    }
}
