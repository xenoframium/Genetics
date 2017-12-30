package xenoframium.ecsrender.picking;

import xenoframium.ecs.Entity;
import xenoframium.glmath.linearalgebra.Vec3;

/**
 * Created by chrisjung on 23/12/17.
 */
public class PickInfo3D {
    public final Entity pickedEntity;
    public final Vec3 pickPoint;
    public final Vec3 triangleNormal;

    PickInfo3D(Entity e, Vec3 p, Vec3 n) {
        pickedEntity = e;
        pickPoint = p;
        triangleNormal = n;
    }
}
