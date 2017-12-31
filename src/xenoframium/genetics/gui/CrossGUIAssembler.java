package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.event.Event;
import xenoframium.ecs.event.EventBus;
import xenoframium.ecsrender.colour.ColouredRenderStrategy;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.input.InputComponent;
import xenoframium.ecsrender.picking.PickingComponent2D;
import xenoframium.genetics.graphics.TreeRenderStrategy;
import xenoframium.genetics.model.Models;
import xenoframium.genetics.plant.PlantPropertiesComponent;
import xenoframium.genetics.plant.TerrainComponent;
import xenoframium.genetics.selection.SelectionComponent;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;

import java.util.Random;

/**
 * Created by chrisjung on 26/12/17.
 */
public class CrossGUIAssembler {
    private static final CrossGUIInput input = new CrossGUIInput();

    public static Entity assembleEntity(Space space) {
        Entity crossGUI = space.createEntity();
        TransformComponent2D crossGUIT = new TransformComponent2D();
        crossGUIT.pos = new Vec2(147, -226);
        crossGUIT.z = -1.0f;
        crossGUI.addComponent(crossGUIT);

        Entity inButton1P = space.createEntity();
        TransformComponent2D inButton1PT = new TransformComponent2D();
        inButton1P.addComponent(inButton1PT);
        inButton1PT.pos = new Vec2(0, -29);
        inButton1PT.setParent(crossGUI);

        Entity inButton1 = space.createEntity();
        TransformComponent2D inButton1T = new TransformComponent2D();
        inButton1.addComponent(inButton1T);
        inButton1.addComponent(new RenderComponent2D(new ColouredRenderStrategy(Models.SQUARE, new Vec4(1.0f, 1.0f, 1.0f, 0.0f)), true));
        inButton1.addComponent(new PickingComponent2D(Models.SQUARE));
        inButton1.addComponent(new SelectionComponent());
        inButton1.addComponent(input.getInputComponent());
        inButton1T.scale = new Vec2(23, 28);
        inButton1T.setParent(inButton1P);

        Entity treeUI1 = TreePropertiesUIAssembler.assembleEntity(space);
        treeUI1.getComponent(TransformComponent2D.class).z = -0.2f;
        treeUI1.getComponent(TransformComponent2D.class).setParent(inButton1P);

        Entity inButton2P = space.createEntity();
        Entity treeUI2 = TreePropertiesUIAssembler.assembleEntity(space);
        treeUI2.getComponent(TransformComponent2D.class).z = -0.3f;
        treeUI2.getComponent(TransformComponent2D.class).setParent(inButton2P);

        Entity outP = space.createEntity();
        outP.addComponent(new TransformComponent2D());
        outP.getComponent(TransformComponent2D.class).pos = new Vec2(-146, -279);
        outP.getComponent(TransformComponent2D.class).z = -0.2f;
        Entity treeUI3 = TreePropertiesUIAssembler.assembleEntity(space);
        treeUI3.getComponent(TransformComponent2D.class).z = -0.2f;
        treeUI3.getComponent(TransformComponent2D.class).setParent(outP);

        inButton1.addComponent(new CrossGUIButtonComponent(new CrossGUIButtonCallback() {
            @Override
            public void onClick(Entity entity) {
                EventBus.post(new Event(GUIEvents.PLANT_CHOOSE_MODE), space, inButton1);
                SelectionMode.currentMode = SelectionMode.Mode.CHOOSE;
                CrossGUIInput.button = entity;
            }

            @Override
            public void onPlantSelect(Entity entity) {
                TerrainPropertiesComponent land = entity.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class);
                if (!land.isOwned()) {
                    return;
                }
                if (entity == CrossGUIInput.plant2) {
                    CrossGUIInput.plant2 = null;
                    treeUI2.getComponent(TreePropertiesUIComponent.class).update(null);
                }
                CrossGUIInput.plant1 = entity;
                treeUI1.getComponent(TreePropertiesUIComponent.class).update(entity);
            }
        }));

        TransformComponent2D inButton2PT = new TransformComponent2D();
        inButton2P.addComponent(inButton2PT);
        inButton2PT.pos = new Vec2(-1, -62);
        inButton2PT.setParent(crossGUI);

        Entity inButton2 = space.createEntity();
        TransformComponent2D inButton2T = new TransformComponent2D();
        inButton2.addComponent(inButton2T);
        inButton2.addComponent(new RenderComponent2D(new ColouredRenderStrategy(Models.SQUARE, new Vec4(1.0f, 1.0f, 1.0f, 0.0f)), true));
        inButton2.addComponent(new PickingComponent2D(Models.SQUARE));
        inButton2.addComponent(new SelectionComponent());

        inButton2.addComponent(input.getInputComponent());
        inButton2T.scale = new Vec2(23, 28);
        inButton2T.setParent(inButton2P);

        inButton2.addComponent(new CrossGUIButtonComponent(new CrossGUIButtonCallback() {
            @Override
            public void onClick(Entity entity) {
                EventBus.post(new Event(GUIEvents.PLANT_CHOOSE_MODE), space, inButton2);
                SelectionMode.currentMode = SelectionMode.Mode.CHOOSE;
                CrossGUIInput.button = entity;
            }

            @Override
            public void onPlantSelect(Entity entity) {
                TerrainPropertiesComponent land = entity.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class);
                if (!land.isOwned()) {
                    return;
                }
                if (entity == CrossGUIInput.plant1) {
                    CrossGUIInput.plant1 = null;
                    treeUI1.getComponent(TreePropertiesUIComponent.class).update(null);
                }
                CrossGUIInput.plant2 = entity;
                treeUI2.getComponent(TreePropertiesUIComponent.class).update(entity);
            }
        }));

        Entity crossButtonP = space.createEntity();
        TransformComponent2D crossButtonPT = new TransformComponent2D();
        crossButtonPT.pos = new Vec2(-106, -271);
        crossButtonPT.z = -0.2f;
        crossButtonP.addComponent(crossButtonPT);

        Entity crossButton = space.createEntity();
        TransformComponent2D crossButtonT = new TransformComponent2D();
        crossButtonT.setParent(crossButtonP);
        crossButtonT.scale = new Vec2(36, 29);
        crossButton.addComponent(crossButtonT);
        crossButton.addComponent(new RenderComponent2D(new ColouredRenderStrategy(Models.SQUARE, new Vec4(1.0f, 1.0f, 1.0f, 0.0f)), true));
        crossButton.addComponent(new PickingComponent2D(Models.SQUARE));
        crossButton.addComponent(new SelectionComponent());
        crossButton.addComponent(input.getInputComponent());
        Entity dummy = space.createEntity();
        dummy.addComponent(new TransformComponent3D());
        crossButton.addComponent(new CrossGUIButtonComponent(new CrossGUIButtonCallback() {
            @Override
            public void onClick(Entity entity) {
                if (CrossGUIInput.plant1 == null || CrossGUIInput.plant2 == null) {
                    return;
                }
                PlantPropertiesComponent pp1 = CrossGUIInput.plant1.getComponent(PlantPropertiesComponent.class);
                PlantPropertiesComponent pp2 = CrossGUIInput.plant2.getComponent(PlantPropertiesComponent.class);
                if (!pp1.isMature() || !pp1.isMature()) {
                    return;
                }
                CrossGUIInput.plant1 = null;
                CrossGUIInput.plant2 = null;
                treeUI1.getComponent(TreePropertiesUIComponent.class).update(null);
                treeUI2.getComponent(TreePropertiesUIComponent.class).update(null);
                pp1.resetMaturity();
                pp2.resetMaturity();
                CrossGUIInput.newPlantProperties = cross(pp1, pp2);
                dummy.addComponent(CrossGUIInput.newPlantProperties);
                treeUI3.getComponent(TreePropertiesUIComponent.class).update(dummy);
                dummy.removeComponent(PlantPropertiesComponent.class);
            }

            @Override
            public void onPlantSelect(Entity entity) {}
        }));

        Entity outButtonP = space.createEntity();
        TransformComponent2D outButtonPT = new TransformComponent2D();
        outButtonP.addComponent(outButtonPT);
        outButtonPT.pos = new Vec2(-305, -44);
        outButtonPT.setParent(crossGUI);
        outButtonPT.z = -0.2f;

        Entity outButton = space.createEntity();
        TransformComponent2D outButtonT = new TransformComponent2D();
        outButton.addComponent(outButtonT);
        outButton.addComponent(new RenderComponent2D(new ColouredRenderStrategy(Models.SQUARE, new Vec4(1.0f, 1.0f, 1.0f, 0.0f)), true));
        outButton.addComponent(new PickingComponent2D(Models.SQUARE));
        outButton.addComponent(new SelectionComponent());

        outButton.addComponent(input.getInputComponent());
        outButtonT.scale = new Vec2(23, 28);
        outButtonT.setParent(outButtonP);

        outButton.addComponent(new CrossGUIButtonComponent(new CrossGUIButtonCallback() {
            @Override
            public void onClick(Entity entity) {
                EventBus.post(new Event(GUIEvents.PLANT_PLACE_MODE), space, inButton2);
                SelectionMode.currentMode = SelectionMode.Mode.PLACE;
                CrossGUIInput.button = entity;
            }

            @Override
            public void onPlantSelect(Entity entity) {
                if (CrossGUIInput.newPlantProperties == null) {
                    return;
                }
                TerrainPropertiesComponent land = entity.getComponent(TerrainComponent.class).land.getComponent(TerrainPropertiesComponent.class);
                if (!land.isOwned()) {
                    return;
                }
                entity.removeComponent(PlantPropertiesComponent.class);
                entity.removeComponent(RenderComponent3D.class);
                entity.addComponent(CrossGUIInput.newPlantProperties);
                entity.addComponent(new RenderComponent3D(new TreeRenderStrategy(new Vec4(CrossGUIInput.newPlantProperties.getColour(), 1.0f)), false));
                CrossGUIInput.newPlantProperties = null;
                treeUI3.getComponent(TreePropertiesUIComponent.class).update(null);
            }
        }));

        return crossGUI;
    }

    private static PlantPropertiesComponent cross(PlantPropertiesComponent a, PlantPropertiesComponent b) {
        Random random = new Random();
        int water = Math.max(0, random.nextBoolean()?a.optimalWater:b.optimalWater+random.nextInt(4)-2);
        int temp = Math.max(0, random.nextBoolean()?a.optimalTemperature:b.optimalTemperature+random.nextInt(4)-2);
        int humidity = Math.max(0, random.nextBoolean()?a.optimalHumidity:b.optimalHumidity+random.nextInt(4)-2);
        int nut = Math.max(0, random.nextBoolean()?a.requiredFertility:b.requiredFertility+random.nextInt(4)-2);
        int growth= Math.max(1, random.nextBoolean()?a.growthRate:b.growthRate+random.nextInt(4)-2);
        int prof = Math.max(0, random.nextBoolean()?a.yield:b.yield+random.nextInt(4)-2);
        return new PlantPropertiesComponent("Random plant", prof, humidity, water, temp, growth, nut, new Vec3(170/255f*(float)random.nextDouble(), 225/255f, 100/255f));
    }
}
