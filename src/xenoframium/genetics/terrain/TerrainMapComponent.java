package xenoframium.genetics.terrain;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityManager;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecs.event.NullEventData;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.graphics.TreeRenderStrategy;
import xenoframium.genetics.plant.TerrainComponent;
import xenoframium.glmath.linearalgebra.Vec3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainMapComponent implements Component {
    private static float EPSILON = 1e-6f;
    private static final int[] xOff = new int[]{-1, 0, 1, 0};
    private static final int[] yOff = new int[]{0, -1, 0, 1};

    private int landOwnedCount = 9;

    Vec3 movement = new Vec3(0, 0, 0);

    Map<Integer, HashMap<Integer, Entity>> terrainCells = new HashMap<>();

    TerrainMapComponent(Space space, Entity map, TerrainGenerator generator, int width, int height) {
        Entity[][] terrainCellsArr = generator.generateTerrain(space, map, width, height);
        for (int i = 0; i < terrainCellsArr.length; i++) {
            terrainCells.put(i-terrainCellsArr.length/2, new HashMap<>());
            for (int j = 0; j < terrainCellsArr[0].length; j++) {
                terrainCells.get(i-terrainCellsArr.length/2).put(j-terrainCellsArr.length/2, terrainCellsArr[i][j]);
            }
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Entity land = terrainCells.get(i).get(j);
                land.getComponent(TerrainPropertiesComponent.class).setOwned();
                Object o = (land.getComponent(TerrainPropertiesComponent.class).contents.getComponent(RenderComponent3D.class));
                ((TreeRenderStrategy) land.getComponent(TerrainPropertiesComponent.class).contents.getComponent(RenderComponent3D.class).strategy).setBrightness(1.1f);
            }
        }
    }

    private TerrainPropertiesComponent get(int x, int y) {
        return terrainCells.get(x) == null ? null : terrainCells.get(x).get(y) == null ? null : terrainCells.get(x).get(y).getComponent(TerrainPropertiesComponent.class);
    }

    public boolean canBuy(TerrainPropertiesComponent tlc) {
        int x = tlc.x;
        int y = tlc.y;
        for (int i = 0; i < 4; i++) {
            if (get(x+xOff[i], y+yOff[i]) != null && get(x+xOff[i], y+yOff[i]).isOwned()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCameraMoving() {
        return movement.mag() > EPSILON;
    }

    public int getLandOwnedCount() {
        return landOwnedCount;
    }

    public void incrementLandOwned() {
        landOwnedCount++;
    }
}
