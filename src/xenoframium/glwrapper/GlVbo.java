package xenoframium.glwrapper;

import javax.swing.plaf.nimbus.State;

import static org.lwjgl.opengl.GL15.*;

//VBO objects are immutable
public class GlVbo implements AutoCloseable {
	private static final GlVbo NULL_VBO = new GlVbo(0);
	
	private final GlfwWindow sharedContext;
	private final int id;
	
	private GlVbo(int id) {
		sharedContext = GlfwWindow.getNullWindow();
		this.id = id;
	}
	
	public GlVbo() {
		sharedContext = StateManager.getCurrentContext();
		id = glGenBuffers();
	}
	
	public static GlVbo getNullVBO() {
		return NULL_VBO;
	}
	
	public void bind(int bufferType) {
		if (!StateManager.getCurrentContext().equals(sharedContext)) {
			throw new IncompatibleContextException("Incompatible context when trying to bind VBO");
		}
		glBindBuffer(bufferType, id);
	}
	
	public int getId() {
		return id;
	}

	public GlfwWindow getContext() {
		return sharedContext;
	}

	@Override
	public void close() {
		glDeleteBuffers(id);
	}
}