package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.EventID;
import xenoframium.ecsrender.colour.ColouredRenderStrategy;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.input.*;
import xenoframium.genetics.model.Models;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.glmath.quaternion.Quat;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainMapAssembler {
    private static final int GRID_SIZE = 30;
    private static final TerrainMapInput tmi = new TerrainMapInput();

    public static Entity assembleEntity(Space space, TerrainGenerator generator) {
        Entity map = space.createEntity();
        TerrainMapComponent tmc = new TerrainMapComponent(space, map, generator, GRID_SIZE, GRID_SIZE);
        map.addComponent(tmc);
        map.addComponent(new TransformComponent3D());
        map.addComponent(tmi.generateInputComponent());

        for (Map.Entry<Integer, HashMap<Integer, Entity>> hm : tmc.terrainCells.entrySet()) {
            for (Map.Entry<Integer, Entity> e : hm.getValue().entrySet()) {
                TransformComponent3D comp = e.getValue().getComponent(TransformComponent3D.class);
                comp.setParent(map);
                comp.pos = new Vec3(hm.getKey(), 0, e.getKey());
            }
        }

        return map;
    }
}
