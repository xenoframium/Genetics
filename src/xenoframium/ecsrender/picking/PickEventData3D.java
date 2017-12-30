package xenoframium.ecsrender.picking;

import xenoframium.ecs.event.EventData;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec3;

/**
 * Created by chrisjung on 24/12/17.
 */
public class PickEventData3D implements EventData {
    private final Vec3 pickPoint;
    private final Vec3 normal;

    public PickEventData3D(Vec3 pickPoint, Vec3 normal) {
        this.pickPoint = new Vec3(pickPoint);
        this.normal = new Vec3(normal);
    }

    public Vec3 getPickPoint() {
        return new Vec3(pickPoint);
    }

    public Vec3 getNormal() {
        return new Vec3(normal);
    }
}
