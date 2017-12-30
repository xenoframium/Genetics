package xenoframium.glwrapper;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.PointerBuffer;

//Monitor objects are immutable
public class GlfwMonitor {
	private static final GlfwMonitor NULL_MONITOR = new GlfwMonitor(NULL);
	
	private final long pointer;

	private GlfwMonitor(long monitorPointer) {
		pointer = monitorPointer;
	}

	public static GlfwMonitor getNullMonitor() {
		return NULL_MONITOR;
	}
	
	public static GlfwMonitor getPrimaryMonitor() {
		return new GlfwMonitor(glfwGetPrimaryMonitor());
	}

	public static GlfwMonitor[] getMonitors() {
		PointerBuffer monitorBuffer = glfwGetMonitors();
		GlfwMonitor[] monitorList = new GlfwMonitor[monitorBuffer.remaining()];

		for (int i = 0; monitorBuffer.hasRemaining(); i++) {
			monitorList[i] = new GlfwMonitor(monitorBuffer.get());
		}

		return monitorList;
	}
	
	public long getPointer() {
		return pointer;
	}
}
