package xenoframium.genetics.plant;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.picking.PickingComponent3D;
import xenoframium.ecsrender.texture.Texture;
import xenoframium.genetics.graphics.TreeRenderStrategy;
import xenoframium.genetics.selection.SelectionComponent;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;

import java.io.File;

/**
 * Created by chrisjung on 21/12/17.
 */
public class PlantAssembler {
    private static final Texture cubeTexture = new Texture(new File("assets/outlinedSquare32.png"));
    private static final PlantInput input = new PlantInput();

    private static Entity createPlantEntity(Space space, Entity land, PlantPropertiesComponent component) {
        TreeRenderStrategy strategy = new TreeRenderStrategy(new Vec4(component.getColour(), 1.0f));
        strategy.setBrightness(0.7f);
        RenderComponent3D renderComponent = new RenderComponent3D(strategy, false);
        TransformComponent3D transformComponent = new TransformComponent3D();
        transformComponent.setParent(land);
        PickingComponent3D pick = new PickingComponent3D(TreeRenderStrategy.TREE_MESH);
        float percentGrowth = (float) Math.min(component.getMaturity(), 100)/100f;
        float size = 0.15f + 0.3f * percentGrowth;
        transformComponent.scale = new Vec3(size, size, size);

        Entity plant = space.createEntity();
        plant.addComponent(new TerrainComponent(land));
        plant.addComponent(renderComponent);
        plant.addComponent(transformComponent);
        plant.addComponent(component);
        plant.addComponent(pick);
        plant.addComponent(new SelectionComponent());
        plant.addComponent(input.getInputComponent());

        return plant;
    }

    public static Entity assembleEntity(Space space, Entity land, String name, int yield, int optimalHumidity, int optimalWater, int optimalTemperature, int growthRate, int requiredFertility, Vec3 colour) {
        PlantPropertiesComponent component = new PlantPropertiesComponent(name, yield, optimalHumidity, optimalWater, optimalTemperature, growthRate, requiredFertility, colour);
        return createPlantEntity(space, land, component);
    }

    public static Entity assembleEntity(Space space, Entity land, String name, int yield, int optimalHumidity, int optimalWater, int optimalTemperature, int growthRate, int requiredFertility, int maturity, Vec3 colour) {
        PlantPropertiesComponent component = new PlantPropertiesComponent(name, yield, optimalHumidity, optimalWater, optimalTemperature, growthRate, requiredFertility, colour, maturity);
        return createPlantEntity(space, land, component);
    }
}
