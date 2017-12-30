package xenoframium.genetics.terrain;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecs.event.EventID;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainPropertiesComponent implements Component {
    public final int humidity;
    public final int water;
    public final int temperature;
    public final int fertility;
    public final int price;
    public final int x;
    public final int y;

    public Entity contents;
    public final Entity map;

    private boolean isOwned = false;

    public TerrainPropertiesComponent(Entity map, int x, int y, int price, int h, int w, int t, int f) {
        this.price = price;
        humidity = h;
        temperature = t;
        fertility = f;
        water = w;
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public boolean canBuy() {
        return isOwned() ? false : map.getComponent(TerrainMapComponent.class).canBuy(this);
    }

    public void setOwned() {
        isOwned = true;
    }

    public boolean isOwned() {
        return isOwned;
    }
}
