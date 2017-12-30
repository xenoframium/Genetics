package xenoframium.glmath.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLMUtil {
	public static float[][] deepCopy(float[][] arr) {
		float[][] temp = new float[arr.length][];
		for (int i = 0; i < arr.length; i++) {
			temp[i] = arr[i].clone();
		}
		return temp;
	}
	
	public static FloatBuffer createDirectFloatBuffer(int length) {
		return ByteBuffer.allocateDirect(length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
}