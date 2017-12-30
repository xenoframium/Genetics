package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.*;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.player.Player;
import xenoframium.genetics.terrain.TerrainMapComponent;
import xenoframium.genetics.terrain.TerrainMapEvents;

/**
 * Created by chrisjung on 25/12/17.
 */
public class LandUIInput implements EventListener {
    private final Entity textBox;
    private final Entity map;

    LandUIInput(Entity textBox, Entity map) {
        this.map = map;
        this.textBox = textBox;
        EventBus.subscribe(this, TerrainMapEvents.LAND_BOUGHT_EVENT);
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        textBox.getComponent(TextBoxComponent.class).setText(String.format("%,d", map.getComponent(TerrainMapComponent.class).getLandOwnedCount()));
    }
}
