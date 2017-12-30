package xenoframium.glwrapper;

import javax.swing.plaf.nimbus.State;

import static org.lwjgl.opengl.GL20.*;

public class GlProgram implements AutoCloseable {
	private static final GlProgram NULL_PROGRAM = new GlProgram(0);
	
	private final int id;
	
	private GlProgram(int id) {
		this.id = id;
	}
	
	public GlProgram(GlShader... shaders) throws MissingShaderException {
		this.id = glCreateProgram();
		
		boolean hasVertexShader = false;
		boolean hasFragmentShader = false;
		
		for (GlShader shader : shaders) {
			if (shader.getType() == GL_VERTEX_SHADER) {
				hasVertexShader = true;
			} else if (shader.getType() == GL_FRAGMENT_SHADER) {
				hasFragmentShader = true;
			}
			glAttachShader(this.id, shader.getId());
		}
		
		if (!hasVertexShader) {
			throw new MissingShaderException("No vertex shader was given when trying to create program.");
		} else if (!hasFragmentShader) {
			throw new MissingShaderException("No fragment shader was given when trying to create program.");
		}
		
		glLinkProgram(this.id);
		
		for (GlShader shader : shaders) {
			glDetachShader(this.id, shader.getId());
		}
	}

	public static GlProgram getNullProgram() {
		return NULL_PROGRAM;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GlProgram)) {
			return false;
		}
		GlProgram other = (GlProgram) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	public int getId() {
		return id;
	}
	
	public void use() {
		StateManager.useProgram(this);
	}

	@Override
	public void close() throws Exception {
		glDeleteProgram(id);
		StateManager.useProgram(NULL_PROGRAM);
	}
}
