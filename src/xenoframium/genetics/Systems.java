package xenoframium.genetics;

import xenoframium.ecsrender.input.InputSystem;
import xenoframium.ecsrender.picking.PickingSystem2D;
import xenoframium.ecsrender.picking.PickingSystem3D;
import xenoframium.genetics.plant.PlantGrowthSystem;
import xenoframium.genetics.selection.SelectionSystem;
import xenoframium.genetics.terrain.TerrainMapSystem;

/**
 * Created by chrisjung on 21/12/17.
 */
public class Systems {
    public static final InputSystem inputSystem = new InputSystem();
    public static final TerrainMapSystem terrainMapSystem = new TerrainMapSystem();
    public static final PlantGrowthSystem plantGrowthSystem = new PlantGrowthSystem();
    public static final SelectionSystem selectionSystem = new SelectionSystem();
    public static final PickingSystem3D pickingSystem3D = new PickingSystem3D(Genetics.camera, Genetics.perspective, Float.POSITIVE_INFINITY);
    public static final PickingSystem2D pickingSystem2D = new PickingSystem2D(Genetics.orthographic);
}
