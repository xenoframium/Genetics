package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.genetics.terrain.TerrainInput;
import xenoframium.genetics.terrain.TerrainMapComponent;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 25/12/17.
 */
public class LandUIAssembler {
    public static Entity assembleEntity(Space space, Entity map) {
        Entity landUI = TextBoxAssembler.assembleEntity(space, Fonts.serif, String.format("%,d", map.getComponent(TerrainMapComponent.class).getLandOwnedCount()), new Vec4(1.0f, 1.0f, 1.0f, 1.0f), new Vec4(0.0f, 0.0f, 0.0f, 0.0f));

        landUI.getComponent(TransformComponent2D.class).scale = new Vec2(14, 14);

        new LandUIInput(landUI, map);

        return landUI;
    }
}
