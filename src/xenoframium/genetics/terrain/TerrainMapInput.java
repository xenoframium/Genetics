package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecs.event.EventListener;
import xenoframium.ecsrender.input.InputComponent;
import xenoframium.ecsrender.input.InputEventData;
import xenoframium.ecsrender.input.InputManager;
import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.glmath.quaternion.Quat;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by chrisjung on 24/12/17.
 */
public class TerrainMapInput implements EventListener {
    private static final EventID FORWARD = new EventID();
    private static final EventID BACKWARD = new EventID();
    private static final EventID LEFT = new EventID();
    private static final EventID RIGHT = new EventID();

    private static final Vec3 forwardVector;
    private static final Vec3 rightVector = new Vec3(-1, 0, 0);

    private static Map<EventID, Vec3> inputMovementMapping = new HashMap<>();

    static {
        InputManager.registerKeyBinding(GLFW_KEY_W, TerrainMapInput.FORWARD);
        InputManager.registerKeyBinding(GLFW_KEY_S, TerrainMapInput.BACKWARD);
        InputManager.registerKeyBinding(GLFW_KEY_A, TerrainMapInput.LEFT);
        InputManager.registerKeyBinding(GLFW_KEY_D, TerrainMapInput.RIGHT);

        Vec4 forward = new Vec4(0, 0, 1, 1);
        forward = new Quat(new Vec3(1, 0, 0), (float) Math.toRadians(45)).toRotMat().mult(forward);

        forwardVector = new Vec3(forward.x, forward.y, forward.z);

        inputMovementMapping.put(FORWARD, forwardVector);
        inputMovementMapping.put(BACKWARD, GLM.mult(-1, forwardVector));
        inputMovementMapping.put(RIGHT, rightVector);
        inputMovementMapping.put(LEFT, GLM.mult(-1, rightVector));
    }

    TerrainMapInput() {
        EventBus.subscribe(this, FORWARD);
        EventBus.subscribe(this, BACKWARD);
        EventBus.subscribe(this, LEFT);
        EventBus.subscribe(this, RIGHT);
    }

    InputComponent generateInputComponent() {
        return new InputComponent(new EventID[]{TerrainMapInput.FORWARD, TerrainMapInput.BACKWARD, TerrainMapInput.LEFT, TerrainMapInput.RIGHT});
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        TerrainMapComponent tmc = entity.getComponent(TerrainMapComponent.class);
        if (((InputEventData) event.data).actionType == GLFW_PRESS) {
            tmc.movement.add(inputMovementMapping.get(event.id));
        } else {
            tmc.movement.subt(inputMovementMapping.get(event.id));
        }
    }
}
