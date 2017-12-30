package xenoframium.glwrapper;

public class MissingShaderException extends RuntimeException{
	private static final long serialVersionUID = 1;
	
	public MissingShaderException(String string) {
		super(string);
	}
}
