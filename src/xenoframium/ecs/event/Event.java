package xenoframium.ecs.event;

/**
 * Created by chrisjung on 24/12/17.
 */
public class Event {
    public final EventID id;
    public final EventData data;

    public Event(EventID id) {
        this.id = id;
        data = new NullEventData();
    }

    public Event(EventID id, EventData data) {
        this.id = id;
        this.data = data;
    }
}
