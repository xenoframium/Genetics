package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.text.TextRenderStrategy;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 31/12/17.
 */
public class TreePropertiesUIAssembler {
    public static Entity assembleEntity(Space space) {
        Entity positioningRoot = space.createEntity();
        positioningRoot.addComponent(new TransformComponent2D());

        Entity waterP = space.createEntity();
        TransformComponent2D waterPT = new TransformComponent2D();
        waterPT.pos = new Vec2(-45, 10);
        waterPT.setParent(positioningRoot);
        waterP.addComponent(waterPT);

        Entity water = space.createEntity();
        TransformComponent2D waterT = new TransformComponent2D();
        waterT.setParent(waterP);
        waterT.scale = new Vec2(10, 10);
        water.addComponent(waterT);
        water.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        Entity tempP = space.createEntity();
        TransformComponent2D tempPT = new TransformComponent2D();
        tempPT.pos = new Vec2(-78, 10);
        tempPT.setParent(positioningRoot);
        tempP.addComponent(tempPT);

        Entity temp = space.createEntity();
        TransformComponent2D tempT = new TransformComponent2D();
        tempT.setParent(tempP);
        tempT.scale = new Vec2(10, 10);
        temp.addComponent(tempT);
        temp.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        Entity humP = space.createEntity();
        TransformComponent2D humPT = new TransformComponent2D();
        humPT.pos = new Vec2(-111, 10);
        humPT.setParent(positioningRoot);
        humP.addComponent(humPT);

        Entity hum = space.createEntity();
        TransformComponent2D humT = new TransformComponent2D();
        humT.setParent(humP);
        humT.scale = new Vec2(10, 10);
        hum.addComponent(humT);
        hum.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        Entity nutP = space.createEntity();
        TransformComponent2D nutPT = new TransformComponent2D();
        nutPT.pos = new Vec2(-144, 10);
        nutPT.setParent(positioningRoot);
        nutP.addComponent(nutPT);

        Entity nut = space.createEntity();
        TransformComponent2D nutT = new TransformComponent2D();
        nutT.setParent(nutP);
        nutT.scale = new Vec2(10, 10);
        nut.addComponent(nutT);
        nut.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        Entity growP = space.createEntity();
        TransformComponent2D growPT = new TransformComponent2D();
        growPT.pos = new Vec2(-177, 10);
        growPT.setParent(positioningRoot);
        growP.addComponent(growPT);

        Entity grow = space.createEntity();
        TransformComponent2D growT = new TransformComponent2D();
        growT.setParent(growP);
        growT.scale = new Vec2(10, 10);
        grow.addComponent(growT);
        grow.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        Entity profP = space.createEntity();
        TransformComponent2D profPT = new TransformComponent2D();
        profPT.pos = new Vec2(-210, 10);
        profPT.setParent(positioningRoot);
        profP.addComponent(profPT);

        Entity prof = space.createEntity();
        TransformComponent2D profT = new TransformComponent2D();
        profT.setParent(profP);
        profT.scale = new Vec2(10, 10);
        prof.addComponent(profT);
        prof.addComponent(new RenderComponent2D(new TextRenderStrategy(Fonts.serif, "-", 0, new Vec4(1, 1, 1, 1), new Vec4(0, 0, 0, 0)), true));

        positioningRoot.addComponent(new TreePropertiesUIComponent(water, temp, hum, nut, grow, prof));

        return positioningRoot;
    }
}
