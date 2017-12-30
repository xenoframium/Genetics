package xenoframium.ecsrender.input;

import xenoframium.ecs.Component;
import xenoframium.ecs.event.EventID;

import java.util.*;

/**
 * Created by chrisjung on 18/12/17.
 */
public class InputComponent implements Component {
    Map<EventID, Boolean> isActive;
    Set<EventID> actions;

    public InputComponent(EventID[] subscribedEvents) {
        this.actions = new HashSet<>(Arrays.asList(subscribedEvents));
        isActive = new HashMap<>();
        for (EventID eventID : subscribedEvents) {
            isActive.put(eventID, false);
        }
    }

    public void subscribe(EventID eventID) {
        actions.add(eventID);
        isActive.put(eventID, false);
    }

    public void removeListener(EventID eventID) {
        actions.remove(eventID);
        isActive.remove(eventID);
    }
}
