package xenoframium.genetics.selection;

import xenoframium.ecs.Component;

/**
 * Created by chrisjung on 23/12/17.
 */
public class SelectionComponent implements Component {
    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }
}
