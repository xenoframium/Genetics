package xenoframium.glwrapper;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
	private static GlfwWindow currentContext = GlfwWindow.getNullWindow();
	private static GlVao currentVAO = GlVao.getNullVAO();
	private static GlProgram currentProgram = GlProgram.getNullProgram();
	private static Map<Integer, GlTexture> currentTextures = new HashMap<>();

	static void makeContextCurrent(GlfwWindow window) {
		if (!currentContext.equals(window)) {
			currentContext = window;
			glfwMakeContextCurrent(window.getId());
		}
	}
	
	public static GlfwWindow getCurrentContext() {
		return currentContext;
	}

	static void bindVertexArray(GlVao vao) {
		if (!currentVAO.equals(vao)) {
			currentVAO = vao;
			glBindVertexArray(vao.getId());
		}
	}

	public static GlVao getVAO() {
		return currentVAO;
	}

	static void useProgram(GlProgram program) {
		if (!currentProgram.equals(program)) {
			currentProgram = program;
			glUseProgram(program.getId());
		}
	}
	
	public static GlProgram getCurrentProgram() {
		return currentProgram;
	}
	
	static void bindTexture(int textureType, GlTexture texture) {
		if (!texture.equals(currentTextures.get(textureType))) {
			currentTextures.put(textureType, texture);
			glBindTexture(textureType, texture.getId());
		}
	}
	
	public static GlTexture getTexture(int target) {
		return currentTextures.get(target);
	}
}
