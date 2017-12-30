package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 25/12/17.
 */
public class LandInfoUIAssembler {
    private static Entity generate(Space space, Entity infoUI, Entity placement, int yOff) {
        Entity text = TextBoxAssembler.assembleEntity(space, Fonts.serif, "-", new Vec4(1.0f, 1.0f, 1.0f, 1.0f), new Vec4(0.0f, 0.0f, 0.0f, 0.0f));
        TransformComponent2D textTransform = text.getComponent(TransformComponent2D.class);
        textTransform.setParent(placement);
        textTransform.scale = new Vec2(14, 14);

        TransformComponent2D placementTransform = new TransformComponent2D();
        placement.addComponent(placementTransform);
        placementTransform.setParent(infoUI);
        placementTransform.pos = new Vec2(-32, yOff);

        return text;
    }

    public static Entity assembleEntity(Space space) {
        Entity landInfoUI = space.createEntity();
        landInfoUI.addComponent(new TransformComponent2D());

        Entity waterPlacement = space.createEntity();
        Entity waterText = generate(space, landInfoUI, waterPlacement, -62);

        Entity temperaturePlacement = space.createEntity();
        Entity temperatureText = generate(space, landInfoUI, temperaturePlacement, -92);

        Entity humidityPlacement = space.createEntity();
        Entity humidityText = generate(space, landInfoUI, humidityPlacement, -120);

        Entity fertilityPlacement = space.createEntity();
        Entity fertilityText = generate(space, landInfoUI, fertilityPlacement, -150);

        Entity costPlacement = space.createEntity();
        Entity costText = generate(space, landInfoUI, costPlacement, -174);

        new LandInfoUIInput(waterText, temperatureText, humidityText, fertilityText, costText);

        return landInfoUI;
    }
}
