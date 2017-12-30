package xenoframium.ecsrender.input;

import xenoframium.ecs.event.EventData;
import xenoframium.ecs.event.EventID;

/**
 * Created by chrisjung on 24/12/17.
 */
public class InputEventData implements EventData {
    public final int actionType;

    public InputEventData(int actionType) {
        this.actionType = actionType;
    }
}
