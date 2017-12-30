package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventListener;
import xenoframium.genetics.plant.PlantEvents;
import xenoframium.genetics.plant.PlantPropertiesComponent;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;

/**
 * Created by chrisjung on 25/12/17.
 */
public class PlantInfoUIInput implements EventListener {
    Entity water;
    Entity temp;
    Entity humidity;
    Entity fertility;
    Entity growth;
    Entity yield;

    PlantInfoUIInput(Entity water, Entity temp, Entity humidity, Entity fertility, Entity growth, Entity yield) {
        this.water = water;
        this.temp = temp;
        this.humidity = humidity;
        this.fertility = fertility;
        this.growth = growth;
        this.yield = yield;
        EventBus.subscribe(this, PlantEvents.PLANT_SELECTION);

    }

    private String format(int number) {
        return String.format("%,d", number);
    }

    private void updateEntity(Entity entity, int number) {
        entity.getComponent(TextBoxComponent.class).setText(format(number));
    }

    @Override
    public void onEvent(Event event, Space space, Entity entity) {
        PlantPropertiesComponent plantPropertiesComponent = entity.getComponent(PlantPropertiesComponent.class);

        updateEntity(water, plantPropertiesComponent.optimalWater);
        updateEntity(temp, plantPropertiesComponent.optimalTemperature);
        updateEntity(humidity, plantPropertiesComponent.optimalHumidity);
        updateEntity(fertility, plantPropertiesComponent.requiredFertility);
        updateEntity(growth, plantPropertiesComponent.growthRate);
        updateEntity(yield, plantPropertiesComponent.yield);
    }
}
