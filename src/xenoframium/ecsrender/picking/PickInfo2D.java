package xenoframium.ecsrender.picking;

import xenoframium.ecs.Entity;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec3;

/**
 * Created by chrisjung on 23/12/17.
 */
public class PickInfo2D {
    public final Entity pickedEntity;
    public final Vec2 pickPoint;

    PickInfo2D(Entity e, Vec2 p) {
        pickedEntity = e;
        pickPoint = p;
    }
}
