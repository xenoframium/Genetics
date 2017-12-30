package xenoframium.genetics;

import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityManager;
import xenoframium.ecs.Space;
import xenoframium.ecs.state.StateStack;
import xenoframium.ecsrender.GraphicsManager;
import xenoframium.ecsrender.components.BackRenderComponent2D;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.components.TransformComponent2D;
import xenoframium.ecsrender.components.TransformComponent3D;
import xenoframium.ecsrender.gl.Camera;
import xenoframium.ecsrender.gl.Projection;
import xenoframium.ecsrender.gl.Window;
import xenoframium.ecsrender.renderers.Renderer;
import xenoframium.ecsrender.texture.Texture;
import xenoframium.ecsrender.texture.TexturedRenderStrategy;
import xenoframium.genetics.gamestates.TerrainViewState;
import xenoframium.genetics.model.Models;
import xenoframium.genetics.player.Player;
import xenoframium.glmath.linearalgebra.Vec2;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.quaternion.Quat;

import java.io.File;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;

/**
 * Created by chrisjung on 21/12/17.
 */
public class Genetics {
    public static final Player player = new Player();

    public static final Window window = GraphicsManager.initGlAndContext("Genetics", 800, 600);
    public static final Projection perspective = Projection.createPerspectiveProjection(90, GraphicsManager.getWindow().getAspect(), 0.1f, 100);
    public static final Camera camera = new Camera(new Vec3(0, 0, 0), new Vec3(0, 0, 1));
    public static final Projection orthographic = Projection.createOrthoProjection(GraphicsManager.getWindow().width, GraphicsManager.getWindow().height, 0, 100);
    public static final Renderer renderer = new Renderer(camera, perspective, orthographic);

    public static final StateStack stateManager = new StateStack();
    public static final EntityManager entityManager = new EntityManager(stateManager);

    static {
        camera.transform(new Quat(new Vec3(0, 1, 0), (float) Math.toRadians(-45)).toRotMat());
        camera.transform(new Quat(new Vec3(1, 0, 0), (float) Math.toRadians(45)).toRotMat());
        camera.move(new Vec3(0, 0, -2));
    }

    public static void main(String[] args) {
        Space space = entityManager.createSpace();
        TerrainViewState state = new TerrainViewState(space);
        Texture t = new Texture(new File("assets/background.png"));

        Entity background = space.createEntity();
        background.addComponent(new BackRenderComponent2D(new TexturedRenderStrategy(Models.SQUARE, Models.getSquareUvs(), t), false));
        TransformComponent2D backgroundTransform = new TransformComponent2D();
        background.addComponent(backgroundTransform);
        backgroundTransform.pos = new Vec2(0.5f, -0.5f);
        backgroundTransform.scale = new Vec2(800, 600);

        stateManager.push(state);
        window.show();
        while (!window.shouldClose()) {
            entityManager.update();
            entityManager.render();
            glfwPollEvents();
        }
    }
}
