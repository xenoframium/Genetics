package xenoframium.genetics.plant;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.*;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.input.InputComponent;
import xenoframium.ecsrender.input.InputEventData;
import xenoframium.ecsrender.input.InputManager;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.Systems;
import xenoframium.genetics.gui.SelectionMode;
import xenoframium.genetics.graphics.TreeRenderStrategy;
import xenoframium.genetics.selection.SelectionComponent;
import xenoframium.genetics.selection.SelectionEvents;
import xenoframium.genetics.selection.SelectionSystem;
import xenoframium.genetics.terrain.TerrainMapComponent;
import xenoframium.genetics.terrain.TerrainMapEvents;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;
import xenoframium.glmath.linearalgebra.Vec4;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by chrisjung on 24/12/17.
 */
public class PlantInput implements EventListener {
    private static final EventID CLICK_EVENT = new EventID();
    private static final EventID RIGHT_CLICK_EVENT = new EventID();

    static {
        InputManager.registerMouseButtonBinding(GLFW_MOUSE_BUTTON_LEFT, CLICK_EVENT);
        InputManager.registerMouseButtonBinding(GLFW_MOUSE_BUTTON_RIGHT, RIGHT_CLICK_EVENT);
    }

    PlantInput() {
        EventBus.subscribe(this, CLICK_EVENT);
        EventBus.subscribe(this, RIGHT_CLICK_EVENT);
        EventBus.subscribe(this, SelectionEvents.SELECTION_EVENT);
        EventBus.subscribe(this, SelectionEvents.DESELECTION_EVENT);
    }

    InputComponent getInputComponent() {
        return new InputComponent(new EventID[]{CLICK_EVENT, RIGHT_CLICK_EVENT});
    }

    private void buyTile(Space space, Entity entity) {
        if (!entity.getComponent(SelectionComponent.class).isSelected()) {
            return;
        }
        Entity land = entity.getComponent(TerrainComponent.class).land;
        if (!land.getComponent(TerrainPropertiesComponent.class).canBuy()) {
            return;
        }
        int price = land.getComponent(TerrainPropertiesComponent.class).price;
        if (Genetics.player.hasMoney(price) && !land.getComponent(TerrainPropertiesComponent.class).isOwned()) {
            land.getComponent(TerrainPropertiesComponent.class).setOwned();
            ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy).setBrightness(1.1f);
            Genetics.player.removeMoney(price);
            Entity map = entity.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class).map;
            map.getComponent(TerrainMapComponent.class).incrementLandOwned();
            EventBus.post(new Event(TerrainMapEvents.LAND_BOUGHT_EVENT, new NullEventData()), space, map);
        }
    }

    private void onClick(Space space, Entity entity) {
        switch (SelectionMode.getCurrentMode()) {
            case PURCHASE:
                buyTile(space, entity);
                break;
            default:
                EventBus.post(new Event(PlantEvents.PLANT_CLICK), space, entity);
                break;
        }
    }

    private void toggleHarvest(Entity entity) {
        if (!entity.getComponent(SelectionComponent.class).isSelected()) {
            return;
        }
        if (!entity.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class).isOwned()) {
            return;
        }
        boolean shouldAuto = !entity.getComponent(PlantPropertiesComponent.class).shouldAutoHarvest;
        entity.getComponent(PlantPropertiesComponent.class).shouldAutoHarvest = shouldAuto;
        if (!shouldAuto) {
            ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy).setColour(new Vec4(entity.getComponent(PlantPropertiesComponent.class).getColour(), 1));
        } else {
            ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy).setColour(new Vec4(entity.getComponent(PlantPropertiesComponent.class).getColour(), 1.0f));
        }
    }

    private void onPurchaseSelect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setBrightness(strat.getBrightness()+0.2f);
        entity.getComponent(PlantPropertiesComponent.class).isSelected = true;
    }

    private void onChooseSelect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setColour(new Vec4(255/255f, 188/255f, 75/255f, 1));
    }

    private void onPlaceSelect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setColour(new Vec4(87/255f, 252/255f, 255/255f, 1));
    }

    private void onSelect(Space space, Entity entity) {
        EventBus.post(new Event(PlantEvents.PLANT_SELECTION, new NullEventData()), space, entity);
        switch (SelectionMode.getCurrentMode()) {
            case PURCHASE:
                onPurchaseSelect(entity);
                break;
            case CHOOSE:
                onChooseSelect(entity);
                break;
            case PLACE:
                onPlaceSelect(entity);
                break;
        }
    }

    private void onPurchaseDeselect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setColour(new Vec4(entity.getComponent(PlantPropertiesComponent.class).getColour(), 1.0f));
        if (entity.getComponent(PlantPropertiesComponent.class).isSelected) {
            strat.setBrightness(strat.getBrightness() - 0.2f);
            entity.getComponent(PlantPropertiesComponent.class).isSelected = false;
        }
    }

    private void onChooseDeselect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setColour(new Vec4(entity.getComponent(PlantPropertiesComponent.class).getColour(), 1));
    }

    private void onPlaceDeselect(Entity entity) {
        TreeRenderStrategy strat = ((TreeRenderStrategy) entity.getComponent(RenderComponent3D.class).strategy);
        strat.setColour(new Vec4(entity.getComponent(PlantPropertiesComponent.class).getColour(), 1));
    }

    private void onDeselect(Space space, Entity entity) {
        EventBus.post(new Event(PlantEvents.PLANT_DESELECTION, new NullEventData()), space, entity.getComponent(TerrainComponent.class).land);
        switch (SelectionMode.getCurrentMode()) {
            case PURCHASE:
                onPurchaseDeselect(entity);
                break;
            case CHOOSE:
                onChooseDeselect(entity);
                break;
            case PLACE:
                onPlaceDeselect(entity);
                break;
        }
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        if (!entity.hasComponent(PlantPropertiesComponent.class)) {
            return;
        }
        if (event.id == SelectionEvents.SELECTION_EVENT) {
            onSelect(space, entity);
            return;
        }
        if (event.id == SelectionEvents.DESELECTION_EVENT) {
            onDeselect(space, entity);
            return;
        }
        if (Systems.selectionSystem.getSelectedEntity() != entity) {
            return;
        }
        InputEventData data = (InputEventData) event.data;
        if (data.actionType == GLFW_PRESS) {
            if (event.id == CLICK_EVENT) {
                onClick(space, entity);
            } else if (event.id == RIGHT_CLICK_EVENT) {
                toggleHarvest(entity);
            }
        }
    }
}
