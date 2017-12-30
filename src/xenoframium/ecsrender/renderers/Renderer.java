package xenoframium.ecsrender.renderers;

import xenoframium.ecs.BaseRenderer;
import xenoframium.ecs.Space;
import xenoframium.ecsrender.GraphicsManager;
import xenoframium.ecsrender.gl.Camera;
import xenoframium.ecsrender.gl.Projection;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

/**
 * Created by chrisjung on 18/12/17.
 */
public class Renderer implements BaseRenderer {
    private final BackRenderer2D backRenderer2D;
    private final Renderer2D renderer2D;
    private final Renderer3D renderer3D;

    public Renderer(Camera camera, Projection proj3D, Projection proj2D) {
        backRenderer2D = new BackRenderer2D(proj2D);
        renderer2D = new Renderer2D(proj2D);
        renderer3D = new Renderer3D(proj3D, camera);
    }

    @Override
    public void render(Space space) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        backRenderer2D.render(space);
//        glClearColor(GL_F);
        renderer3D.render(space);
        renderer2D.render(space);
        GraphicsManager.getWindow().swapBuffers();
    }
}
