package xenoframium.glwrapper;

import javax.swing.plaf.nimbus.State;

import static org.lwjgl.opengl.GL30.*;

//VAO objects are immutable
public class GlVao implements AutoCloseable {
	private static final GlVao NULL_VAO = new GlVao(0);
	
	private final GlfwWindow sharedContext;
	private final int id;
	
	private GlVao(int id) {
		sharedContext = GlfwWindow.getNullWindow();
		this.id = id;
	}
	
	public GlVao() {
		sharedContext = StateManager.getCurrentContext();
		id = glGenVertexArrays();
	}
	
	public static GlVao getNullVAO() {
		return NULL_VAO;
	}
	
	public void bind() {
		if (!StateManager.getCurrentContext().equals(sharedContext)) {
			throw new IncompatibleContextException("Incompatible context when trying to bind VAO");
		}
		StateManager.bindVertexArray(this);
	}
	
	public int getId() {
		return id;
	}

	public GlfwWindow getContext() {
		return sharedContext;
	}

	@Override
	public void close() {
		glDeleteVertexArrays(id);
		StateManager.bindVertexArray(NULL_VAO);
	}
}
