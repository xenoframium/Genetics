package xenoframium.ecsrender.picking;

import xenoframium.ecs.event.EventData;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec3;

/**
 * Created by chrisjung on 24/12/17.
 */
public class PickEventData2D implements EventData {
    private final Vec2 pickPoint;

    public PickEventData2D(Vec2 pickPoint) {
        this.pickPoint = new Vec2(pickPoint);
    }

    public Vec2 getPickPoint() {
        return new Vec2(pickPoint);
    }
}
