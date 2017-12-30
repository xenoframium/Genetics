package xenoframium.genetics.gamestates;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecs.state.GameState;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.Systems;
import xenoframium.genetics.gui.GUIAssembler;
import xenoframium.genetics.gui.GUIOverlayAssembler;
import xenoframium.genetics.gui.MoneyUIAssembler;
import xenoframium.genetics.terrain.RandomTerrainGenerator;
import xenoframium.genetics.terrain.TerrainMapAssembler;
import xenoframium.glmath.linearalgebra.Vec3;

/**
 * Created by chrisjung on 21/12/17.
 */
public class TerrainViewState implements GameState {
    private final Space terrainViewSpace;

    public TerrainViewState(Space terrainViewSpace) {
        this.terrainViewSpace = terrainViewSpace;
    }

    @Override
    public void init() {
        Entity terrainMap = TerrainMapAssembler.assembleEntity(terrainViewSpace, new RandomTerrainGenerator());
        terrainMap.getComponent(TransformComponent3D.class).scale = new Vec3(0.3f, 0.3f, 0.3f);
        Entity gui = GUIAssembler.assembleEntity(terrainViewSpace, terrainMap);
    }

    @Override
    public void update(double delta, double time) {
        Systems.inputSystem.update(terrainViewSpace, delta, time);
        Systems.terrainMapSystem.update(terrainViewSpace, delta, time);
        Systems.plantGrowthSystem.update(terrainViewSpace, delta, time);
        Systems.pickingSystem3D.update(terrainViewSpace, delta, time);
        Systems.pickingSystem2D.update(terrainViewSpace, delta, time);
        Systems.selectionSystem.update(terrainViewSpace, delta, time);
    }

    @Override
    public void render() {
        Genetics.renderer.render(terrainViewSpace);
    }

    @Override
    public void destroy() {

    }
}
