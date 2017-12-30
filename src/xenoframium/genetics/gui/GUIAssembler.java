package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.glmath.linearalgebra.Vec2;

/**
 * Created by chrisjung on 25/12/17.
 */
public class GUIAssembler {
    public static Entity assembleEntity(Space space, Entity map) {

        Entity gui = space.createEntity();
        gui.addComponent(new TransformComponent2D());

        Entity guiOverlayPlacement = space.createEntity();
        Entity guiOverlay = GUIOverlayAssembler.assembleEntity(space);
        guiOverlay.getComponent(TransformComponent2D.class).setParent(guiOverlayPlacement);

        TransformComponent2D overlayTransform = new TransformComponent2D();
        guiOverlayPlacement.addComponent(overlayTransform);
        overlayTransform.pos = new Vec2(400, -300);
        overlayTransform.setParent(gui);
        overlayTransform.z = 1.0f;

        Entity moneyTextPlacement = space.createEntity();
        Entity moneyText = MoneyUIAssembler.assembleEntity(space);
        moneyText.getComponent(TransformComponent2D.class).setParent(moneyTextPlacement);

        TransformComponent2D moneyTextTransform = new TransformComponent2D();
        moneyTextPlacement.addComponent(moneyTextTransform);
        moneyTextTransform.pos = new Vec2(238, -244);
        moneyTextTransform.setParent(gui);

        Entity landTextPlacement = space.createEntity();
        Entity landText = LandUIAssembler.assembleEntity(space, map);
        landText.getComponent(TransformComponent2D.class).setParent(landTextPlacement);

        TransformComponent2D landTextTransform = new TransformComponent2D();
        landTextPlacement.addComponent(landTextTransform);
        landTextTransform.pos = new Vec2(238, -282);
        landTextTransform.setParent(gui);

        Entity landInfoUIPlacement = space.createEntity();
        Entity landInfoUI = LandInfoUIAssembler.assembleEntity(space);
        landInfoUI.getComponent(TransformComponent2D.class).setParent(landInfoUIPlacement);

        TransformComponent2D landInfoUIPlacementTransform = new TransformComponent2D();
        landInfoUIPlacement.addComponent(landInfoUIPlacementTransform);
        landInfoUIPlacementTransform.pos = new Vec2(400, -112);
        landInfoUIPlacementTransform.setParent(gui);

        Entity plantInfoUIPlacement = space.createEntity();
        Entity plantInfoUI = PlantInfoUIAssembler.assembleEntity(space);
        plantInfoUI.getComponent(TransformComponent2D.class).setParent(plantInfoUIPlacement);

        TransformComponent2D plantInfoUIPlacementTransform= new TransformComponent2D();
        plantInfoUIPlacement.addComponent(plantInfoUIPlacementTransform);
        plantInfoUIPlacementTransform.pos = new Vec2(400, 130);
        plantInfoUIPlacementTransform.setParent(gui);

        Entity crossGUI = CrossGUIAssembler.assembleEntity(space);

        return gui;
    }
}
