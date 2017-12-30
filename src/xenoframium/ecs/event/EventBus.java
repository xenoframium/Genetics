package xenoframium.ecs.event;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by chrisjung on 24/12/17.
 */
public class EventBus {
    private static Map<EventID, HashSet<EventListener>> eventListeners = new HashMap<>();
    private static Map<GlobalEventID, HashSet<GlobalEventListener>> globalEventListeners = new HashMap<>();

    public static class EventNotFoundException extends RuntimeException {}

    public static void subscribe(EventListener listener, EventID eventID) {
        if (!eventListeners.containsKey(eventID)) {
            eventListeners.put(eventID, new HashSet<>());
        }
        eventListeners.get(eventID).add(listener);
    }

    public static void unsubscribe(EventListener listener, EventID eventID) {
        if (!eventListeners.containsKey(eventID)) {
            throw new EventNotFoundException();
        }
        eventListeners.get(eventID).remove(listener);
    }

    public static void subscribe(GlobalEventListener listener, GlobalEventID id) {
        if (!globalEventListeners.containsKey(id)) {
            globalEventListeners.put(id, new HashSet<>());
        }
        globalEventListeners.get(id).add(listener);
    }

    public static void unsubscribe(GlobalEventListener listener, EventID eventID) {
        if (!globalEventListeners.containsKey(eventID)) {
            throw new EventNotFoundException();
        }
        globalEventListeners.get(eventID).remove(listener);
    }

    public static void post(Event event, Space space, Entity entity) {
        if (!eventListeners.containsKey(event.id)) {
            return;
        }
        for (EventListener listener : eventListeners.get(event.id)) {
            listener.onEvent(event, space, entity);
        }
    }

    public static void post(GlobalEvent event) {
        if (!globalEventListeners.containsKey(event.id)) {
            return;
        }
        for (GlobalEventListener listener : globalEventListeners.get(event.id)) {
            listener.onEvent(event);
        }
    }
}
