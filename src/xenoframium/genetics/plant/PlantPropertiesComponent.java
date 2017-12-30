package xenoframium.genetics.plant;

import xenoframium.ecs.Component;
import xenoframium.ecs.event.EventID;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 21/12/17.
 */
public class PlantPropertiesComponent implements Component {
    static final Vec4 SUSPENDED_COLOUR = new Vec4(255/255f,  251/255f, 91/255f, 1.0f);

    public final String name;
    public final int optimalHumidity;
    public final int optimalWater;
    public final int optimalTemperature;
    public final int requiredFertility;
    public final int yield;
    public final int growthRate;
    private final Vec3 colour;

    boolean isSelected = false;
    int maturity = 0;

    public boolean shouldAutoHarvest = true;

    public PlantPropertiesComponent(String name, int yield, int optimalHumidity, int optimalWater, int optimalTemperature, int growthRate, int requiredFertility, Vec3 colour) {
        this.name = name;
        this.yield = yield;
        this.optimalHumidity = optimalHumidity;
        this.optimalTemperature = optimalTemperature;
        this.growthRate = growthRate;
        this.requiredFertility = requiredFertility;
        this.optimalWater = optimalWater;
        this.colour = new Vec3(colour);
    }

    public PlantPropertiesComponent(String name, int yield, int optimalHumidity, int optimalWater, int optimalTemperature, int growthRate, int requiredFertility, Vec3 colour, int maturity) {
        this(name, yield, optimalHumidity, optimalWater, optimalTemperature, growthRate, requiredFertility, colour);
        this.maturity = maturity;
    }

    public int getMaturity() {
        return maturity;
    }

    public boolean isMature() {
        return maturity >= 100;
    }

    public void resetMaturity() {
        maturity = 0;
    }

    public Vec3 getColour() {
        if (shouldAutoHarvest) {
            return new Vec3(colour);
        } else {
            return new Vec3(SUSPENDED_COLOUR);
        }
    }
}
