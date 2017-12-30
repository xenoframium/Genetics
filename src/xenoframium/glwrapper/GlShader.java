package xenoframium.glwrapper;

import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.IOException;

public class GlShader {
	private static final GlShader NULL_SHADER = new GlShader(0, -1);
	
	private final int id;
	private final int type;

	private GlShader(int id, int type) {
		this.id = id;
		this.type = type;
	}
	
	public GlShader(int type, File file) {
		String shaderSource = null;
		try {
			shaderSource = GlWrapperFileUtil.readFile(file);
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		this.type = type;
		this.id = glCreateShader(type);
		glShaderSource(this.id, shaderSource);
		glCompileShader(this.id);
	}

	public GlShader(int type, String shaderSource) {
		this.type = type;
		this.id = glCreateShader(type);
		glShaderSource(this.id, shaderSource);
		glCompileShader(this.id);
	}

	public static GlShader getNullShader() {
		return NULL_SHADER;
	}
	
	public int getId() {
		return id;
	}

	public void delete() {
		glDeleteShader(id);
	}

	public int getType() {
		return type;
	}
}