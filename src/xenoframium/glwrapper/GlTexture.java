package xenoframium.glwrapper;

import static org.lwjgl.opengl.GL11.*;

public class GlTexture {
	private final int id;

	public GlTexture() {
		id = glGenTextures();
	}

	public int getId() {
		return id;
	}

	public void bind(int textureType) {
		StateManager.bindTexture(textureType, this);
	}
}
