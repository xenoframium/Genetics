package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 24/12/17.
 */
public class MoneyUIAssembler {
    public static Entity assembleEntity(Space space) {
        Entity moneyUI = TextBoxAssembler.assembleEntity(space, Fonts.serif, String.format("%,d", Genetics.player.getMoney()), new Vec4(1.0f, 1.0f, 1.0f, 1.0f), new Vec4(0.0f, 0.0f, 0.0f, 0.0f));

        TransformComponent2D tbtc = moneyUI.getComponent(TransformComponent2D.class);
        tbtc.scale = new Vec2(14, 14);

        new MoneyUIInput(moneyUI);

        return moneyUI;
    }
}
