package xenoframium.glwrapper;

public class WindowBuilder {
	private int width;
	private int height;
	private String title;
	private WindowHintApplier windowHintApplier = new NullWindowHintApplier();
	private GlfwMonitor monitor = GlfwMonitor.getNullMonitor();
	private GlfwWindow sharedContext = GlfwWindow.getNullWindow();

	public WindowBuilder(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	public GlfwWindow build() {
		return new GlfwWindow(this);
	}
	
	public WindowBuilder setWidth(int newWidth) {
		width = newWidth;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public WindowBuilder setHeight(int newHeight) {
		height = newHeight;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public WindowBuilder setTitle(String newTitle) {
		title = newTitle;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WindowBuilder setWindowHintApplier(WindowHintApplier newWindowHintApplier) {
		windowHintApplier = newWindowHintApplier;
		return this;
	}

	public WindowHintApplier getWindowHintApplier() {
		return windowHintApplier;
	}

	public WindowBuilder setMonitor(GlfwMonitor newMonitor) {
		monitor = newMonitor;
		return this;
	}
	
	public GlfwMonitor getMonitor() {
		return monitor;
	}

	public WindowBuilder setSharedContext(GlfwWindow newSharedContext) {
		sharedContext = newSharedContext;
		return this;
	}
	
	public GlfwWindow getSharedContext() {
		return sharedContext;
	}
}
