package xenoframium.genetics.model;

import xenoframium.ecsrender.Mesh;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by chrisjung on 21/12/17.
 */
public class Models {
    public static final Mesh CUBE = new Mesh(new float[]{
            0.5f, 1f, -0.5f,
            0.5f, 0f, -0.5f,
            -0.5f, 0f, -0.5f,
            -0.5f, 0f, -0.5f,
            -0.5f, 1f, -0.5f,
            0.5f, 1f, -0.5f,

            -0.5f, 1f, -0.5f,
            -0.5f, 0f, -0.5f,
            -0.5f, 0f, 0.5f,
            -0.5f, 0f, 0.5f,
            -0.5f, 1f, 0.5f,
            -0.5f, 1f, -0.5f,

            -0.5f, 1f, 0.5f,
            -0.5f, 0f, 0.5f,
            0.5f, 0f, 0.5f,
            0.5f, 0f, 0.5f,
            0.5f, 1f, 0.5f,
            -0.5f, 1f, 0.5f,

            0.5f, 1f, 0.5f,
            0.5f, 0f, 0.5f,
            0.5f, 0, -0.5f,
            0.5f, 0, -0.5f,
            0.5f, 1, -0.5f,
            0.5f, 1f, 0.5f,

            0.5f, 1f, 0.5f,
            0.5f, 1f, -0.5f,
            -0.5f, 1f, -0.5f,
            -0.5f, 1f, -0.5f,
            -0.5f, 1f, 0.5f,
            0.5f, 1f, 0.5f,

            0.5f, 0f, 0.5f,
            -0.5f, 0f, 0.5f,
            -0.5f, 0f, -0.5f,
            -0.5f, 0f, -0.5f,
            0.5f, 0f, -0.5f,
            0.5f, 0f, 0.5f
    }, GL_TRIANGLES);

    private static float[] CUBE_UVS = new float[]{
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 0,
            1, 1,
            0, 1
    };

    public static Mesh SQUARE = new Mesh(new float[]{0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f}, GL_TRIANGLE_STRIP);

    public static Mesh FLOOR_SQUARE = new Mesh(new float[]{-0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f, 0.5f}, GL_TRIANGLE_STRIP);

    private static float[] SQUARE_UVS = new float[]{0, 0, 1, 0, 0, 1, 1, 1};

    private static float[] FLOOR_SQUARE_UVS = new float[]{1, 0, 1, 1, 0, 0, 0, 1};

    public static float[] getCubeUvs() {
        return Arrays.copyOf(CUBE_UVS, CUBE_UVS.length);
    }

    public static float[] getSquareUvs() {
        return Arrays.copyOf(SQUARE_UVS, SQUARE_UVS.length);
    }

    public static float[] getFloorSquareUvs() {
        return Arrays.copyOf(FLOOR_SQUARE_UVS, FLOOR_SQUARE_UVS.length);
    }
}
