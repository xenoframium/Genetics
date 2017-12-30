package xenoframium.genetics.graphics;

import javafx.scene.effect.Lighting;
import xenoframium.ecs.Entity;
import xenoframium.ecsrender.Mesh;
import xenoframium.ecsrender.RenderStrategy;
import xenoframium.ecsrender.components.RenderComponent3D;
import xenoframium.ecsrender.gl.IBO;
import xenoframium.ecsrender.gl.VAO;
import xenoframium.ecsrender.gl.VBO;
import xenoframium.ecsrender.texture.Texture;
import xenoframium.genetics.graphics.GeneticsShaders;
import xenoframium.genetics.terrain.TerrainPropertiesComponent;
import xenoframium.glmath.linearalgebra.Mat4;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.glwrapper.GlUniform;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by chrisjung on 21/12/17.
 */
public class LandRenderStrategy implements RenderStrategy {
    private static final GlUniform mvpUniform = new GlUniform(GeneticsShaders.outlineShader, "mvp");
    private static final GlUniform fgUniform = new GlUniform(GeneticsShaders.outlineShader, "fgColour");
    private static final GlUniform bgUniform = new GlUniform(GeneticsShaders.outlineShader, "bgColour");
    private static final GlUniform brightnessUniform = new GlUniform(GeneticsShaders.outlineShader, "brightness");

    private static final Vec4 UNOWNED_FILL_COLOUR = new Vec4(140/255f, 104/255f,  57/255f, 1.0f);
    private static final Vec4 UNOWNED_BORDER_COLOUR = new Vec4(110/255f, 74/255f, 20/255f, 1.0f);
    private static final Vec4 OWNED_FILL_COLOUR = new Vec4(80/255f, 158/255f,  54/255f, 1.0f);
    private static final Vec4 OWNED_BORDER_COLOUR = new Vec4(13/255f, 141/255f, 0/255f, 1.0f);

    private final VAO vao;
    private final VBO vertexVBO;
    private final VBO uvVBO;
    private final IBO ibo;
    private final Texture texture;
    private final int renderMode;
    private final int numVertices;

    private float brightness = 1.0f;

    public LandRenderStrategy(Mesh mesh, float uvs[], Texture texture) {
        this.renderMode = mesh.getRenderMode();
        this.numVertices = mesh.getNumVertices();

        vertexVBO = new VBO();
        vertexVBO.bufferData(mesh.getVertices());
        uvVBO = new VBO();
        uvVBO.bufferData(uvs);
        this.texture = texture;

        ibo = new IBO();
        ibo.bufferData(mesh.getIndices());

        vao = new VAO();
        vao.addAttribPointer(vertexVBO, 0, 3, GL_FLOAT);
        vao.addAttribPointer(uvVBO, 1, 2, GL_FLOAT);
    }

    @Override
    public void render(Entity e, Mat4 mvp) {
        GeneticsShaders.outlineShader.use();
        texture.bind();
        vao.bind();
        if (!e.getComponent(TerrainPropertiesComponent.class).isOwned()) {
            glUniform4fv(fgUniform.getLocation(), UNOWNED_FILL_COLOUR.asArr());
            glUniform4fv(bgUniform.getLocation(), UNOWNED_BORDER_COLOUR.asArr());
        } else {
            glUniform4fv(fgUniform.getLocation(), OWNED_FILL_COLOUR.asArr());
            glUniform4fv(bgUniform.getLocation(), OWNED_BORDER_COLOUR.asArr());
        }
        glUniformMatrix4fv(mvpUniform.getLocation(), false, mvp.asArr());
        glUniform1f(brightnessUniform.getLocation(), brightness);
        ibo.bind();
        glDrawElements(renderMode, numVertices, GL_UNSIGNED_INT, NULL);
    }

    @Override
    public void close() {
        vao.close();
        vertexVBO.close();
        uvVBO.close();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getBrightness() {
        return brightness;
    }
}
