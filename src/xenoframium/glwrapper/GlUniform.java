package xenoframium.glwrapper;

import static org.lwjgl.opengl.GL20.*;

//Uniform objects are immutable
public class GlUniform {
	private final int location;
	
	public GlUniform(GlProgram program, String name) {
		location = glGetUniformLocation(program.getId(), name);
	}
	
	public int getLocation() {
		return location;
	}
}
