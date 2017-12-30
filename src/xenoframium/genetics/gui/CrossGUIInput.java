package xenoframium.genetics.gui;

import org.lwjgl.glfw.GLFW;
import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecs.event.EventListener;
import xenoframium.ecsrender.colour.ColouredRenderStrategy;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.input.InputComponent;
import xenoframium.ecsrender.input.InputEventData;
import xenoframium.ecsrender.input.InputManager;
import xenoframium.genetics.Systems;
import xenoframium.genetics.plant.PlantEvents;
import xenoframium.genetics.plant.PlantPropertiesComponent;
import xenoframium.genetics.selection.SelectionComponent;
import xenoframium.genetics.selection.SelectionEvents;
import xenoframium.glmath.linearalgebra.Vec4;

import java.awt.event.InputEvent;

/**
 * Created by chrisjung on 26/12/17.
 */
public class CrossGUIInput implements EventListener {
    private static final EventID CLICK_EVENT = new EventID();
    static Entity button;
    static Entity plant1;
    static Entity plant2;
    static PlantPropertiesComponent newPlantProperties;

    static {
        InputManager.registerMouseButtonBinding(GLFW.GLFW_MOUSE_BUTTON_LEFT, CLICK_EVENT);
    }

    CrossGUIInput() {
        EventBus.subscribe(this, CLICK_EVENT);
        EventBus.subscribe(this, SelectionEvents.SELECTION_EVENT);
        EventBus.subscribe(this, SelectionEvents.DESELECTION_EVENT);
        EventBus.subscribe(this, PlantEvents.PLANT_CLICK);
    }

    InputComponent getInputComponent() {
        return new InputComponent(new EventID[]{CLICK_EVENT});
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        if (event.id == CLICK_EVENT && ((InputEventData) event.data).actionType == GLFW.GLFW_PRESS && (Systems.selectionSystem.getSelectedEntity() == null || !Systems.selectionSystem.getSelectedEntity().hasComponent(CrossGUIButtonComponent.class))) {
            if (Systems.selectionSystem.getSelectedEntity() != null && Systems.selectionSystem.getSelectedEntity().hasComponent(PlantPropertiesComponent.class)) {
                return;
            }
            SelectionMode.currentMode = SelectionMode.Mode.PURCHASE;
            return;
        }
        if (event.id == PlantEvents.PLANT_CLICK) {
            SelectionMode.currentMode = SelectionMode.Mode.PURCHASE;
            button.getComponent(CrossGUIButtonComponent.class).buttonCallback.onPlantSelect(entity);
            return;
        }
        if (!entity.hasComponent(CrossGUIButtonComponent.class)) {
            return;
        }
        if (event.id == SelectionEvents.SELECTION_EVENT) {
            ((ColouredRenderStrategy) entity.getComponent(RenderComponent2D.class).strategy).setColour(new Vec4(1.0f, 1.0f, 1.0f, 0.5f));
            return;
        }
        if (event.id == SelectionEvents.DESELECTION_EVENT) {
            ((ColouredRenderStrategy) entity.getComponent(RenderComponent2D.class).strategy).setColour(new Vec4(1.0f, 1.0f, 1.0f, 0.0f));
            return;
        }
        if (((InputEventData) event.data).actionType == GLFW.GLFW_PRESS) {
            if (Systems.selectionSystem.getSelectedEntity() != entity) {
                return;
            }
            entity.getComponent(CrossGUIButtonComponent.class).buttonCallback.onClick(entity);
        }
    }
}
