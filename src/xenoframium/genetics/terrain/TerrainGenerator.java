package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;

/**
 * Created by chrisjung on 21/12/17.
 */
public interface TerrainGenerator {
    Entity[][] generateTerrain(Space space, Entity map, int width, int height);
}
