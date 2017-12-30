package xenoframium.genetics.graphics;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryStack;
import xenoframium.ecs.Entity;
import xenoframium.ecsrender.Mesh;
import xenoframium.ecsrender.RenderStrategy;
import xenoframium.ecsrender.gl.IBO;
import xenoframium.ecsrender.gl.VAO;
import xenoframium.ecsrender.gl.VBO;
import xenoframium.glmath.linearalgebra.Mat4;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.glwrapper.GlUniform;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;

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

import static org.lwjgl.assimp.Assimp.*;

/**
 * Created by chrisjung on 25/12/17.
 */
public class TreeRenderStrategy implements RenderStrategy {
    private static final GlUniform mvpUniform = new GlUniform(GeneticsShaders.objShader, "mvp");
    private static final GlUniform colourUniform = new GlUniform(GeneticsShaders.objShader, "inColour");
    private static final GlUniform brightnessUniform = new GlUniform(GeneticsShaders.objShader, "brightness");

    public static final Mesh TREE_MESH;

    private static final ArrayList<Float> vertData = new ArrayList<>();
    private static final ArrayList<Float> normData = new ArrayList<>();
    private static final ArrayList<Integer> indices = new ArrayList<>();
    private static final ArrayList<Integer> part = new ArrayList<>();

    static VAO vao;

    static VBO vbo;
    static VBO norms;
    static VBO stageVBO;
    static IBO ibo;

    static int numVertices;

    float brightness = 1.0f;

    private Vec4 colour;

    static {
        loadObj(new File("assets/treeLeaves.obj"), 0);
        loadObj(new File("assets/treeTrunk.obj"), 1);

        float[] vs = new float[vertData.size()];
        for (int i = 0; i < vertData.size(); i++) {
            vs[i] = vertData.get(i);
        }

        float[] ns = new float[normData.size()];
        for (int i = 0; i < normData.size(); i++) {
            ns[i] = normData.get(i);
        }

        int[] is = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            is[i] = indices.get(i);
        }

        int[] ps = new int[part.size()];
        for (int i = 0; i < part.size(); i++) {
            ps[i] = part.get(i);
        }

        numVertices = is.length;

        vbo = new VBO();
        vbo.bufferData(vs);
        norms = new VBO();
        norms.bufferData(ns);
        stageVBO = new VBO();
        stageVBO.bufferData(ps);

        ibo = new IBO();
        ibo.bufferData(is);

        vao = new VAO();
        vao.addAttribPointer(vbo, 0, 3, GL_FLOAT);
        vao.addAttribPointer(norms, 1, 3, GL_FLOAT);
        vao.addAttribPointerI(stageVBO, 2, 1, GL_UNSIGNED_INT);

        TREE_MESH = new Mesh(vs, is, GL_TRIANGLES);
    }

    private static void loadObj(File obj, int stage) {
        int offset = vertData.size()/3;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            AIScene scene = aiImportFile(obj.getAbsolutePath(), aiProcess_JoinIdenticalVertices | aiProcess_Triangulate);

            PointerBuffer buffer = scene.mMeshes();

            AIMesh[] meshes = new AIMesh[buffer.remaining()];
            int i = 0;
            while (buffer.hasRemaining()) {
                meshes[i] = AIMesh.create(buffer.get());
                i++;
            }

            i = 0;
            for (AIMesh mesh : meshes) {
                AIVector3D.Buffer verts = mesh.mVertices();
                while (verts.hasRemaining()) {
                    AIVector3D vert = verts.get();
                    vertData.add(vert.x());
                    vertData.add(vert.y());
                    vertData.add(vert.z());
                    part.add(stage);
                }

                AIVector3D.Buffer normals = mesh.mNormals();
                while (normals.hasRemaining()) {
                    AIVector3D normal = normals.get();
                    normData.add(normal.x());
                    normData.add(normal.y());
                    normData.add(normal.z());
                }

                int numFaces = mesh.mNumFaces();
                AIFace.Buffer facesBuffer = mesh.mFaces();
                for (int j = 0; j < numFaces; j++) {
                    AIFace face = facesBuffer.get(j);
                    IntBuffer ind = face.mIndices();
                    while (ind.hasRemaining()) {
                        indices.add(ind.get() + offset);
                    }
                }

                offset = vertData.size()/3;

                i++;
            }
        }
    }

    public TreeRenderStrategy(Vec4 colour) {
        this.colour = colour;
    }


    @Override
    public void render(Entity e, Mat4 mvp) {
        vao.bind();
        GeneticsShaders.objShader.use();

        glUniformMatrix4fv(mvpUniform.getLocation(), false, mvp.asArr());
        glUniform4fv(colourUniform.getLocation(), colour.asArr());
        glUniform1f(brightnessUniform.getLocation(), brightness);

        ibo.bind();
        glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, NULL);
    }

    @Override
    public void close() {
    }

    public Vec4 getColour() {
        return new Vec4(colour);
    }

    public void setColour(Vec4 colour) {
        this.colour = new Vec4(colour);
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getBrightness() {
        return brightness;
    }
}
