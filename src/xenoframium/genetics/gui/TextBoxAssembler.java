package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.colour.ColouredRenderStrategy;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.text.Font;
import xenoframium.ecsrender.text.TextRenderStrategy;
import xenoframium.genetics.model.Models;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 25/12/17.
 */
public class TextBoxAssembler {
    public static Entity assembleEntity(Space space, Font font, String text, Vec4 fgColour, Vec4 bgColour) {
        Entity textBox = space.createEntity();
        Entity textEntity = space.createEntity();
        Entity bgEntity = space.createEntity();

        TransformComponent2D tbtc = new TransformComponent2D();
        textBox.addComponent(tbtc);

        TransformComponent2D bgtc = new TransformComponent2D();
        bgEntity.addComponent(bgtc);
        bgtc.setParent(textBox);

        TransformComponent2D txttc = new TransformComponent2D();
        textEntity.addComponent(txttc);
        txttc.setParent(textBox);
        txttc.z -= 0.1f;

        TextRenderStrategy strat = new TextRenderStrategy(font, text, 0, fgColour, bgColour);
        textEntity.addComponent(new RenderComponent2D(strat, true));

        bgEntity.addComponent(new RenderComponent2D(new ColouredRenderStrategy(Models.SQUARE, bgColour), false));

        bgtc.scale = new Vec2(strat.width + 0.2f, strat.height + 0.2f);
        bgtc.pos = new Vec2(0.1f / (strat.width + 0.2f), -0.1f / (strat.height + 0.2f));

        textBox.addComponent(new TextBoxComponent(textEntity, bgEntity, text, fgColour, bgColour));
        tbtc.scale = new Vec2(1/1.2f, 1/1.2f);

        return textBox;
    }
}
