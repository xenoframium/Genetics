package xenoframium.ecsrender.input;

import org.lwjgl.glfw.GLFW;
import xenoframium.ecs.*;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;

import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by chrisjung on 18/12/17.
 */
public class InputSystem implements BaseSystem {
    private static final EntityFilter inputFilter = new BasicFilter(InputComponent.class);

    @Override
    public void update(Space space, double delta, double time) {
        GLFW.glfwPollEvents();
        Set<Entity> entities = space.getEntities(inputFilter);
        for (Entity entity : entities) {
            InputComponent component = entity.getComponent(InputComponent.class);
            for (EventID eventID: component.actions) {
                boolean status = InputManager.isEventActive(eventID);
                if (status == true && !component.isActive.get(eventID)) {
                    EventBus.post(new Event(eventID, new InputEventData(GLFW_PRESS)), space, entity);
                    component.isActive.put(eventID, true);
                } else if (status == false && component.isActive.get(eventID)) {
                    EventBus.post(new Event(eventID, new InputEventData(GLFW_RELEASE)), space, entity);
                    component.isActive.put(eventID, false);
                }
            }
        }
    }

    public void clearInput(Space space) {
        Set<Entity> entities = space.getEntities(inputFilter);
        for (Entity entity : entities) {
            InputComponent component = entity.getComponent(InputComponent.class);
            for (EventID eventID : component.actions) {
                if (component.isActive.get(eventID)) {
                    EventBus.post(new Event(eventID, new InputEventData(GLFW_RELEASE)), space, entity);
                    component.isActive.put(eventID, false);
                }
            }
        }
    }
}
