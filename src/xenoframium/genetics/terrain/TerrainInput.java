package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecs.event.EventListener;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.picking.PickingSystem3D;
import xenoframium.genetics.graphics.LandRenderStrategy;
import xenoframium.genetics.gui.TextBoxComponent;
import xenoframium.genetics.plant.PlantEvents;

/**
 * Created by chrisjung on 25/12/17.
 */
public class TerrainInput implements EventListener {
    private Entity entity;

    private final EventID terrainSelect = new EventID();
    private final EventID terrainDeselect = new EventID();

    TerrainInput(Entity entity) {
        EventBus.subscribe(this, PlantEvents.PLANT_SELECTION);
        EventBus.subscribe(this, PlantEvents.PLANT_DESELECTION);
        EventBus.subscribe(this, terrainSelect);
        EventBus.subscribe(this, terrainDeselect);
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        if (!entity.hasComponent(TerrainPropertiesComponent.class)) {
            return;
        }

        if (event.id == TerrainEvents.TERRAIN_SELECTION) {
            LandRenderStrategy lrs = (LandRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy;
            lrs.setBrightness(lrs.getBrightness() + 0.2f);
            return;
        }
        if (event.id == TerrainEvents.TERRAIN_DESELECTION) {
            LandRenderStrategy lrs = (LandRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy;
            lrs.setBrightness(lrs.getBrightness() - 0.2f);
            return;
        }
    }
}
