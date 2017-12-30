package xenoframium.genetics.terrain;

import xenoframium.ecs.*;
import xenoframium.genetics.Genetics;
import xenoframium.glmath.linearalgebra.Vec3;

import java.util.Set;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainMapSystem implements BaseSystem {
    private static final EntityFilter filter = new BasicFilter(TerrainMapComponent.class);

    private static final float MOVE_SPEED = 0.08f;
    private static final double UPDATE_INTERVAL = 0.01f;

    private final DeltaTimeAggregator dta = new DeltaTimeAggregator(UPDATE_INTERVAL);

    @Override
    public void update(Space space, double delta, double time) {
        dta.addTime(delta);
        if (!dta.shouldUpdate()) {
            return;
        }
        dta.reset();
        Set<Entity> entities = space.getEntities(filter);
        for (Entity map : entities) {
            if (!map.getComponent(TerrainMapComponent.class).isCameraMoving()) {
                continue;
            }
            Genetics.camera.move(new Vec3(map.getComponent(TerrainMapComponent.class).movement).normalize().mult(MOVE_SPEED));
        }
    }
}
