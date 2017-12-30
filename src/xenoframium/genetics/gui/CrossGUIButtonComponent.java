package xenoframium.genetics.gui;

import xenoframium.ecs.Component;

/**
 * Created by chrisjung on 26/12/17.
 */
public class CrossGUIButtonComponent implements Component {
    final CrossGUIButtonCallback buttonCallback;

    public CrossGUIButtonComponent(CrossGUIButtonCallback buttonCallback) {
        this.buttonCallback = buttonCallback;
    }
}
