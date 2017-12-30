package xenoframium.glwrapper;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

//Window objects are immutable
public class GlfwWindow {
	private static final GlfwWindow NULL_WINDOW = new GlfwWindow();

	private final GlfwWindow sharedContext;
	private final long id;

	GlfwWindow(WindowBuilder builder) {
		glfwDefaultWindowHints();
		builder.getWindowHintApplier().applyWindowHints();
		id = glfwCreateWindow(builder.getWidth(), builder.getHeight(), builder.getTitle(),
				builder.getMonitor().getPointer(), builder.getSharedContext().id);

		if (builder.getSharedContext().equals(NULL_WINDOW)) {
			sharedContext = this;
		} else {
			sharedContext = builder.getSharedContext().sharedContext;
		}
	}

	private GlfwWindow() {
		id = NULL;
		sharedContext = NULL_WINDOW;
	}

	public static GlfwWindow getNullWindow() {
		return NULL_WINDOW;
	}

	public void makeContextCurrent() {
		StateManager.makeContextCurrent(this);
	}

	public long getId() {
		return id;
	}

	public GlfwWindow getSharedContext() {
		return sharedContext;
	}
}
