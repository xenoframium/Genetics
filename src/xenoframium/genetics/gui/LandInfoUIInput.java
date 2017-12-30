package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecs.event.EventListener;
import xenoframium.genetics.plant.TerrainComponent;
import xenoframium.genetics.plant.PlantEvents;
import xenoframium.genetics.plant.PlantPropertiesComponent;
import xenoframium.genetics.terrain.TerrainEvents;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;

/**
 * Created by chrisjung on 25/12/17.
 */
public class LandInfoUIInput implements EventListener {
    Entity water;
    Entity temp;
    Entity humidity;
    Entity fertility;
    Entity price;

    LandInfoUIInput(Entity water, Entity temp, Entity humidity, Entity fertility, Entity price) {
        this.water = water;
        this.temp = temp;
        this.humidity = humidity;
        this.fertility = fertility;
        this.price = price;
        EventBus.subscribe(this, TerrainEvents.TERRAIN_SELECTION);
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
        if (entity.hasComponent(PlantPropertiesComponent.class)) {
            entity = entity.getComponent(TerrainComponent.class).land;
        }

        TerrainPropertiesComponent terrainPropertiesComponent = entity.getComponent(TerrainPropertiesComponent.class);

        updateEntity(water, terrainPropertiesComponent.water);
        updateEntity(temp, terrainPropertiesComponent.temperature);
        updateEntity(humidity, terrainPropertiesComponent.humidity);
        updateEntity(fertility, terrainPropertiesComponent.fertility);
        updateEntity(price, terrainPropertiesComponent.price);
    }
}
