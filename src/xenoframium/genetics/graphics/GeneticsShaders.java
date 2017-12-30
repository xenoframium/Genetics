package xenoframium.genetics.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import xenoframium.glwrapper.GlProgram;
import xenoframium.glwrapper.GlShader;

import java.io.File;

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
 * Created by chrisjung on 21/12/17.
 */
public class GeneticsShaders {
    public static final GlProgram outlineShader;
    public static final GlProgram objShader;

    static {
        GlShader vert = new GlShader(GL_VERTEX_SHADER, new File("shaders/outlineVert.vert"));
        GlShader frag = new GlShader(GL_FRAGMENT_SHADER, new File("shaders/outlineFrag.frag"));
        outlineShader = new GlProgram(vert, frag);
        vert.delete();
        frag.delete();

        vert = new GlShader(GL_VERTEX_SHADER, new File("shaders/objVert.vert"));
        frag = new GlShader(GL_FRAGMENT_SHADER, new File("shaders/objFrag.frag"));
        objShader = new GlProgram(vert, frag);

        vert.delete();
        frag.delete();

    }
}
