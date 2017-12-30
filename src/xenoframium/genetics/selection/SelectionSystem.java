package xenoframium.genetics.selection;

import xenoframium.ecs.*;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventID;
import xenoframium.ecs.event.NullEventData;
import xenoframium.ecsrender.picking.PickInfo2D;
import xenoframium.ecsrender.picking.PickInfo3D;
import xenoframium.genetics.Systems;

import java.util.Set;

/**
 * Created by chrisjung on 23/12/17.
 */
public class SelectionSystem implements BaseSystem {
    private static final EntityFilter filter = new BasicFilter(SelectionComponent.class);

    private Entity lastSelectedEntity;
    private Entity selectedEntity;

    @Override
    public void update(Space space, double delta, double time) {
        selectedEntity = null;

        Set<Entity> entities = space.getEntities(filter);

        PickInfo3D pickInfo3D = Systems.pickingSystem3D.pick();
        if (entities.contains(pickInfo3D.pickedEntity)) {
            selectedEntity = pickInfo3D.pickedEntity;
        }

        PickInfo2D pickInfo2D = Systems.pickingSystem2D.pick();
        if (entities.contains(pickInfo2D.pickedEntity)) {
            selectedEntity = pickInfo2D.pickedEntity;
        }

        if (lastSelectedEntity != selectedEntity) {
            if (lastSelectedEntity != null) {
                lastSelectedEntity.getComponent(SelectionComponent.class).isSelected = false;
                EventBus.post(new Event(SelectionEvents.DESELECTION_EVENT, new NullEventData()), space, lastSelectedEntity);
            }
        }

        if (selectedEntity != null && lastSelectedEntity != selectedEntity) {
            selectedEntity.getComponent(SelectionComponent.class).isSelected = true;
            EventBus.post(new Event(SelectionEvents.SELECTION_EVENT, new NullEventData()), space, selectedEntity);
        }

        lastSelectedEntity = selectedEntity;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }
}
