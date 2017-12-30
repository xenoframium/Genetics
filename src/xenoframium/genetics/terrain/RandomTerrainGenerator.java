package xenoframium.genetics.terrain;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.genetics.plant.PlantAssembler;
import xenoframium.glmath.linearalgebra.Vec3;

import java.util.Random;

/**
 * Created by chrisjung on 21/12/17.
 */
public class RandomTerrainGenerator implements TerrainGenerator {
    @Override
    public Entity[][] generateTerrain(Space space, Entity map, int width, int height) {
        Entity[][] grid = new Entity[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int dist = Math.abs(i-width/2) + Math.abs(j-height/2);
                int statModifier = Math.abs(i-width/2) + Math.abs(j-height/2) + 1;
                statModifier *= statModifier/2;
                Random random = new Random();
                int boostStat = random.nextInt(4);
                int temp = random.nextInt(10)+statModifier+5;
                int water = random.nextInt(10)+statModifier+5;
                int humidity = random.nextInt(10)+statModifier+5;
                int nutrients = Math.max(5, 100-(random.nextInt(10)+ (int) (10*Math.log(statModifier+1))+5));

                int cost = (Math.abs(i-width/2) + Math.abs(j-height/2));
                cost = cost*cost;
                int ptemp = dist + random.nextInt(10);
                int pwater = dist + random.nextInt(10);
                int pnutrients = 70 - dist - random.nextInt(5);
                int phumidity = dist + random.nextInt(10);
                int pgrowth = random.nextInt(30)/10+1;
                int pyield = random.nextInt(20)+1;
                int pmaturity = random.nextInt(90)+5;

                switch (boostStat) {
                    case 0:
                        temp *= 2;
                        break;
                    case 1:
                        water *= 2;
                        break;
                    case 2:
                        humidity *= 2;
                        break;
                    case 3:
                        nutrients /= 2;
                        break;
                }

                grid[i][j] = TerrainLandAssembler.assembleEntity(space, map, i-width/2, j-height/2, cost, temp, water, humidity, nutrients);
                Entity plant = PlantAssembler.assembleEntity(space, grid[i][j], "Random Plant", pyield, phumidity, pwater, ptemp, pgrowth, pnutrients, pmaturity, new Vec3(170/255f*(float)random.nextDouble(), 225/255f, 100/255f));
                grid[i][j].getComponent(TerrainPropertiesComponent.class).contents = plant;
            }
        }

        return grid;
    }
}
