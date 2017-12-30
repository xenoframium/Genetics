package xenoframium.genetics.plant;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;

/**
 * Created by chrisjung on 24/12/17.
 */
public class TerrainComponent implements Component {
    public final Entity land;

    TerrainComponent(Entity land) {
        this.land = land;
    }
}
