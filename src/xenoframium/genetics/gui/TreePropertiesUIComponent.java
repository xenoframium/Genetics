package xenoframium.genetics.gui;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.text.TextRenderStrategy;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.genetics.plant.PlantPropertiesComponent;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 31/12/17.
 */
public class TreePropertiesUIComponent implements Component {
    public final Entity water;
    public final Entity temp;
    public final Entity humidity;
    public final Entity nutrients;
    public final Entity growth;
    public final Entity profit;

    public TreePropertiesUIComponent(Entity water, Entity temp, Entity humidity, Entity nutrients, Entity growth, Entity profit) {
        this.water = water;
        this.temp = temp;
        this.humidity = humidity;
        this.nutrients = nutrients;
        this.growth = growth;
        this.profit = profit;
    }

    private void updateText(Entity entity, String txt) {
        entity.getComponent(RenderComponent2D.class).close();
        entity.removeComponent(RenderComponent2D.class);
        entity.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, txt, 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));
    }

    public void update(Entity plant) {
        if (plant == null) {
            updateText(water, "-");
            updateText(temp, "-");
            updateText(humidity, "-");
            updateText(nutrients, "-");
            updateText(growth, "-");
            updateText(profit, "-");
            return;
        }
        PlantPropertiesComponent ppc = plant.getComponent(PlantPropertiesComponent.class);
        updateText(water, ""+ppc.optimalWater);
        updateText(temp, ""+ppc.optimalTemperature);
        updateText(humidity, ""+ppc.optimalHumidity);
        updateText(nutrients, ""+ppc.requiredFertility);
        updateText(growth, ""+ppc.growthRate);
        updateText(profit, ""+ppc.yield);
    }
}
