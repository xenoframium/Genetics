package xenoframium.ecs.event;

/**
 * Created by chrisjung on 24/12/17.
 */
public final class GlobalEvent {
    public final GlobalEventID id;
    public final EventData data;

    public GlobalEvent(GlobalEventID id, EventData data) {
        this.id = id;
        this.data = data;
    }
}
