package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;

/**
 * Created by chrisjung on 26/12/17.
 */
public interface CrossGUIButtonCallback {
    void onClick(Entity entity);
    void onPlantSelect(Entity entity);
}
