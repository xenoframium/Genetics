package xenoframium.genetics.plant;

import xenoframium.ecs.*;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.graphics.TreeRenderStrategy;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;

import java.util.Random;
import java.util.Set;

/**
 * Created by chrisjung on 21/12/17.
 */
public class PlantGrowthSystem implements BaseSystem {
    private static final EntityFilter filter = new BasicFilter(PlantPropertiesComponent.class);

    private final Random rng = new Random();

    @Override
    public void update(Space space, double delta, double time) {
        Set<Entity> entities = space.getEntities(filter);

        for (Entity plant : entities) {
            PlantPropertiesComponent properties = plant.getComponent(PlantPropertiesComponent.class);
            if (rng.nextDouble() < getGrowthChance(plant)) {
                properties.maturity++;
            }
            if (properties.maturity >= 120 && properties.shouldAutoHarvest) {
                if (plant.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class).isOwned()) {
                    Genetics.player.addMoney(properties.yield);
                }
                properties.maturity = 0;
            }
            TransformComponent3D transform = plant.getComponent(TransformComponent3D.class);
            float percentGrowth = (float) Math.min(properties.getMaturity(), 100)/100f;
            float size = 0.15f + 0.3f * percentGrowth;
            transform.scale = new Vec3(size, size, size);
        }
    }

    public void mutate(Entity plant) {
        Random random = new Random();
        PlantPropertiesComponent ppc = plant.getComponent(PlantPropertiesComponent.class);
        plant.removeComponent(PlantPropertiesComponent.class);
        plant.removeComponent(RenderComponent3D.class);

        int yield = ppc.yield + random.nextFloat() < 0.1f ? 1 : 0;
        int water = ppc.optimalWater+ random.nextFloat() < 0.1f ? 1 : 0;
        int humidity = ppc.optimalHumidity + random.nextFloat() < 0.1f ? 1 : 0;
        int temp = ppc.optimalTemperature + random.nextFloat() < 0.1f ? 1 : 0;
        int growth = ppc.growthRate + random.nextFloat() < 0.1f ? 1 : 0;
        int fertility = ppc.requiredFertility + random.nextFloat() < 0.1f ? 1 : 0;

        plant.addComponent(new PlantPropertiesComponent("Random Plant", yield, humidity, water, temp, growth, fertility, new Vec3(170/255f*(float)random.nextDouble(), 225/255f, 100/255f)));
        plant.addComponent(new RenderComponent3D(new TreeRenderStrategy(new Vec4(plant.getComponent(PlantPropertiesComponent.class).getColour(), 1.0f)), false));
    }

    public double getGrowthChance(Entity plant) {
        PlantPropertiesComponent ppc = plant.getComponent(PlantPropertiesComponent.class);
        TerrainPropertiesComponent tpc = plant.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class);
        if (ppc.requiredFertility > tpc.fertility) {
            return 0;
        }
        double baseGrowth = Math.log(ppc.growthRate+1)*1.5;

        int differenceSum = 0;
        differenceSum += Math.max(0, -ppc.optimalHumidity+tpc.humidity);
        differenceSum += Math.max(0, -ppc.optimalTemperature+tpc.temperature);
        differenceSum += Math.max(0, -ppc.optimalWater+tpc.water);

        return baseGrowth * Math.pow(0.90, differenceSum);
    }
}
