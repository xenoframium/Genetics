package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.picking.PickingComponent3D;
import xenoframium.ecsrender.texture.Texture;
import xenoframium.genetics.graphics.LandRenderStrategy;
import xenoframium.genetics.model.Models;
import xenoframium.glmath.linearalgebra.Vec4;

import java.io.File;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainLandAssembler {
    private static final Texture texture = new Texture(new File("assets/outlinedSquare32.png"));

    public static Entity assembleEntity(Space space, Entity map, int x, int y, int price, int temperature, int water, int humidity, int nutrients) {
        Entity cell = space.createEntity();
        cell.addComponent(new TerrainPropertiesComponent(map, x, y, price, humidity, water, temperature, nutrients));
        cell.addComponent(new RenderComponent3D(new LandRenderStrategy(Models.FLOOR_SQUARE, Models.getFloorSquareUvs(), texture), false));
        cell.addComponent(new TransformComponent3D());
        new TerrainInput(cell);

        return cell;
    }
}
