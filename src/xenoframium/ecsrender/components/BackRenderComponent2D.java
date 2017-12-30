package xenoframium.ecsrender.components;

import xenoframium.ecs.Component;
import xenoframium.ecsrender.RenderStrategy;

/**
 * Created by chrisjung on 10/12/17.
 */
public class BackRenderComponent2D implements Component, AutoCloseable {
    public final RenderStrategy strategy;
    public final boolean hasTransparency;

    public BackRenderComponent2D(RenderStrategy strategy, boolean hasTransparency) {
        this.strategy = strategy;
        this.hasTransparency = hasTransparency;
    }

    @Override
    public void close() {
        strategy.close();
    }
}
