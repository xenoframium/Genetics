package xenoframium.ecsrender.renderers;

import xenoframium.ecs.BasicFilter;
import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityFilter;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.components.RenderComponent2D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.gl.Projection;
import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.Mat4;
import xenoframium.glmath.linearalgebra.Vec3;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by chrisjung on 5/12/17.
 */
class Renderer2D {
    private static EntityFilter filter = new BasicFilter(RenderComponent2D.class);

    public Projection projection;

    public Renderer2D(Projection projection) {
        this.projection = projection;
    }

    private void renderEntity(Entity entity, Mat4 vp) {
        RenderComponent2D renderComponent2D = entity.getComponent(RenderComponent2D.class);
        TransformComponent2D transform = entity.getComponent(TransformComponent2D.class);

        renderComponent2D.strategy.render(entity, GLM.mult(vp, transform.getModelMatrix()));
    }

    public void render(Space space) {
        Set<Entity> entities = space.getEntities(filter);

        ArrayList<Entity> entitiesArray = new ArrayList<>(entities);
        Collections.sort(entitiesArray, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return o1.getComponent(TransformComponent2D.class).compareZ(o2.getComponent(TransformComponent2D.class));
            }
        });
        Collections.reverse(entitiesArray);

        Mat4 vp = projection.getMat().scale(new Vec3(-1.0f, 1.0f, 1.0f));

        glDepthMask(false);
        for (Entity e : entitiesArray) {
            renderEntity(e, vp);
        }
        glDepthMask(true);
    }
}
