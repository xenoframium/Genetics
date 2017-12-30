package xenoframium.ecs.event;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;

/**
 * Created by chrisjung on 24/12/17.
 */
public interface EventListener {
    void onEvent(Event event, Space space, Entity entity);
}
