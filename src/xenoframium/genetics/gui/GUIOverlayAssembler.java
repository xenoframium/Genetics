package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.EventID;
import xenoframium.ecsrender.colour.ColouredRenderStrategy;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.picking.PickingComponent2D;
import xenoframium.ecsrender.texture.Texture;
import xenoframium.ecsrender.texture.TexturedRenderStrategy;
import xenoframium.genetics.model.Models;
import xenoframium.genetics.selection.SelectionComponent;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

import java.io.File;

/**
 * Created by chrisjung on 25/12/17.
 */
public class GUIOverlayAssembler {
    private static final Texture guiTexture = new Texture(new File("assets/TerrainGUI.png"));

    public static Entity assembleEntity(Space space) {
        Entity guiOverlay = space.createEntity();
        guiOverlay.addComponent(new RenderComponent2D(new TexturedRenderStrategy(Models.SQUARE, Models.getSquareUvs(), guiTexture), true));
        TransformComponent2D tc = new TransformComponent2D();
        tc.scale = new Vec2(800, 600);
        guiOverlay.addComponent(tc);

        Entity leftSqPlacement = space.createEntity();
        Entity leftSq = space.createEntity();
        TransformComponent2D leftSqPT = new TransformComponent2D();
        leftSqPlacement.addComponent(leftSqPT);
        leftSqPT.pos = new Vec2(400, -300);
        leftSqPT.z = 2.0f;

        TransformComponent2D leftSqT = new TransformComponent2D();
        leftSq.addComponent(leftSqT);
        leftSqT.scale = new Vec2(114, 433);
        leftSqT.setParent(leftSqPlacement);
        leftSq.addComponent(new PickingComponent2D(Models.SQUARE));
        leftSq.addComponent(new SelectionComponent());

        Entity rightSqPlacement = space.createEntity();
        Entity rightSq = space.createEntity();
        TransformComponent2D rightSqPT = new TransformComponent2D();
        rightSqPlacement.addComponent(rightSqPT);
        rightSqPT.pos = new Vec2(286, -300);

        TransformComponent2D rightSqT = new TransformComponent2D();
        rightSq.addComponent(rightSqT);
        rightSqT.scale = new Vec2(686, 92);
        rightSqT.z = 2.0f;
        rightSqT.setParent(rightSqPlacement);
        rightSq.addComponent(new PickingComponent2D(Models.SQUARE));
        rightSq.addComponent(new SelectionComponent());

        return guiOverlay;
    }
}
