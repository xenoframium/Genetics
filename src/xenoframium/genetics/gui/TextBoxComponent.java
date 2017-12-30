package xenoframium.genetics.gui;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.text.TextRenderStrategy;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.graphics.Fonts;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec4;

/**
 * Created by chrisjung on 25/12/17.
 */
public class TextBoxComponent implements Component {
    String text;

    Vec4 fgColour;
    Vec4 bgColour;

    Entity textEntity;
    Entity bgEntity;

    TextBoxComponent(Entity textEntity, Entity bgEntity, String text, Vec4 fgColour, Vec4 bgColour) {
        this.textEntity = textEntity;
        this.bgEntity = bgEntity;
        this.text = text;
        this.fgColour = new Vec4(fgColour);
        this.bgColour = new Vec4(bgColour);
    }

    public void setText(String newText) {
        text = newText;
        textEntity.getComponent(RenderComponent2D.class).close();
        textEntity.removeComponent(RenderComponent2D.class);

        TextRenderStrategy strat = new TextRenderStrategy(Fonts.serif, text, 0, fgColour, bgColour);
        textEntity.addComponent(new RenderComponent2D(strat, true));

        bgEntity.getComponent(TransformComponent2D.class).scale = new Vec2(strat.width + 0.2f, strat.height + 0.2f);
        bgEntity.getComponent(TransformComponent2D.class).pos = new Vec2(0.1f / (strat.width + 0.2f), -0.1f / (strat.height + 0.2f));
    }

    public String getText() {
        return text;
    }
}
